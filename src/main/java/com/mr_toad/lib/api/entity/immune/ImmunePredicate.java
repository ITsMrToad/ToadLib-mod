package com.mr_toad.lib.api.entity.immune;

import net.minecraft.world.entity.EntityType;

@FunctionalInterface
public interface ImmunePredicate<E> {
    ImmunePredicate<EntityType<?>> EMPTY = e -> true;

    boolean canGetImmune(E obj);
}
