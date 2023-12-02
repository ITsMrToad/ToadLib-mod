package com.mr_toad.lib.api.util.value;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlockPropertyValues {

    public static Block.Properties plant() {
        return Block.Properties.of(Material.PLANT, MaterialColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ);
    }

    public static Block.Properties sapling() {
        return Block.Properties.of(Material.PLANT, MaterialColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS);
    }

    public static Block.Properties ladder() {
        return Block.Properties.of(Material.DECORATION, MaterialColor.WOOD).strength(0.4F).sound(SoundType.LADDER).noOcclusion();
    }

    public static Block.Properties pot() {
        return Block.Properties.of(Material.DECORATION).instabreak().noOcclusion();
    }

    public static Block.Properties stone(float s, float s2) {
        return Block.Properties.of(Material.STONE, MaterialColor.STONE).sound(SoundType.STONE).strength(s, s2);
    }

    public static Block.Properties stoneWithOuterSoundType(float s, float s2, SoundType type) {
        return Block.Properties.of(Material.STONE, MaterialColor.STONE).sound(type).strength(s, s2);
    }

    public static Block.Properties wood(float s, float s2) {
        return Block.Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).strength(s, s2);
    }

}



