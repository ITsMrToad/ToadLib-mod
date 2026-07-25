package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import org.jetbrains.annotations.NotNull;

///@deprecated outdated version of checkbox. Use {@link com.mr_toad.lib.api.client.screen.ex.widget.ExCheckboxV2} instead
@Deprecated(since = "idk")
public class ExCheckbox extends AbstractButton {

    private static final Identifier CHECKBOX = ToadLib.id("textures/gui/widget/checkbox.png");

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
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CHECKBOX, this.getX(), this.getY(), this.active ? (this.isHovered() || this.isFocused() ? 16 : 0) : 32, this.selected ? 16 : 0, 16, 16, 64, 64);
        if (this.showLabel) {
            graphics.text(Minecraft.getInstance().font, this.getMessage(), this.getX() + 18, this.getY() + (this.height - 8) / 2, ToadClientUtils.WHITE);
        }
    }

    @Override
    public void onPress(@NotNull InputWithModifiers modifiers) {
        this.selected = !this.selected;
        this.callback.accept(this.selected);
    }

    public boolean isSelected() {
        return this.selected;
    }

    @SuppressWarnings("DeprecatedTranslation")
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
