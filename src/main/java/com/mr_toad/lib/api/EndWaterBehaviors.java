package com.mr_toad.lib.api;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;

@MethodsReturnNonnullByDefault
public enum EndWaterBehaviors implements StringRepresentable {
   
    NONE("none"),
    REGEN("regen"),
    ADD_EFFECT("add_effect"),
    HURT("hurt"),
    INSTANT_DEATH("instant_death");

    private final String nami;

    EndWaterBehaviors(String name) {
        this.nami = name;
        this.randomSwapped = number;
    }

    public static EndWaterBehaviors byName(String name) {
        return StringRepresentable.fromEnum(EndWaterBehaviors::values).byName(name, NONE);
    }

    @Override
    public String getSerializedName() {
        return this.nami;
    }
}
