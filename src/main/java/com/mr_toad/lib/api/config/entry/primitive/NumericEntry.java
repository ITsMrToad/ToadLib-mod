package com.mr_toad.lib.api.config.entry.primitive;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryType;
import com.mr_toad.lib.api.config.error.ConfigException;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;

public abstract class NumericEntry<S extends Number, N extends NumericEntry<S, N>> extends ConfigEntry<S, N> {

    private float step = 1.0F;
    private boolean fatal = false;

    private Pair<S, S> range;

    protected NumericEntry(String name, S defaultValue, Codec<S> codec, ConfigEntryType type) {
        super(name, defaultValue, codec, type);
    }

    public abstract boolean inBounds(S v, S min, S max);

    public abstract S safeMin();

    public abstract S safeMax();

    @Override
    public void setValue(S value) {
        if (this.checkBounds(value, this.range)) {
            if (this.fatal) {
                throw new ConfigException("New target of '" + this.name + "' not in bounds!");
            } else {
                ToadLib.LOGGER.error(ToadLib.CONFIG, "New target of '{}' not in bounds!", this.name);
            }
            this.resetValue();
        } else {
            super.setValue(value);
        }
    }

    @Override
    public Component getTitle() {
        return super.getTitle().copy().append(": ");
    }

    public N makeFatal() {
        this.fatal = true;
        return (N) this;
    }

    public N range(S min, S max) {
        if (min.equals(max)) {
            throw new ConfigException("Min and max cannot be equal! " + this.name);
        } else if (min.doubleValue() > max.doubleValue()) {
            throw new ConfigException("Illegal min/max combination! " + this.name);
        } else {
            this.range = Pair.of(min, max);
            if (this.checkBounds(this.defaultValue, this.range)) {
                throw new ConfigException("Default target of '" + this.name + "' not in bounds!");
            }
        }
        return (N) this;
    }

    public N setStep(float step) {
        this.step = step;
        return (N) this;
    }

    public boolean checkBounds(S v, @Nullable Pair<S, S> range) {
        if (range == null) {
            return false;
        } else {
            return !this.inBounds(v, range.getFirst(), range.getSecond());
        }
    }

    public S getMax() {
        if (this.range == null) {
            return this.safeMax();
        } else {
            return this.range.getSecond();
        }
    }

    public S getMin() {
        if (this.range == null) {
            return this.safeMin();
        } else {
            return this.range.getFirst();
        }
    }

    public float getStep() {
        return this.step;
    }
}
