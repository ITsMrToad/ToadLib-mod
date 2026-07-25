package com.mr_toad.lib.api.config.entry.primitive;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;

public class FloatEntry extends NumericEntry<Float, FloatEntry> {

    public FloatEntry(String name, float defaultValue) {
        super(name, defaultValue, Codec.FLOAT, ConfigEntryTypes.FLOAT);
    }

    @Override
    public boolean inBounds(Float v, Float min, Float max) {
        return v >= min && v <= max;
    }

    @Override
    public Float safeMin() {
        return Float.MIN_VALUE;
    }

    @Override
    public Float safeMax() {
        return Float.MAX_VALUE;
    }
}
