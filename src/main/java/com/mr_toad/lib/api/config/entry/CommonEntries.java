package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.primitive.BigDecimalEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.math.BigDecimal;
import java.util.function.Function;

public class CommonEntries {

    ///@deprecated BigDecimal slow, use {@link com.mr_toad.lib.api.config.entry.primitive.FloatEntry} & {@link com.mr_toad.lib.api.config.entry.primitive.DoubleEntry} instead
    @Deprecated(since = "1.5.0")
    public static BigDecimalEntry createFloat(String name, float defaultValue) {
        return new BigDecimalEntry(name, BigDecimal.valueOf(defaultValue));
    }

    ///@deprecated BigDecimal slow, use {@link com.mr_toad.lib.api.config.entry.primitive.FloatEntry} & {@link com.mr_toad.lib.api.config.entry.primitive.DoubleEntry} instead
    @Deprecated(since = "1.5.0")
    public static BigDecimalEntry createDouble(String name, double defaultValue) {
        return new BigDecimalEntry(name, BigDecimal.valueOf(defaultValue));
    }

    public static<E extends Enum<E> & StringRepresentable> EnumEntry<E> createEnum(String name, E defaultValue, E[] values, Function<E, Component> naming, Codec<E> codec) {
        return new EnumEntry<>(name, defaultValue, values, codec, naming);
    }
}
