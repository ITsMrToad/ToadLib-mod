package com.mr_toad.lib.api.config.entry.primitive;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import net.minecraft.util.Mth;

public class LongEntry extends NumericEntry<Long, LongEntry> {

    public LongEntry(String name, long defaultValue) {
        super(name, defaultValue, Codec.LONG, ConfigEntryTypes.LONG);
    }

    @Override
    public LongEntry setStep(float step) {
        return super.setStep(Mth.floor(step));
    }

    @Override
    public boolean inBounds(Long v, Long min, Long max) {
        return v >= min && v <= max;
    }

    @Override
    public Long safeMin() {
        return -2096L;
    }

    @Override
    public Long safeMax() {
        return 2096L;
    }


}
