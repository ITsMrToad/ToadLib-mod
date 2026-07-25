package com.mr_toad.lib.api.config.entry.primitive;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;

public class DoubleEntry extends NumericEntry<Double, DoubleEntry> {

    public DoubleEntry(String name, double defaultValue) {
        super(name, defaultValue, Codec.DOUBLE, ConfigEntryTypes.DOUBLE);
    }

    @Override
    public boolean inBounds(Double v, Double min, Double max) {
        return v >= min && v <= max;
    }

    @Override
    public Double safeMin() {
        return (double) Float.MIN_VALUE;
    }

    @Override
    public Double safeMax() {
        return (double) Float.MAX_VALUE;
    }
}
