package com.mr_toad.lib.api.util;

import com.mr_toad.lib.api.entity.EntityDataContainer;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.PathfinderMob;

public interface BlowUpDataContainer extends EntityDataContainer {

    EntityDataAccessor<Boolean> IS_IGNITED = SynchedEntityData.defineId(PathfinderMob.class, EntityDataSerializers.BOOLEAN);
    EntityDataAccessor<Integer> SWELL_DIR = SynchedEntityData.defineId(PathfinderMob.class, EntityDataSerializers.INT);

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

    default void defineBlowUpData() {
        this.getData().define(IS_IGNITED, false);
        this.getData().define(SWELL_DIR, -1);
    }

}
