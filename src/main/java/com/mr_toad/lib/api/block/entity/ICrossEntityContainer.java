package com.mr_toad.lib.api.block.entity;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;

public interface ICrossEntityContainer extends Container {
    default void startEntityOpen(LivingEntity entity) {}
    default void stopEntityOpen(LivingEntity entity) {}
}