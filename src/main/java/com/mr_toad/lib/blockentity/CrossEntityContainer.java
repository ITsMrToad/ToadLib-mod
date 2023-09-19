package com.mr_toad.villageupgrade.common.block.entity;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;

public interface CrossEntityContainer extends Container {
    default void startEntityOpen(LivingEntity entity) {}
    default void stopEntityOpen(LivingEntity entity) {}
}
