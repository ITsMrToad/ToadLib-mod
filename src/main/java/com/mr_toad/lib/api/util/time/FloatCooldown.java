package com.mr_toad.lib.api.util.time;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatCooldown {

    public float cooldown;
    @Nullable public final String name;
    public boolean immutable = false;
    public static final Logger LOGGER = LoggerFactory.getLogger("Cooldown");

    public static final FloatCooldown BASIC = FloatCooldown.createNoName(500);

    public static FloatCooldown createNoName(int cooldown) {
        return new FloatCooldown(cooldown, null);
    }

    public FloatCooldown(int cooldown, @Nullable String name) {
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

    public FloatCooldown minus(float i) {
        this.check();
        this.cooldown -= i;
        return this;
    }

    public FloatCooldown sum(float i) {
        this.check();
        this.cooldown += i;
        return this;
    }

    public FloatCooldown setImmutable() {
        this.immutable = true;
        return this;
    }

    public void check() {
        if (this.immutable) {
            throw new IllegalArgumentException("Cannot modify cooldown value with name:" + this.getName() + "because cooldown field is immutable");
        }

        if (this.getCooldown() < -1) {
            this.setCooldown(0);
            LOGGER.error("Cooldown value with name: {} cannot be lower than -1", this.getName());
        }

    }

    public @Nullable String getName() {
        return this.name;
    }

    public float getCooldown() {
        return this.cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public String toString() {
        return "<float cooldown value: name = " + this.getName() + ", cooldown = " + this.getCooldown() + ">";
    }
}
