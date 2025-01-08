package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

//Same as the vanilla checkbox but with a callback and a different texture
public class ExCheckbox extends AbstractButton {

    private static final ResourceLocation CHECKBOX = ToadLib.id("textures/gui/widget/checkbox.png");

    public boolean selected;

    private final boolean showLabel;
    private final BooleanConsumer callback;

    public ExCheckbox(int x, int y, boolean selected, BooleanConsumer callback) {
        this(x, y, CommonComponents.EMPTY, selected, false, callback);
    }

    public ExCheckbox(int x, int y, Component label, boolean selected, BooleanConsumer callback) {
        this(x, y, label, selected, true, callback);
    }

    protected ExCheckbox(int x, int y, Component label, boolean selected, boolean showLabel, BooleanConsumer callback) {
        super(x, y, 16, 16, label);
        this.selected = selected;
        this.showLabel = showLabel;
        this.callback = callback;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mx, int my, float partialTicks) {
        RenderSystem.enableDepthTest();
        graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(CHECKBOX, this.getX(), this.getY(), this.active ? (this.isHoveredOrFocused() ? 16 : 0) : 32, this.selected ? 16 : 0, 16, 16, 64, 64);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.showLabel) {
            graphics.drawString(Minecraft.getInstance().font, this.getMessage(), this.getX() + 18, this.getY() + (this.height - 8) / 2, ToadClientUtils.WHITE);
        }
    }

    @Override
    public void onPress() {
        this.selected = !this.selected;
        this.callback.accept(this.selected);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                output.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.focused"));
            } else {
                output.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.hovered"));
            }
        }
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
