package com.mr_toad.lib.api.config.entry.primitive;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import net.minecraft.util.Mth;

public class ShortEntry extends NumericEntry<Short, ShortEntry> {

    public ShortEntry(String name, short defaultValue) {
        super(name, defaultValue, Codec.SHORT, ConfigEntryTypes.SHORT);
    }

    @Override
    public ShortEntry setStep(float step) {
        return super.setStep(Mth.floor(step));
    }

    @Override
    public boolean inBounds(Short v, Short min, Short max) {
        return v >= min && v <= max;
    }

    @Override
    public Short safeMin() {
        return -1048;
    }

    @Override
    public Short safeMax() {
        return 1048;
    }
}
