package com.mr_toad.lib.api.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public abstract class CrossEntityOpenersCounter extends ContainerOpenersCounter {

    public void incrementEntityOpeners(LivingEntity entity, Level world, BlockPos blockPos, BlockState state) {
        int i = this.openCount++;
        if (i == 0) {
            this.onOpen(world, blockPos, state);
            world.gameEvent(entity, GameEvent.CONTAINER_OPEN, blockPos);
            scheduleRecheck(world, blockPos, state);
        }

        this.openerCountChanged(world, blockPos, state, i, this.openCount);
    }

    public void decrementEntityOpeners(LivingEntity entity, Level world, BlockPos blockPos, BlockState state) {
        int i = this.openCount--;
        if (this.openCount == 0) {
            this.onClose(world, blockPos, state);
            world.gameEvent(entity, GameEvent.CONTAINER_CLOSE, blockPos);
        }

        this.openerCountChanged(world, blockPos, state, i, this.openCount);
    }
}
