package com.mr_toad.lib.api.gen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.material.Material;

public class ToadFeatureUtils {

    public static void placeLog(LevelWriter level, BlockPos pos, RandomSource rand, TreeConfiguration config) {
        setState(level, pos, config.trunkProvider.getState(rand, pos));
    }

    public static void placeLogWithDir(LevelWriter level, BlockPos pos, Direction direction, RandomSource rand, TreeConfiguration config) {
        setState(level, pos, config.trunkProvider.getState(rand, pos).setValue(RotatedPillarBlock.AXIS, direction.getAxis()));
    }

    public static boolean containsTag(LevelSimulatedReader level, BlockPos pos, TagKey<Block> tag) {
        return level.isStateAtPosition(pos, (block) -> block.is(tag));
    }

    public static void setState(LevelWriter level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state, 19);
    }

    public static boolean isReplaceablePlant(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (state) -> state.getMaterial() == Material.REPLACEABLE_PLANT);
    }

    public static boolean isReplaceableFireproofPlant(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (state) -> state.getMaterial() == Material.REPLACEABLE_FIREPROOF_PLANT);
    }

    public static boolean isReplaceableWaterPlant(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (state) -> state.getMaterial() == Material.REPLACEABLE_WATER_PLANT);
    }

    public static boolean isLog(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (state) -> state.is(BlockTags.LOGS));
    }

    public static boolean isLeaves(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (state) -> state.is(BlockTags.LEAVES));
    }

    public static boolean isAirOrLeaves(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (state) -> state.isAir() || state.is(BlockTags.LEAVES));
    }

    public static boolean mayBePlaceOn(LevelAccessor level, BlockPos pos, SaplingBlock sapling) {
        return level.getBlockState(pos).canSustainPlant(level, pos, Direction.UP, sapling);
    }
}
