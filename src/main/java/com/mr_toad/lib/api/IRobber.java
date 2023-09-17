package com.mr_toad.lib.api;

import net.minecraft.world.entity.LivingEntity;

public interface IRobber<T extends LivingEntity>{
    T getRobber();
  
    T setRobber(T robber);
}
