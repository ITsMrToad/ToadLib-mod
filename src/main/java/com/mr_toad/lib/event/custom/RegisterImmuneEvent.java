package com.mr_toad.lib.api.event.custom;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public final class RegisterImmuneEvent extends Event {

    private final Object2ObjectMap<EntityType<?>, MobEffect> type;

    public RegisterImmuneEvent(Object2ObjectMap<EntityType<?>, MobEffect> type) {
        this.type = type;
    }

    public<E extends LivingEntity> void register(EntityType<E> type, MobEffect effect) {
        this.type.put(type, effect);
    }

    public void delete(EntityType<?> type) {
        this.type.remove(type);
    }

    public void delete(EntityType<?> type, MobEffect effect) {
        this.type.remove(type, effect);
    }
}
