package com.mr_toad.lib.api.client.screen.config.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public record UnexpectedEntry(int x, int y) implements GuiEventListener, Renderable {

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {}

    @Override
    public void setFocused(boolean b) {}

    @Override
    public boolean isFocused() {
        return false;
    }
}
