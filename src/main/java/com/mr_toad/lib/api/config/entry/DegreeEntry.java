package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import net.minecraft.util.Mth;

public class DegreeEntry extends ConfigEntry<Float, DegreeEntry> {

    public DegreeEntry(String name, float defaultValue) {
        super(name, Mth.clamp(defaultValue, -180.0F, 180.0F), Codec.FLOAT, ConfigEntryTypes.DEGREE);
    }

    @Override
    public void setValue(Float value) {
        super.setValue(Mth.clamp(value, -180.0F, 180.0F));
    }

    public float toRad() {
        return this.get() * Mth.DEG_TO_RAD;
    }
}
