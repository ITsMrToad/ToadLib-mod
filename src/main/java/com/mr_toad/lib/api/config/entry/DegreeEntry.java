package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.mtjava.MtJava;
import net.minecraft.util.Mth;

public class DegreeEntry extends ConfigEntry<Float, DegreeEntry> {

    public DegreeEntry(String name, float defaultValue) {
        super(name, defaultValue, Codec.FLOAT, ConfigEntryTypes.DEGREE);
        MtJava.validateDegrees(defaultValue);
    }

    @Override
    public void setValue(Float value) {
        MtJava.validateDegrees(value);
        super.setValue(value);
    }

    public float toRad() {
        return this.get() * Mth.DEG_TO_RAD;
    }
}
