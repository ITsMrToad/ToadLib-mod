package com.mr_toad.lib.api.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import org.jetbrains.annotations.NotNull;
import java.util.function.IntFunction;

public enum HybridAttackType implements StringRepresentable {

    MELEE("melee", 0),
    RANGED("ranged", 1);

    public static final IntFunction<HybridAttackType> ID2VALUE = ByIdMap.continuous(HybridAttackType::getId, HybridAttackType.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<@NotNull ByteBuf, @NotNull HybridAttackType> PACKET_CODEC = ByteBufCodecs.idMapper(ID2VALUE, HybridAttackType::getId);

    public final String name;
    public final int id;

    HybridAttackType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static HybridAttackType byName(String name) {
        return StringRepresentable.fromEnum(HybridAttackType::values).byName(name, MELEE);
    }

    public int getId() {
        return this.id;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
