package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.primitive.BigDecimalEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.math.BigDecimal;
import java.util.function.Function;

public class CommonEntries {

    public static BigDecimalEntry createFloat(String name, float defaultValue) {
        return new BigDecimalEntry(name, new BigDecimal(defaultValue));
    }

    public static BigDecimalEntry createDouble(String name, double defaultValue) {
        return new BigDecimalEntry(name, new BigDecimal(defaultValue));
    }

    public static<E extends Enum<E> & StringRepresentable> CycledEntry<E> createEnum(String name, E defaultValue, E[] values, Function<E, Component> naming, Codec<E> codec) {
        return new CycledEntry<>(name, defaultValue, values, codec, naming);
    }
}
