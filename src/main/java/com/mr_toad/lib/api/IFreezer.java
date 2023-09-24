package com.mr_toad.lib.api;

import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.Entity;

public interface IFreezer<T extends Entity> {

    @Nullable T getIceCube();

    void setIceCube(@Nullable T target);

}
