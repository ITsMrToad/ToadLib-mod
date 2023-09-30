package com.mr_toad.lib.api.entity;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface IFreezer<T extends Entity> {
    @Nullable T getIceCube();

    void setIceCube(@Nullable T var1);
}
