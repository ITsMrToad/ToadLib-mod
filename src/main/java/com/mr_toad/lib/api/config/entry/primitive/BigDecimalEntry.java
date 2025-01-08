package com.mr_toad.lib.api.config.entry.primitive;

import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.api.config.error.ConfigException;
import com.mr_toad.lib.mtjava.math.MtMath;

import java.math.BigDecimal;

public class BigDecimalEntry extends NumericEntry<BigDecimal, BigDecimalEntry> {

    public BigDecimalEntry(String name, BigDecimal defaultValue) {
        super(name, defaultValue, MtMath.BIG_DECIMAL, ConfigEntryTypes.BIG_DECIMAL);
    }

    @Override
    public BigDecimal get() {
        BigDecimal decimal = super.get();
        if (MtMath.isNanOrInfinite(decimal)) {
            throw new ConfigException("Value of '" + this.name + "' is NaN or infinite!");
        }
        return decimal;
    }

    @Override
    public boolean inBounds(BigDecimal v, BigDecimal min, BigDecimal max) {
        return v.doubleValue() >= min.doubleValue() && v.doubleValue() <= max.doubleValue();
    }

    @Override
    public BigDecimal safeMin() {
        return BigDecimal.valueOf(-1048);
    }

    @Override
    public BigDecimal safeMax() {
        return BigDecimal.valueOf(1048);
    }

    public void setValue(float value) {
        this.setValue(new BigDecimal(value));
    }

    public void setValue(double value) {
        this.setValue(new BigDecimal(value));
    }

    public float getFloat() {
        return this.get().floatValue();
    }

    public double getDouble() {
        return this.get().doubleValue();
    }

}
