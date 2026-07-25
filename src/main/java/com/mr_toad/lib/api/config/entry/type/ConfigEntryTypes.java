package com.mr_toad.lib.api.config.entry.type;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.network.chat.Component;

import java.math.BigDecimal;
import java.util.Optional;

public class ConfigEntryTypes {
    
    private static final Object2ObjectMap<String, ConfigEntryType<?>> TYPES = new Object2ObjectLinkedOpenHashMap<>();

    public static final ConfigEntryType<Byte> BYTE = registerType("byte");
    public static final ConfigEntryType<Short> SHORT = registerType("short");
    public static final ConfigEntryType<Integer> INT = registerType("int");
    public static final ConfigEntryType<Long> LONG = registerType("long");
    public static final ConfigEntryType<Boolean> BOOL = registerType("bool");
    ///@deprecated idk
    @Deprecated(since = "1.5.0")
    public static final ConfigEntryType<BigDecimal> BIG_DECIMAL = registerType("double_float");
    public static final ConfigEntryType<Float> FLOAT = registerType("float");
    public static final ConfigEntryType<Double> DOUBLE = registerType("double");
    public static final ConfigEntryType<Float> DEGREE = registerType("degree");
    public static final ConfigEntryType<Double> PERCENT = registerType("percent");
    public static final ConfigEntryType<String> STRING = registerType("string");
    public static final ConfigEntryType<Integer> COLOR = registerType("color");
    public static final ConfigEntryType<Enum<?>> ENUM = registerType("enum");
    public static final ConfigEntryType<Component> PAGE = registerType("page");

    public static<T> ConfigEntryType<T> registerType(String name) {
        ConfigEntryType<T> type = new ConfigEntryType<>(name);
        TYPES.put(name, type);
        return type;
    }

    public static Optional<ConfigEntryType<?>> byName(String name) {
        return Optional.ofNullable(TYPES.get(name));
    }
}
