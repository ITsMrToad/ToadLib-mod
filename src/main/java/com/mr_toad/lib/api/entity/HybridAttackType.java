package com.mr_toad.lib.api.entity;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;
import net.minecraftforge.common.IExtensibleEnum;

@MethodsReturnNonnullByDefault
public enum HybridAttackType implements StringRepresentable, IExtensibleEnum {

    MELEE("melee"),
    RANGED("ranged");

    public final String name;

    HybridAttackType(String name) {
        this.name = name;
    }

    public static HybridAttackType byName(String name) {
        return StringRepresentable.fromEnum(HybridAttackType::values).byName(name, MELEE);
    }

    public static HybridAttackType create(String exName, String name) {
        throw new IllegalStateException("Enum not extended");
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
