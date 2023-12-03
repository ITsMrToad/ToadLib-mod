package com.mr_toad.lib.api.util.value;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;

import java.util.function.ToIntFunction;

public class BlockPropertyValues {

    public static Block.Properties plant() {
        return Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ);
    }

    public static Block.Properties sapling() {
        return Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS);
    }

    public static Block.Properties ladder() {
        return Block.Properties.of().mapColor(MapColor.WOOD).strength(0.4F).sound(SoundType.LADDER).noOcclusion();
    }

    public static Block.Properties pot() {
        return Block.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).instabreak().noOcclusion();
    }

    public static Block.Properties stone(float s, float s2) {
        return Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(s, s2);
    }

    public static Block.Properties stoneWithOuterSoundType(float s, float s2, SoundType type) {
        return Block.Properties.of().mapColor(MapColor.STONE).sound(type).strength(s, s2);
    }

    public static Block.Properties wood(float s, float s2) {
        return Block.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(s, s2);
    }

}



