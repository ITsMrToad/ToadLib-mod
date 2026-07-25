package com.mr_toad.lib.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.ToIntFunction;

public class ToadBlockUtils {

    public static void registerCompostables(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    private static boolean isTouchesLiquid(BlockGetter world, BlockPos pos) {
        boolean flag = false;
        BlockPos.MutableBlockPos mutable = pos.mutable();
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            BlockState blockState = world.getBlockState(mutable);
            if (direction != Direction.DOWN || blockState.getFluidState().is(FluidTags.WATER)) {
                mutable.setWithOffset(pos, direction);
                blockState = world.getBlockState(mutable);
                if (blockState.getFluidState().is(FluidTags.WATER) && !blockState.isFaceSturdy(world, pos, direction.getOpposite())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public static ToIntFunction<BlockState> litBlockEmission(int llvl) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? llvl : 0;
    }

}
