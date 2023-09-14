package com.mr_toad.lib.api.enums;

import net.minecraftforge.common.IExtensibleEnum;

public enum EndWaterBehaviors implements IExtensibleEnum {

    REGEN("regen"),
    ADD_EFFECT("add_effect"),
    HURT("hurt"),
    INSTANT_DEATH("instant_death");

    private final String n;


    EndWaterBehaviors(String name) {
        n = name;
    }

    public String getName(){
        return this.n;
    }




}
