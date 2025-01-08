package com.mr_toad.lib.api.client.screen.interpolation.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mr_toad.lib.api.client.screen.ex.widget.ExCheckbox;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Predicate;

public class InterpolationTypeWidget extends ExCheckbox {

    private static final Component FOCUSED = Component.translatable("toadlib.nar.interp.focused");
    private static final Component HOVERED = Component.translatable("toadlib.nar.interp.hovered");

    private static final ResourceLocation SELECTED = ToadLib.id("textures/gui/widget/interpolation/selected.png");

    private final ResourceLocation icon;
    private final Predicate<Interpolation> same;

    public InterpolationTypeWidget(int x, int y, boolean selected, BooleanConsumer callback, Interpolation interpolation) {
        super(x, y, CommonComponents.EMPTY, selected, false, callback);
        this.icon = ToadLib.id("textures/gui/widget/interpolation/" + interpolation.name() + ".png");
        this.same = i -> i.name().equals(interpolation.name());
        this.setTooltip(Tooltip.create(CommonComponents.joinLines(interpolation.getName(), CommonComponents.NEW_LINE, interpolation.getTooltip())));
        this.setWidth(20);
        this.setHeight(20);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        if (this.isSelected()) {
            graphics.blit(SELECTED, this.getX(), this.getY(), 0, 0,this.width, this.height, 20, 20);
        }
        graphics.blit(this.icon, this.getX(), this.getY(), 0, 0, this.width, this.height, 20, 20);
    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                narrationElementOutput.add(NarratedElementType.USAGE, FOCUSED);
            } else {
                narrationElementOutput.add(NarratedElementType.USAGE, HOVERED);
            }
        }
    }

    public boolean isSame(Interpolation other) {
        return this.same.test(other);
    }
}
