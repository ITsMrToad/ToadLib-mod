package com.mr_toad.lib.api.client.screen.ex;

import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ToadLibScreen extends Screen {

    protected ToadLibScreen() {
        super(CommonComponents.EMPTY);
    }

    protected ToadLibScreen(Component t) {
        super(t);
    }

    @Override
    protected void init() {
        this.addRenderableWidget(new StringWidget(this.width / 2, 10, this.getTitle(), this.font));
    }
}
