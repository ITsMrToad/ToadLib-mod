package com.mr_toad.lib.api.client.screen.ex.widget;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.gui.widget.ForgeSlider;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ExSlider extends ForgeSlider {

    private final OnValueChange callback;

    public ExSlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue, double stepSize, OnValueChange callback) {
        this(x, y, width, height, prefix, suffix, minValue, maxValue, currentValue, stepSize, true, callback);
    }

    public ExSlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue, double stepSize, boolean drawString, OnValueChange callback) {
        super(x, y, width, height, prefix, suffix, minValue, maxValue, currentValue, stepSize, 0, drawString);
        this.callback = callback;
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double delta) {
        if (this.isHoveredOrFocused()) {
            this.setValue(this.getValue() + this.stepSize * delta);
            return true;
        }
        return false;
    }

    @Override
    protected void applyValue() {
        this.callback.apply(this);
    }

    @Override
    public void setValue(double value) {
        super.setValue(value);
        this.applyValue();
    }

    @Override
    protected void updateMessage() {
        MutableComponent c = Component.empty();
        if (this.drawString) {
            if (this.prefix != CommonComponents.EMPTY) {
                c.append(this.prefix);
            }
            c.append(this.getValueString());
            if (this.suffix != null) {
                c.append(this.suffix);
            }
        }
        this.setMessage(c);
    }


    @FunctionalInterface
    public interface OnValueChange {
        void apply(ExSlider slider);
    }
}
