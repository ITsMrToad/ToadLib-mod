package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryType;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.api.config.error.ConfigException;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.ArrayUtils;

import org.jetbrains.annotations.Nullable;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class EnumEntry<C extends Enum<C>> extends ConfigEntry<C, EnumEntry<C>> {

    private final C[] values;
    private final Function<C, Component> naming;

    @Nullable private Function<C, Component> variousTooltips = null;

    private int current;

    public EnumEntry(String name, C defaultValue, C[] values, Codec<C> codec, Function<C, Component> naming) {
        super(name, defaultValue, codec, (ConfigEntryType<C>) ConfigEntryTypes.ENUM);
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
        for (Callback<ConfigEntry<C, EnumEntry<C>>, C> callback : this.changeCallbacks) {
            callback.call(this, this.get(), value);
        }
        this.current = ArrayUtils.indexOf(this.values, value);
    }

    @Override
    protected void loadValue(C value) {
        for (Callback<ConfigEntry<C, EnumEntry<C>>, C> callback : this.loadCallbacks) {
            callback.call(this, this.get(), value);
        }
        this.current = ArrayUtils.indexOf(this.values, value);
    }

    @Override
    protected Component getBaseTooltip() {
        if (this.variousTooltips != null) {
            return this.variousTooltips.apply(this.get());
        }
        return super.getBaseTooltip();
    }

    public EnumEntry<C> addVariousTooltip(Function<C, Component> func) {
        this.variousTooltips = func;
        return this;
    }

    public C[] getValues() {
        return this.values;
    }

    public Function<C, Component> getNaming() {
        return this.naming;
    }
}

