package com.mr_toad.lib.client;

import com.mr_toad.lib.api.client.init.ConfigEntryWidgetsRegistry;
import com.mr_toad.lib.api.client.init.EaseWidgetSettingsRegistry;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.integration.IntegrationV2;
import com.mr_toad.lib.core.ToadLib;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;

@Environment(EnvType.CLIENT)
public class ToadLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EaseWidgetSettingsRegistry.init();
        ConfigEntryWidgetsRegistry.init();

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!IntegrationV2.MOD_MENU.isLoaded() && ToadLib.CFG.showConfigButton.get()) {
                if (screen instanceof TitleScreen s) {
                    s.addRenderableWidget(ToadClientUtils.createConfigButton(screen.width / 2 + 104, screen.height / 4 + 96, screen));
                } else if (screen instanceof PauseScreen s) {
                    s.addRenderableWidget(ToadClientUtils.createConfigButton(screen.width / 2 + 104, screen.height / 4 + 89, screen));
                }
            }
        });
    }
}
