package com.mr_toad.lib.core.init;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

public class AddWoodTypeInit {

    private static final Map<String, WoodType> WOOD_TYPES = Maps.newHashMap();

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
