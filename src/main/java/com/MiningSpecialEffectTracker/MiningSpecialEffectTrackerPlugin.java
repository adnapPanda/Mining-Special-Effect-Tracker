package com.MiningSpecialEffectTracker;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Set;

import static net.runelite.api.AnimationID.*;

@Slf4j
@PluginDescriptor(
	name = "Mining Special Effect Tracker"
)
public class MiningSpecialEffectTrackerPlugin extends Plugin
{
	int totalOreMined = 0, totalOreEffect = 0, smithingEffect = 0, totalSmithingEffect = 0;
	int varrockArmourEffect, celestrialRingEffect, miningCapeEffect, miningGlovesEffect;
	float miningDuration = 0;
	boolean inMlm = false;

	public static final Set<Integer> MINING_ANIMATION_IDS = ImmutableSet.of(
			MINING_BRONZE_PICKAXE, MINING_IRON_PICKAXE, MINING_STEEL_PICKAXE, MINING_BLACK_PICKAXE,
			MINING_MITHRIL_PICKAXE, MINING_ADAMANT_PICKAXE, MINING_RUNE_PICKAXE, MINING_GILDED_PICKAXE,
			MINING_DRAGON_PICKAXE, MINING_DRAGON_PICKAXE_UPGRADED, MINING_DRAGON_PICKAXE_OR,
			MINING_DRAGON_PICKAXE_OR_TRAILBLAZER, MINING_INFERNAL_PICKAXE, MINING_3A_PICKAXE, MINING_CRYSTAL_PICKAXE,
			MINING_TRAILBLAZER_PICKAXE, MINING_TRAILBLAZER_PICKAXE_2, MINING_TRAILBLAZER_PICKAXE_3,

			MINING_MOTHERLODE_BRONZE, MINING_MOTHERLODE_IRON, MINING_MOTHERLODE_STEEL, //need motherload mine ids for amethyst
			MINING_MOTHERLODE_BLACK, MINING_MOTHERLODE_MITHRIL, MINING_MOTHERLODE_ADAMANT,
			MINING_MOTHERLODE_RUNE, MINING_MOTHERLODE_GILDED, MINING_MOTHERLODE_DRAGON,
			MINING_MOTHERLODE_DRAGON_UPGRADED, MINING_MOTHERLODE_DRAGON_OR, MINING_MOTHERLODE_DRAGON_OR_TRAILBLAZER,
			MINING_MOTHERLODE_INFERNAL, MINING_MOTHERLODE_3A, MINING_MOTHERLODE_CRYSTAL,
			MINING_MOTHERLODE_TRAILBLAZER
	);

	private static final Set<Integer> MOTHERLODE_MAP_REGIONS = ImmutableSet.of(14679, 14680, 14681, 14935, 14936, 14937, 15191, 15192, 15193);

	@Inject
	private Client client;

	@Inject
	private MiningSpecialEffectTrackerConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MiningSpecialEffectTrackerOverlay overlay;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		inMlm = checkInMlm();
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Provides
	MiningSpecialEffectTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MiningSpecialEffectTrackerConfig.class);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)	inMlm = checkInMlm();
	}

	private boolean checkInMlm() {
		GameState gameState = client.getGameState();
		if (gameState != GameState.LOGGED_IN && gameState != GameState.LOADING)
		{
			return false;
		}

		int[] currentMapRegions = client.getMapRegions();

		// Verify that all regions exist in MOTHERLODE_MAP_REGIONS
		for (int region : currentMapRegions)
		{
			if (!MOTHERLODE_MAP_REGIONS.contains(region))
			{
				return false;
			}
		}

		return true;
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		if (inMlm) {
			return;
		}
		if (miningDuration > 0) {
			miningDuration -= 0.6;
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (inMlm) { //if in mlm return since mining effects dont work in mlm
			return;
		}
		if (event.getType() != ChatMessageType.SPAM) {
			return;
		}

		String message = event.getMessage();

		if (message.equals("The Varrock platebody enabled you to mine an additional ore.")) {
			incrementOreEffect(1);
			incrementOreMined(1);
			incrementVarrockEffect(1);
		}

		if (message.equals("Your Celestial ring allows you to mine an additional ore.")) {
			incrementOreEffect(1);
			incrementOreMined(1);
			incrementCelestialEffect(1);
		}

		if (message.equals("Your cape allows you to mine an additional ore.")) {
			incrementOreEffect(1);
			incrementOreMined(1);
			incrementCapeEffect(1);
		}

		if (message.equals("The Varrock platebody enabled you to smelt your next ore simultaneously.")) {
			incrementSmithingEffect(1);
		}

		if (message.equals("Your Mining gloves prevent the rock from depleting.")) {
			incrementOreEffect(1);
			incrementGlovesEffect(1);
		}

		if (message.matches("You manage to mine some(.*)")) {
			miningDuration = 300; //reset overlay to 5 minutes
			incrementOreMined(1);
		}
	}

	public void incrementOreMined(int count) {
		totalOreMined+=count;
	}

	public void incrementOreEffect(int count) {
		totalOreEffect+=count;
	}

	public void incrementSmithingEffect(int count) {
		totalSmithingEffect+=count;
	}

	public void incrementVarrockEffect(int count) {
		varrockArmourEffect+=count;
	}

	public void incrementCelestialEffect(int count) {
		celestrialRingEffect+=count;
	}

	public void incrementCapeEffect(int count) {
		miningCapeEffect+=count;
	}

	public void incrementGlovesEffect(int count) {
		miningGlovesEffect+=count;
	}

	public boolean checkMining() {
		return MINING_ANIMATION_IDS.contains(client.getLocalPlayer().getAnimation());
	}
}
