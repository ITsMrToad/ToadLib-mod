package com.mr_toad.lib.api.event.custom;

import com.mr_toad.lib.api.util.LoadableSavedData;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.eventbus.api.Event;

import java.util.function.Function;

public final class RegisterSavedDataEvent extends Event {

    private final ServerLevel level;

    public RegisterSavedDataEvent(ServerLevel level) {
        this.level = level;
    }

    public<T extends SavedData, J extends LoadableSavedData<T>> void register(J loader, Function<ServerLevel, T> creator, String file) {
        ToadLib.SAVED_DATA_SUPPLIERS.put(file, () -> this.getDataStorage().computeIfAbsent(nbt -> loader.load(this.getLevel(), nbt), () -> creator.apply(this.getLevel()), file));
    }

    public DimensionDataStorage getDataStorage() {
        return this.getLevel().getDataStorage();
    }

    public ServerLevel getLevel() {
        return this.level;
    }
}
