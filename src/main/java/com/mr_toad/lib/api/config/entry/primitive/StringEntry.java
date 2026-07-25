package com.mr_toad.lib.api.config.entry.primitive;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class StringEntry extends ConfigEntry<String, StringEntry> {

    private Predicate<String> filter = Objects::nonNull;
    private BiFunction<String, Integer, FormattedText> provider = (s, i) -> FormattedText.of(s, Style.EMPTY);
    private int maxLength = 32;

    public StringEntry(String name, String defaultValue) {
        super(name, defaultValue, Codec.STRING, ConfigEntryTypes.STRING);
    }

    public StringEntry addFilter(Predicate<String> filter) {
        this.filter = filter;
        return this;
    }

    public StringEntry setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public StringEntry setProvider(BiFunction<String, Integer, FormattedText> provider) {
        this.provider = provider;
        return this;
    }

    public BiFunction<String, Integer, FormattedText> getProvider() {
        return this.provider;
    }

    public Predicate<String> getFilter() {
        return this.filter;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

}
