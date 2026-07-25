package com.mr_toad.lib.api.config.util;

import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import java.util.function.BooleanSupplier;

@SuppressWarnings("unchecked")
public class DeprecationRule<T> {

    private Component tooltip = CommonComponents.EMPTY;
    @Nullable private T displayValue = null;

    private final BooleanSupplier rule;

    public DeprecationRule(BooleanSupplier rule) {
        this.rule = rule;
    }

    public static<O> DeprecationRule<O> make(BooleanSupplier rule) {
        return new DeprecationRule<>(rule);
    }

    public static<O> DeprecationRule<O> make(BooleanSupplier rule, Component tooltip) {
        return (DeprecationRule<O>) new DeprecationRule<>(rule).addTooltip(tooltip);
    }

    public static<O> DeprecationRule<O> make(BooleanSupplier rule, O val) {
        return (DeprecationRule<O>) new DeprecationRule<>(rule).addDisplayValue(val);
    }

    public static<O> DeprecationRule<O> make(BooleanSupplier rule, Component tooltip, O val) {
        return (DeprecationRule<O>) new DeprecationRule<>(rule).addTooltip(tooltip).addDisplayValue(val);
    }

    public DeprecationRule<T> addTooltip(Component tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public DeprecationRule<T> addDisplayValue(T value) {
        this.displayValue = value;
        return this;
    }

    public boolean hasDisplayValue() {
        return this.displayValue != null;
    }

    public boolean isActive() {
        return this.rule.getAsBoolean();
    }

    public Component getTooltip() {
        return this.tooltip;
    }

    public T getDisplayValue() {
        return Objects.requireNonNull(this.displayValue);
    }

}
