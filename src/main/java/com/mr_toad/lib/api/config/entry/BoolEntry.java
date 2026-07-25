package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;

public class BoolEntry extends ConfigEntry<Boolean, BoolEntry> {
    public BoolEntry(String name, Boolean defaultValue) {
        super(name, defaultValue, Codec.BOOL, ConfigEntryTypes.BOOL);
    }
}

