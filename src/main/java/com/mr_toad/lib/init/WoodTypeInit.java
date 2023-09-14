package com.mr_toad.lib.init;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

public class WoodTypeInit {

    private static final HashMap<String, WoodType> WOOD_TYPES = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public static void atlas() {
        for (WoodType woodType : WOOD_TYPES.values()) Sheets.addWoodType(woodType);
    }

    public static void addWoodType() {
        for (WoodType woodType : WOOD_TYPES.values()) WoodType.register(woodType);
    }

    public static synchronized WoodType addWoodType(WoodType woodType) {
        WOOD_TYPES.put(woodType.name(), woodType);
        return woodType;
    }
}
