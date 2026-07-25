package com.mr_toad.lib.api.util.value;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class BlockPropertyValues {

    public static BlockBehaviour.Properties plant() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollision().instabreak().sound(SoundType.GRASS).offsetType(Block.OffsetType.XZ);
    }

    public static BlockBehaviour.Properties sapling() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollision().randomTicks().instabreak().sound(SoundType.GRASS);
    }

    public static BlockBehaviour.Properties ladder() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(0.4F).sound(SoundType.LADDER).noOcclusion();
    }

    public static BlockBehaviour.Properties pot() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).instabreak().noOcclusion();
    }

    public static BlockBehaviour.Properties stone(float s, float s2) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(s, s2);
    }

    public static BlockBehaviour.Properties stoneWithOuterSoundType(float s, float s2, SoundType type) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(type).strength(s, s2);
    }

    public static BlockBehaviour.Properties wood(float s, float s2) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(s, s2);
    }
}



