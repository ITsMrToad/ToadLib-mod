package com.mr_toad.lib.api.client.screen.ex.widget;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import org.jetbrains.annotations.NotNull;
import java.util.Locale;

public class ExSlider extends AbstractSliderButton {

    protected Component prefix;
    protected Component suffix;

    protected float minValue;
    protected float maxValue;
    protected float stepSize;

    private final OnValueChange callback;

    public ExSlider(int x, int y, int width, int height, Component prefix, Component suffix, float minValue, float maxValue, float currentValue, float stepSize, OnValueChange callback) {
        super(x, y, width, height, CommonComponents.EMPTY, toNormalized(currentValue, minValue, maxValue));
        this.prefix = prefix;
        this.suffix = suffix;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepSize = stepSize;
        this.callback = callback;
        this.updateMessage();
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isSelection()) {
            this.canChangeValue = !this.canChangeValue;
            return true;
        } else {
            if (this.canChangeValue) {
                boolean left = keyEvent.isLeft();
                boolean right = keyEvent.isRight();
                if (left || right) {
                    float dir = left ? -1.0F : 1.0F;
                    if (this.stepSize > 0.0F) {
                        this.setValue(this.value + dir * this.stepSize);
                    } else {
                        this.setValue(this.value + (dir / (this.width - 8)));
                    }
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void onClick(@NotNull MouseButtonEvent event, boolean bl) {
        this.setValueFromMouse2(event);
    }

    @Override
    protected void onDrag(@NotNull MouseButtonEvent event, double d, double e) {
        this.setValueFromMouse2(event);
    }

    @Override
    protected void applyValue() {
        this.callback.apply(this);
    }

    @Override
    protected void updateMessage() {
        this.setMessage(this.getMessage());
    }

    @Override
    public @NotNull Component getMessage() {
        return this.prefix.copy().append(this.getValueString()).append(this.suffix);
    }

    public void setRealValue(double real) {
        real = Mth.clamp(real, this.minValue, this.maxValue);
        if (this.stepSize > 0.0) {
            real = Math.round(real / this.stepSize) * this.stepSize;
            real = Mth.clamp(real, this.minValue, this.maxValue);
        }
        this.value = toNormalized(real, this.minValue, this.maxValue);
        this.applyValue();
        this.updateMessage();
    }

    private void setValueFromMouse2(MouseButtonEvent e) {
        double raw = (e.x() - (this.getX() + 4.0)) / (this.width - 8.0);
        raw = Mth.clamp(raw, 0.0, 1.0);
        double real = toReal(raw, this.minValue, this.maxValue);
        if (this.stepSize > 0.0) {
            real = Math.round(real / this.stepSize) * this.stepSize;
        }
        this.value = toNormalized(real, this.minValue, this.maxValue);
        this.applyValue();
        this.updateMessage();
    }

    private static double toNormalized(double real, double min, double max) {
        if (max == min) return 0.0;
        return Mth.clamp((real - min) / (max - min), 0.0, 1.0);
    }

    private static double toReal(double normalized, double min, double max) {
        return min + normalized * (max - min);
    }

    public double getValueDouble() {
        return toReal((float) this.value, this.minValue, this.maxValue);
    }

    public float getValue() {
        return (float) this.getValueDouble();
    }

    public long getValueLong() {
        return Math.round(this.getValue());
    }

    public int getValueInt() {
        return (int) this.getValueLong();
    }

    public String getValueString() {
        double v = this.getValueDouble();
        if (this.stepSize >= 1.0) {
            return String.valueOf(getValueLong());
        }
        int dec = Math.max(0, (int) Math.ceil(-Math.log10(this.stepSize)));
        return String.format(Locale.ROOT, "%." + dec + "f", v);
    }

    @FunctionalInterface
    public interface OnValueChange {
        void apply(ExSlider slider);
    }
}
