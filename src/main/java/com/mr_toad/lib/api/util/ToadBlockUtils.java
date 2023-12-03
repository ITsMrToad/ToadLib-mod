package com.mr_toad.lib.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ToadBlockUtils {

    public static void registerFlammables(Block block, int spread, int flammability) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, spread, flammability);
    }

    public static void registerCompostables(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static boolean isTouchesLiquid(BlockGetter getter, BlockPos blockPos, BlockState state) {

        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = blockPos.mutable();

        for(Direction direction : Direction.values()) {
            BlockState blockstate = getter.getBlockState(blockpos$mutableblockpos);
            if (direction != Direction.DOWN || state.canBeHydrated(getter, blockPos, blockstate.getFluidState(), blockpos$mutableblockpos)) {
                blockpos$mutableblockpos.setWithOffset(blockPos, direction);
                blockstate = getter.getBlockState(blockpos$mutableblockpos);
                if (state.canBeHydrated(getter, blockPos, blockstate.getFluidState(), blockpos$mutableblockpos) && !blockstate.isFaceSturdy(getter, blockPos, direction.getOpposite())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    public static boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
        return true;
    }

    public static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    public static boolean never(BlockState state, BlockGetter getter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    public static boolean always(BlockState state, BlockGetter getter, BlockPos blockPos, EntityType<?> entityType) {
        return true;
    }

    public static ToIntFunction<BlockState> litBlockEmission(int llvl) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? llvl : 0;
    }


}
