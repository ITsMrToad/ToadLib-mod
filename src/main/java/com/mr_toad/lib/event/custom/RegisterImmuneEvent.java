package com.mr_toad.lib.event.custom;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RegisterImmuneEvent {

    Event<@NotNull RegisterImmuneEvent> IMMUNE_REGISTRY = EventFactory.createArrayBacked(RegisterImmuneEvent.class, callbacks -> (map, registerer, deleter) -> {
        for (RegisterImmuneEvent callback : callbacks) {
            callback.apply(map, registerer, deleter);
        }
    });

    void apply(Object2ObjectMap<EntityType<?>, MobEffect> map, Registerer<?> registerer, Deleter deleter);

    @FunctionalInterface
    interface Registerer<E extends LivingEntity> {
        void register(EntityType<@NotNull E> type, MobEffect effect);
    }

    @FunctionalInterface
    interface Deleter {
        void delete(EntityType<?> type, MobEffect effect);
    }
}
