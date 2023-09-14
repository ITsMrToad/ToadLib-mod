package com.mr_toad.lib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class GoalHelper {

    public static void setGateOpen(@Nullable Entity entity, Level w, BlockState state, BlockPos p, boolean b) {
        FenceGateBlock gateBlock = (FenceGateBlock) state.getBlock();

        if (state.is(gateBlock) && state.getValue(FenceGateBlock.OPEN) != b) {
            w.setBlock(p, state.setValue(FenceGateBlock.OPEN, b), 10);

            if (b) {
                int i = 1008;
                w.levelEvent((Player) entity, i, p, 0);
            } else {
                int j = 1014;
                w.levelEvent((Player) entity, j, p, 0);
            }

            w.gameEvent(entity, b ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, p);
        }
    }

    public static boolean isFenceGateOpen(BlockState state) {
        return state.getValue(FenceGateBlock.OPEN);
    }

}
