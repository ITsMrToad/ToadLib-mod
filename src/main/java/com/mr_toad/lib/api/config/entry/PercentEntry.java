package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.mtjava.MtJava;
import it.unimi.dsi.fastutil.SafeMath;
import net.minecraft.util.Mth;

public class PercentEntry extends ConfigEntry<Double, PercentEntry> {

    public PercentEntry(String name, double defaultValue) {
        super(name, defaultValue, Codec.DOUBLE, ConfigEntryTypes.PERCENT);
        MtJava.validatePercents(defaultValue);
    }

    @Override
    public void setValue(Double value) {
        super.setValue(value);
        MtJava.validatePercents(value);
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
