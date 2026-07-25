package com.mr_toad.lib.api.entity.entitydata;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.PathfinderMob;

import org.jetbrains.annotations.NotNull;

public interface BlowUpDataContainer extends EntityDataContainer {

    EntityDataAccessor<@NotNull Boolean> IS_IGNITED = SynchedEntityData.defineId(PathfinderMob.class, EntityDataSerializers.BOOLEAN);
    EntityDataAccessor<@NotNull Integer> SWELL_DIR = SynchedEntityData.defineId(PathfinderMob.class, EntityDataSerializers.INT);

    default void ignite() {
        this.getData().set(IS_IGNITED, true);
    }

    default boolean isIgnited() {
        return this.getData().get(IS_IGNITED);
    }

    default void setSwellDir(int swellDir) {
        this.getData().set(SWELL_DIR, swellDir);
    }

    default int getSwellDir() {
        return this.getData().get(SWELL_DIR);
    }

    default void define(SynchedEntityData.Builder builder) {
        builder.define(IS_IGNITED, false);
        builder.define(SWELL_DIR, -1);
    }
}
