package com.mr_toad.lib.api.util.time;

import com.mojang.logging.LogUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class UniformIntegerCooldown {
    
    private int minInclusive;
    private int maxInclusive;

    public final String name;

    public final RandomSource RANDOM = RandomSource.create();

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Marker MARKER = MarkerFactory.getMarker("UniformCooldown");

    public boolean immutable;
    public int sample;

    public UniformIntegerCooldown(int minInclusive, int maxInclusive, @Nullable String name) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;

        this.sample = this.getSample();

        if (name == null || name.isEmpty()) {
            name = this.toString();
        }

        this.name = name;
    }

    public static UniformIntegerCooldown createNoName(int min, int max) {
        return new UniformIntegerCooldown(min, max, null);
    }

    private int getSample() {
        return Mth.randomBetweenInclusive(RANDOM, this.minInclusive, this.maxInclusive);
    }

    public void reset() {
        this.setCooldown(this.getMin(), this.getMax());
    }

    public void tickUp() {
        ++this.sample;
    }

    public void tickDown() {
        --this.sample;
    }

    public UniformIntegerCooldown minus(int i) {
        this.check();
        this.sample -= i;
        return this;
    }

    public UniformIntegerCooldown sum(int i) {
        this.check();
        this.sample += i;
        return this;
    }

    public UniformIntegerCooldown setImmutable() {
        this.immutable = true;
        return this;
    }

    public void check() {
        if (this.immutable) {
            throw new IllegalArgumentException("Cannot modify cooldown value with name:" + this.getName() + "because cooldown field is immutable");
        } else {
            if (this.getSample() < 0) {
                this.setCooldown(0, 0);
                LOGGER.error(MARKER, "Cooldown value with name: {} cannot be negative", this.getName());
            }

        }
    }

    public @Nullable String getName() {
        return this.name;
    }

    public int getMin() {
        return this.minInclusive;
    }

    public int getMax() {
        return this.maxInclusive;
    }

    public void setCooldown(int min, int max) {
        this.minInclusive = min;
        this.maxInclusive = max;
    }

    @Override
    public String toString() {
        return "<uniform int cooldown value: name = " + this.getName() + ", cooldown = " + this.getSample() + ">";
    }
}
