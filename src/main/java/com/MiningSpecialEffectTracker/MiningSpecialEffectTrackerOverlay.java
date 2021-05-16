package com.MiningSpecialEffectTracker;

import java.awt.*;
import java.util.List;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

class MiningSpecialEffectTrackerOverlay extends OverlayPanel {

    private Client client;
    private MiningSpecialEffectTrackerPlugin plugin;
    private MiningSpecialEffectTrackerConfig config;

    @Inject
    MiningSpecialEffectTrackerOverlay(MiningSpecialEffectTrackerPlugin plugin, Client client, MiningSpecialEffectTrackerConfig config) {
        super(plugin);
        this.client = client;
        this.plugin = plugin;
        this.config = config;

        setPosition(OverlayPosition.TOP_LEFT);

        getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY, OPTION_CONFIGURE, "Mining Special Effect Overlay"));
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (!plugin.inMlm) {
            if (plugin.checkMining() || plugin.miningDuration > 0) { //if player has mined in the last 5 minutes - then display overlay

                List<LayoutableRenderableEntity> elems = panelComponent.getChildren();
                elems.clear();
                panelComponent.setPreferredSize(new Dimension(160, 200));

                if (config.showMiningState()) {
                    if (plugin.checkMining()) {
                        panelComponent.getChildren().add(TitleComponent.builder()
                                .text("Mining")
                                .color(Color.GREEN)
                                .build());
                        plugin.miningDuration = 300; //reset overlay to 5 minutes
                    } else {
                        panelComponent.getChildren().add(TitleComponent.builder()
                                .text("NOT mining")
                                .color(Color.RED)
                                .build());
                    }
                }

                if (config.showTotalEffect())
                    elems.add(LineComponent.builder()
                            .left("Total Effect:")
                            .right(String.format("%d", plugin.totalOreEffect))
                            .build());

                if (config.showTotalOres())
                    elems.add(LineComponent.builder()
                            .left("Total Mined:")
                            .right(String.format("%d", plugin.totalOreMined))
                            .build());

                if (config.showSmithingEffect())
                    elems.add(LineComponent.builder()
                            .left("Smithing Effect:")
                            .right(String.format("%d", plugin.smithingEffect))
                            .build());

                if (config.showVarrockEffect())
                    elems.add(LineComponent.builder()
                            .left("Varrock Armour Effect:")
                            .right(String.format("%d", plugin.varrockArmourEffect))
                            .build());

                if (config.showCelestialEffect())
                    elems.add(LineComponent.builder()
                            .left("Celestial Ring Effect:")
                            .right(String.format("%d", plugin.celestrialRingEffect))
                            .build());

                if (config.showCapeEffect())
                    elems.add(LineComponent.builder()
                            .left("Mining Cape Effect:")
                            .right(String.format("%d", plugin.miningCapeEffect))
                            .build());

                if (config.showGlovesEffect())
                    panelComponent.getChildren().add(LineComponent.builder()
                            .left("Mining Gloves Effect:")
                            .right(String.format("%d", plugin.miningGlovesEffect))
                            .build());
            }
        }
        return super.render(graphics);
        }
}
