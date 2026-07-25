package com.mr_toad.lib.api.client.screen.interpolation.widget;

import com.mr_toad.lib.api.client.screen.ex.widget.ExCheckboxV2;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.function.Predicate;

public class InterpolationTypeWidget extends ExCheckboxV2 {

    private static final Component FOCUSED = Component.translatable("toadlib.nar.interp.focused");
    private static final Component HOVERED = Component.translatable("toadlib.nar.interp.hovered");

    private static final Identifier SELECTED = ToadLib.id("textures/gui/widget/interpolation/selected.png");

    private final Identifier icon;
    private final Predicate<Interpolation> same;

    @SuppressWarnings("UnstableTypeUsedInSignature")
    public InterpolationTypeWidget(int x, int y, int mw, Font font, boolean selected, BooleanConsumer callback, Interpolation interpolation) {
        super(x, y, mw, font, selected, callback);
        this.icon = ToadLib.id("textures/gui/widget/interpolation/" + interpolation.name() + ".png");
        this.same = i -> i.name().equals(interpolation.name());
        this.setTooltip(Tooltip.create(CommonComponents.joinLines(interpolation.getName(), interpolation.getTooltip())));
        this.setWidth(20);
        this.height = 20;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (this.isSelected()) {
            graphics.blit(SELECTED, this.getX(), this.getY(), 0, 0,this.width, this.height, 20, 20);
        }
        graphics.blit(this.icon, this.getX(), this.getY(), 0, 0, this.width, this.height, 20, 20);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        builder.add(NarratedElementType.TITLE, this.getMessage());
        if (this.active) {
            if (this.isFocused()) {
                builder.add(NarratedElementType.USAGE, FOCUSED);
            } else {
                builder.add(NarratedElementType.USAGE, HOVERED);
            }
        }
    }

    public boolean isSame(Interpolation other) {
        return this.same.test(other);
    }
}
