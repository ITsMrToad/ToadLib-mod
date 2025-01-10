package com.mr_toad.lib.api.client.screen.config.widget;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class ConfigPageWidget extends AbstractWidget {

    public ConfigPageWidget(int x, int y, Component msg) {
        super(x, y, 220, 25, msg);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mx, int my, float f) {
        graphics.drawCenteredString(Minecraft.getInstance().font, this.getMessage(), this.getX(), this.getY(), ToadClientUtils.DEFAULT_TEXT);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, this.getMessage());
    }
}
