package com.mr_toad.lib.api.value;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.ToIntFunction;

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

    public static Block.Properties wood(float s, float st2) {
        return Block.Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).strength(s, st2);
    }

    private static boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
        return true;
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static boolean always(BlockState state, BlockGetter getter, BlockPos blockPos, EntityType<?> entityType) {
        return true;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int llvl) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? llvl : 0;
    }
}



