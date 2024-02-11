package com.mr_toad.lib.api.entity.entitydata;

import net.minecraft.network.syncher.SynchedEntityData;
import org.jetbrains.annotations.NotNull;

public interface EntityDataContainer {
    @NotNull SynchedEntityData getData();
}
