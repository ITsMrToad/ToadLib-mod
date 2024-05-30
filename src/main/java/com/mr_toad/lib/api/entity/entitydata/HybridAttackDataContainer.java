package com.mr_toad.lib.api.entity.entitydata;

import com.mr_toad.lib.api.entity.HybridAttackType;
import net.minecraft.nbt.CompoundTag;

public interface HybridAttackDataContainer {

    HybridAttackType getAttackType();

    void setAttackType(HybridAttackType type);

    default void saveAttackType(CompoundTag nbt) {
        nbt.putString("HybridAttackType", this.getAttackType().getSerializedName());
    }

    default void loadAttackType(CompoundTag nbt) {
        this.setAttackType(HybridAttackType.byName(nbt.getString("HybridAttackType")));
    }
}
