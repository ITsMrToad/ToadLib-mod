package com.mr_toad.lib.api.config.entry.primitive;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import net.minecraft.util.Mth;

public class IntEntry extends NumericEntry<Integer, IntEntry> {

    public IntEntry(String name, int defaultValue) {
        super(name, defaultValue, Codec.INT, ConfigEntryTypes.INT);
    }

    @Override
    public IntEntry setStep(float step) {
        return super.setStep(Mth.floor(step));
    }

    @Override
    public boolean inBounds(Integer v, Integer min, Integer max) {
        return v >= min && v <= max;
    }

    @Override
    public Integer safeMin() {
        return -1048;
    }

    @Override
    public Integer safeMax() {
        return 1048;
    }
}
