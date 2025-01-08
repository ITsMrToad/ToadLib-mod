package com.mr_toad.lib.api.client.screen.config.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

//Null-safe entry
@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public record UnexpectedEntry(int x, int y) implements GuiEventListener, Renderable {

    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pt) {}

    @Override
    public void setFocused(boolean b) {}

    @Override
    public boolean isFocused() {
        return false;
    }
}
