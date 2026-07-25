package com.mr_toad.lib.event.custom;

import com.mr_toad.lib.api.util.SpawnerHolder;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.List;

@FunctionalInterface
public interface RegisterCustomSpawnersEvent {

    Event<RegisterCustomSpawnersEvent> SPAWNER_REGISTRY = EventFactory.createArrayBacked(RegisterCustomSpawnersEvent.class, callbacks -> (list) -> {
        for (RegisterCustomSpawnersEvent callback : callbacks) {
            callback.apply(list);
        }
    });

    void apply(List<SpawnerHolder> list);
}
