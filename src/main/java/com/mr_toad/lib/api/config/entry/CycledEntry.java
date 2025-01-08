package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.api.config.error.ConfigException;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.ArrayUtils;

import java.util.function.Function;

public class CycledEntry<C extends Enum<C>> extends ConfigEntry<C, CycledEntry<C>> {

    private final C[] values;
    private final Function<C, Component> naming;
    private int current;

    public CycledEntry(String name, C defaultValue, C[] values, Codec<C> codec, Function<C, Component> naming) {
        super(name, defaultValue, codec, ConfigEntryTypes.ENUM);
        this.values = values;
        this.naming = naming;
        if (!(defaultValue instanceof StringRepresentable)) {
            throw new ConfigException("'" + name + "' is not enum or not implements 'StringRepresentable' interface!");
        }

        if (values.length <= 1) {
            throw new ConfigException("Length of '" + name + "' must be greater than 1!");
        }

        this.current = ArrayUtils.indexOf(this.values, defaultValue);
    }

    @Override
    public C get() {
        return this.values[this.current];
    }

    @Override
    public void setValue(C value) {
        this.current = ArrayUtils.indexOf(this.values, value);
    }

    public C[] getValues() {
        return this.values;
    }

    public Function<C, Component> getNaming() {
        return this.naming;
    }

}

