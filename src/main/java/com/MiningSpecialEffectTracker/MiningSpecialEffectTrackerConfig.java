package com.MiningSpecialEffectTracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("VarrockArmourTrackerConfig")
public interface MiningSpecialEffectTrackerConfig extends Config
{
	@ConfigItem(
			position = 0,
			keyName = "showMiningState",
			name = "Mining State",
			description = "Displays whether you are currently mining or not"
	)
	default boolean showMiningState() {
		return true;
	}

	@ConfigItem(
			position = 1,
			keyName = "showTotalEffect",
			name = "Total Effect",
			description = "The number of times any mining special effect has occurred.)"
	)
	default boolean showTotalEffect() {
		return true;
	}

	@ConfigItem(
			position = 2,
			keyName = "showTotalOres",
			name = "Total Mined",
			description = "The total number of ores mined including the extra ores"
	)
	default boolean showTotalOres() {
		return true;
	}

	@ConfigItem(
			position = 3,
			keyName = "showSmithingEffect",
			name = "Smithing Effect",
			description = "The number of times the varrock armour double smithing effect has activated"
	)
	default boolean showSmithingEffect() {
		return false;
	}

	@ConfigItem(
			position = 4,
			keyName = "showVarrockEffect",
			name = "Varrock Armour Effect",
			description = "The number of times the varrock armour double ore effect has activated"
	)
	default boolean showVarrockEffect() {
		return false;
	}

	@ConfigItem(
			position = 5,
			keyName = "showCelestialEffect",
			name = "Celestial Ring Effect",
			description = "The number of times the celestial ring double ore effect has activated"
	)
	default boolean showCelestialEffect() {
		return false;
	}

	@ConfigItem(
			position = 6,
			keyName = "showCapeEffect",
			name = "Mining Cape Effect",
			description = "The number of times the mining cape double ore effect has activated"
	)
	default boolean showCapeEffect() {
		return false;
	}

	@ConfigItem(
			position = 7,
			keyName = "showGlovesEffect",
			name = "Mining Gloves Effect",
			description = "The number of times the mining gloves special effect has activated"
	)
	default boolean showGlovesEffect() {
		return false;
	}
}
