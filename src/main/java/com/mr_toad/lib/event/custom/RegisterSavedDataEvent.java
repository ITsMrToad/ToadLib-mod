package com.mr_toad.lib.event.custom;

import com.mr_toad.lib.core.ToadLib;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RegisterSavedDataEvent {

    Event<@NotNull RegisterSavedDataEvent> REGISTER_SAVED_DATA = EventFactory.createArrayBacked(RegisterSavedDataEvent.class, callbacks -> (world) -> {
        for (RegisterSavedDataEvent event : callbacks) {
            event.apply(world);
        }
    });

    void apply(ServerLevel serverWorld);

    default<S extends SavedData> void register(String file, SavedDataType<@NotNull S> type, ServerLevel world) {
        ToadLib.SAVED_DATA_SUPPLIERS.put(file, () -> world.getDataStorage().computeIfAbsent(type));
    }
}
