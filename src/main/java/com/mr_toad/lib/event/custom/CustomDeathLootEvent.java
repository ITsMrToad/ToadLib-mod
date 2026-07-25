package com.mr_toad.lib.event.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface CustomDeathLootEvent {

    Event<@NotNull CustomDeathLootEvent> DROP_LOOT = EventFactory.createArrayBacked(CustomDeathLootEvent.class, callbacks -> (entity, level, source) -> {
        for (CustomDeathLootEvent event : callbacks) {
            event.drop(entity, level, source);
        }
    });

    void drop(LivingEntity entity, ServerLevel world, DamageSource source);

}
