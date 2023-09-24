package com.mr_toad.lib.api;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;
import net.minecraftforge.common.IExtensibleEnum;

@MethodsReturnNonnullByDefault
public enum EndWaterBehaviors implements StringRepresentable, IExtensibleEnum {

    REGEN("regen", 5),
    ADD_EFFECT("add_effect", 20),
    HURT("hurt", 10),
    INSTANT_DEATH("instant_death", 2);

    private final String nami;
    private final int randomSwapped;

    EndWaterBehaviors(String name, int number) {
        this.nami = name;
        this.randomSwapped = number;
    }

    public int getRandomSwapped() {
        return this.randomSwapped;
    }

    @Override
    public String getSerializedName() {
        return this.nami;
    }


}
