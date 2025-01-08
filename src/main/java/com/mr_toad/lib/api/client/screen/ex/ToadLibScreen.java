package com.mr_toad.lib.api.client.screen.ex;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ToadLibScreen extends Screen {

    public final boolean centred;
    @Nullable private ClickEvent titleClickEvent = null;

    protected ToadLibScreen(Component t, boolean centred) {
        super(t);
        this.centred = centred;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        if (this.getTitle() != CommonComponents.EMPTY) {
            if (this.centred) {
                graphics.drawCenteredString(this.font, this.getTitle(), this.width / 2, 10, 10526880);
            } else {
                graphics.drawString(this.font, this.getTitle(), this.width / 2, 10, 10526880);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (super.mouseClicked(mx, my, button)) {
            return true;
        } else {
            if (this.titleClickEvent != null) {
                return ToadClientUtils.getStyleOf(this.font, this.getTitle(), mx, my, this.width / 2, 10).map(this::handleComponentClicked).orElse(this.clickAtComponent(mx, my, button));
            } else {
                return this.clickAtComponent(mx, my, button);
            }
        }
    }

    protected boolean clickAtComponent(double mx, double my, int button) {
        return false;
    }

    public void addTitleClickEvent(ClickEvent event) {
        this.titleClickEvent = event;
    }
}
