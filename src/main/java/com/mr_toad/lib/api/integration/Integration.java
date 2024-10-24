package com.mr_toad.lib.api.integration;

import net.minecraftforge.fml.ModList;

@FunctionalInterface
public interface Integration {

    String modid();

    default String findLocation(String s) {
        return this.modid() + ":" + s;
    }

    default boolean isLoaded() {
        return ModList.get().isLoaded(this.modid());
    }

}
