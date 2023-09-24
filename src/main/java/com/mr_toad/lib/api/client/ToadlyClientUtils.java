package com.mr_toad.lib.api.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ToadlyClientUtils {

    public static ModelLayerLocation addMain(String modid, String name) {
        return register(modid, name, "main");
    }


    public static ModelLayerLocation register(String modid, String name, String layer) {
        return new ModelLayerLocation(new ResourceLocation(modid, name), layer);
    }
}
