package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class ExCheckboxV2 extends AbstractButton {

    private static final Identifier CHECKBOX = ToadLib.id("textures/gui/widget/checkbox.png");

    public boolean selected;

    private final BooleanConsumer callback;
    private final MultiLineTextWidget textWidget;

    public ExCheckboxV2(int x, int y, int mw, Font renderer, boolean selected, BooleanConsumer callback) {
        this(x, y, mw, CommonComponents.EMPTY, renderer, selected, callback);
    }

    public ExCheckboxV2(int x, int y, int maxWidth, Component label, Font textRenderer, boolean selected, BooleanConsumer callback) {
        super(x, y, 0, 0, label);
        this.width = this.calculateWidth(maxWidth, label, textRenderer);
        this.textWidget = label == CommonComponents.EMPTY ? null : new MultiLineTextWidget(label, Minecraft.getInstance().font).setMaxWidth(this.width);
        this.height = this.calculateHeight();
        this.selected = selected;
        this.callback = callback;
    }

    private int calculateWidth(int max, Component text, Font textRenderer) {
        return Math.min(21 + textRenderer.width(text), max);
    }

    private int calculateHeight() {
        if (this.textWidget == null) {
            return 17;
        }
        return Math.max(17, this.textWidget.getHeight());
    }

    @Override
    protected void extractContents(@NonNull GuiGraphicsExtractor graphics, int mx, int my, float a) {
        if (this.textWidget != null) {
            int j = this.getX() + 21;
            int k = this.getY() + 8 - this.textWidget.getHeight() / 2;
            this.textWidget.setPosition(j, k);
            this.textWidget.extractRenderState(graphics, mx, my, a);
        }
        graphics.blit(RenderPipelines.GUI_TEXTURED, CHECKBOX, this.getX(), this.getY(), this.active ? (this.isHovered() || this.isFocused() ? 16 : 0) : 32, this.selected ? 16 : 0, 16, 16, 64, 64);
    }

    @Override
    public void onPress(@NonNull InputWithModifiers inputWithModifiers) {
        this.selected = !this.selected;
        this.callback.accept(this.selected);
    }

    public boolean isSelected() {
        return this.selected;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        builder.add(NarratedElementType.TITLE, this.getMessage());
        if (this.active) {
            if (this.isFocused()) {
                builder.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.focused"));
            } else {
                builder.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.hovered"));
            }
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
