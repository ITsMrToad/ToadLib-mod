package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import it.unimi.dsi.fastutil.SafeMath;
import net.minecraft.util.Mth;

public class PercentEntry extends ConfigEntry<Double, PercentEntry> {

    public PercentEntry(String name, double defaultValue) {
        super(name, Mth.clamp(defaultValue, 0.0D, 1.0D), Codec.DOUBLE, ConfigEntryTypes.PERCENT);
    }

    @Override
    public void setValue(Double value) {
        super.setValue(Mth.clamp(value, 0.0D, 1.0D));
    }

    public int getByPercents(int maxValue) {
        return Mth.floor(this.get()) * maxValue;
    }

    public long getByPercents(long maxValue) {
        return Mth.lfloor(this.get()) * maxValue;
    }

    public double getByPercents(double maxValue) {
        return this.get() * maxValue;
    }

    public float getByPercents(float maxValue) {
        return SafeMath.safeDoubleToFloat(this.get() * maxValue);
    }

}
