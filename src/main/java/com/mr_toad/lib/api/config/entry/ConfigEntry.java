package com.mr_toad.lib.api.config.entry;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryType;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.PrintWriter;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public abstract class ConfigEntry<T, E extends ConfigEntry<T, E>> {

    protected Component title = CommonComponents.EMPTY;
    protected Component description = CommonComponents.EMPTY;

    private boolean drawInScreen = true;

    public T value;

    protected final T defaultValue;
    protected final String name;

    private final ConfigEntryType type;
    private final Codec<T> codec;

    public ConfigEntry(String name, T defaultValue, Codec<T> codec, ConfigEntryType type) {
        this.defaultValue = defaultValue;
        this.name = name;
        this.type = type;
        this.codec = codec;
        this.value = this.defaultValue;
    }

    public E dontDrawInScreen() {
        this.drawInScreen = false;
        return (E) this;
    }

    public E addTitle(Component component) {
        this.title = component;
        return (E) this;
    }

    public E addDescription(Component component) {
        this.description = component;
        return (E) this;
    }

    public void save(PrintWriter writer) {
        DataResult<JsonElement> result = this.codec.encodeStart(JsonOps.INSTANCE, this.get());
        result.error().ifPresent(r -> ToadLib.LOGGER.error(ToadLib.CONFIG, "Failed to save '{}':{}", this.name, r.message()));
        result.result().ifPresent(r -> {
            writer.print(this.name);
            writer.print(":");
            writer.println(r);
        });
    }

    public void load(JsonElement element) {
        DataResult<T> result = this.codec.parse(JsonOps.INSTANCE, element);
        result.error().ifPresent(r -> ToadLib.LOGGER.error(ToadLib.CONFIG, "Error parsing '{}':{}", this.name, r.message()));
        result.result().ifPresent(this::setValue);
    }

    public T get() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void resetValue() {
        this.setValue(this.defaultValue);
    }

    public ConfigEntryType getType() {
        return this.type;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public final boolean drawInScreen() {
        return this.drawInScreen;
    }

    public final boolean isDefault() {
        return this.get().equals(this.getDefaultValue());
    }

    public Component getTitle() {
        if (this.title == CommonComponents.EMPTY) {
            return Component.literal(this.name);
        }
        return this.title;
    }

    public Component getDescription() {
        return this.description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        } else if (this == obj) {
            return true;
        } else {
            ConfigEntry<?, ?> other = (ConfigEntry<?, ?>) obj;
            return Objects.equals(this.name, other.name);
        }
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
