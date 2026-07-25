package com.mr_toad.lib.client;

import com.mr_toad.lib.api.client.screen.config.ToadConfigScreen;
import com.mr_toad.lib.core.ToadLib;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ToadLibModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ToadConfigScreen(parent, ToadLib.CFG);
    }
}
