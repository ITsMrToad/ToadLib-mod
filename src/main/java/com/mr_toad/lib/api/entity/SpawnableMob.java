package com.mr_toad.lib.api.entity;

import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;

public interface SpawnableMob<E extends Mob> {
    @Nullable E getOwner();
    void setOwner(@Nullable E entity);

}
