package com.mr_toad.lib.api.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

@FunctionalInterface
public interface LoadablePersistentState<T extends SavedData> {
    T load(ServerLevel serverLevel, CompoundTag nbt);
}
