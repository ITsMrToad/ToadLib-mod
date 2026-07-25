package com.mr_toad.lib.api.util.time;

import com.mr_toad.lib.core.ToadLib;

import org.jetbrains.annotations.Nullable;

public class IntegerCooldown {

    public int cooldown;
    @Nullable public final String name;
    public boolean immutable = false;

    public static IntegerCooldown createNoName(int cooldown) {
        return new IntegerCooldown(cooldown, null);
    }

    public IntegerCooldown(int cooldown, @Nullable String name) {
        this.cooldown = cooldown;
        if (name == null || name.isEmpty()) {
            name = this.toString();
        }
        this.name = name;

    }

    public void reset() {
        this.setCooldown(this.getCooldown());
    }

    public void tickUp() {
        ++this.cooldown;
    }

    public void tickDown() {
        --this.cooldown;
    }

    public IntegerCooldown minus(int i) {
        this.check();
        this.cooldown -= i;
        return this;
    }

    public IntegerCooldown sum(int i) {
        this.check();
        this.cooldown += i;
        return this;
    }

    public IntegerCooldown setImmutable() {
        this.immutable = true;
        return this;
    }

    public void check() {
        if (this.immutable) {
            throw new IllegalArgumentException("Cannot modify cooldown value with name:" + this.getName() + "because cooldown field is immutable");
        }

        if (this.getCooldown() < -1) {
            this.setCooldown(0);
            ToadLib.LOGGER.error("Cooldown value with name: {} cannot be lower than -1", this.getName());
        }

    }

    public @Nullable String getName() {
        return this.name;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public String toString() {
        return "<int cooldown value: name = " + this.getName() + ", cooldown = " + this.getCooldown() + ">";
    }
}

