package com.mr_toad.lib.api.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TitleableScreen extends Screen {

    public final boolean centred;

    protected TitleableScreen(Component t, boolean centred) {
        super(t);
        this.centred = centred;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        if (this.centred) {
            graphics.drawCenteredString(this.font, this.getTitle(), this.width / 2, 10, 10526880);
        } else {
            graphics.drawString(this.font, this.getTitle(), this.width / 2, 10, 10526880);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
