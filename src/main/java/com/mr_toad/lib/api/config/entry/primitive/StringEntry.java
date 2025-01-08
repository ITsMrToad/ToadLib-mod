package com.mr_toad.lib.api.config.entry.primitive;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class StringEntry extends ConfigEntry<String, StringEntry> {

    private BiFunction<String, Integer, FormattedCharSequence> formatter = (s, i) -> FormattedCharSequence.forward(s, Style.EMPTY);
    private Predicate<String> filter = Objects::nonNull;
    private int maxLength = 24;

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

    public StringEntry addFormatter(BiFunction<String, Integer, FormattedCharSequence> formatter) {
        this.formatter = formatter;
        return this;
    }

    public BiFunction<String, Integer, FormattedCharSequence> getFormatter() {
        return this.formatter;
    }

    public Predicate<String> getFilter() {
        return this.filter;
    }

    public int getMaxLength() {
        return this.maxLength;
    }
}
