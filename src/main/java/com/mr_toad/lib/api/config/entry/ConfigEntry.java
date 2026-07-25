package com.mr_toad.lib.api.config.entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryType;
import com.mr_toad.lib.api.config.util.DeprecationRule;
import com.mr_toad.lib.api.config.util.HighlightWarning;
import com.mr_toad.lib.api.config.util.PerformanceImpact;
import com.mr_toad.lib.api.config.util.TooltipSuffix;
import com.mr_toad.lib.api.config.util.TooltipSuffixV2;
import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class ConfigEntry<T, E extends ConfigEntry<T, E>> {

    private static final TooltipSuffixV2 RESOURCE_RELOAD = new TooltipSuffixV2(Component.translatable("toadconfig.auto_resource_reload"), 1);

    protected final ObjectList<Callback<ConfigEntry<T, E>, T>> changeCallbacks = new ObjectArrayList<>();
    protected final ObjectList<Callback<ConfigEntry<T, E>, T>> loadCallbacks = new ObjectArrayList<>();
    protected final ObjectList<Callback<ConfigEntry<T, E>, T>> resetCallbacks = new ObjectArrayList<>();
    protected final ObjectList<DeprecationRule<T>> deprecations = new ObjectArrayList<>();
    protected final ObjectList<TooltipSuffix> suffixes = new ObjectArrayList<>();

    protected Component title = CommonComponents.EMPTY;
    protected Component description = CommonComponents.EMPTY;

    private boolean drawInScreen = true;

    public T value;

    protected final T defaultValue;
    protected final String name;

    private final ConfigEntryType<T> type;
    private final Codec<T> codec;

    protected ConfigEntry(String name, T defaultValue, Codec<T> codec, ConfigEntryType<T> type) {
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

    public E reloadResource() {
        this.suffixes.add(RESOURCE_RELOAD);
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

    public E withWarning(HighlightWarning warning) {
        this.suffixes.add(warning);
        return (E) this;
    }

    public E withPerformanceImpact(PerformanceImpact impact) {
        this.suffixes.add(impact);
        return (E) this;
    }

    public E withTooltipSuffix(TooltipSuffix suffix) {
        this.suffixes.add(suffix);
        return (E) this;
    }

    public E addDeprecationRule(DeprecationRule<T> rule) {
        this.deprecations.add(rule);
        return (E) this;
    }

    public E addDeprecationRule(DeprecationRule<T>... rules) {
        Collections.addAll(this.deprecations, rules);
        return (E) this;
    }

    public E addChangeCallback(Callback<ConfigEntry<T, E>, T> callback) {
        this.changeCallbacks.add(callback);
        return (E) this;
    }

    public E addChangeCallback(Callback<ConfigEntry<T, E>, T>... callbacks) {
        Collections.addAll(this.changeCallbacks, callbacks);
        return (E) this;
    }

    public E addLoadCallback(Callback<ConfigEntry<T, E>, T> callback) {
        this.loadCallbacks.add(callback);
        return (E) this;
    }

    public E addLoadCallback(Callback<ConfigEntry<T, E>, T>... callbacks) {
        Collections.addAll(this.loadCallbacks, callbacks);
        return (E) this;
    }

    public E addResetCallback(Callback<ConfigEntry<T, E>, T> callback) {
        this.resetCallbacks.add(callback);
        return (E) this;
    }

    public E addResetCallback(Callback<ConfigEntry<T, E>, T>... callbacks) {
        Collections.addAll(this.resetCallbacks, callbacks);
        return (E) this;
    }

    public void save(JsonObject object) {
        DataResult<JsonElement> result = this.codec.encodeStart(JsonOps.INSTANCE, this.get());
        result.error().ifPresent(r -> ToadLib.LOGGER.error(ToadLib.CONFIG, "Failed to save '{}':{}", this.name, r.message()));
        result.result().ifPresent(r -> {
            object.add(this.name, r);
            if (ToadLib.printDebug()) {
                ToadLib.LOGGER.info(ToadLib.CONFIG, "Saved '{}', '{}'", this, this.get());
            }
        });
    }

    public void load(JsonElement element) {
        DataResult<T> result = this.codec.parse(JsonOps.INSTANCE, element);
        result.error().ifPresent(r -> ToadLib.LOGGER.error(ToadLib.CONFIG, "Error parsing '{}':{}", this.name, r.message()));
        result.result().ifPresent(this::loadValue);
    }

    public T get() {
        return this.value;
    }

    public T getDisplayValue() {
        Optional<DeprecationRule<T>> opt = this.deprecations.stream().filter(d -> d.hasDisplayValue() && d.isActive()).findFirst();
        if (opt.isPresent()) {
            return opt.get().getDisplayValue();
        }
        return this.value;
    }

    public void setValue(T value) {
        for (Callback<ConfigEntry<T, E>, T> callback : this.changeCallbacks) {
            callback.call(this, this.value, value);
        }
        this.value = value;
    }

    public void resetValue() {
        for (Callback<ConfigEntry<T, E>, T> callback : this.resetCallbacks) {
            callback.call(this, this.value, this.defaultValue);
        }
        this.setValue(this.defaultValue);
    }

    protected void loadValue(T value) {
        for (Callback<ConfigEntry<T, E>, T> callback : this.loadCallbacks) {
            callback.call(this, this.value, value);
        }
        this.value = value;
    }

    public ConfigEntryType<T> getType() {
        return this.type;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public boolean drawInScreen() {
        return this.drawInScreen;
    }

    public boolean isDefault() {
        return this.get().equals(this.getDefaultValue());
    }

    public boolean isDeprecated() {
        return !this.deprecations.isEmpty() && this.deprecations.stream().anyMatch(DeprecationRule::isActive);
    }

    public Component getTitle() {
        if (Objects.equals(this.title, Component.empty())) {
            return Component.literal(this.name);
        }
        return this.title;
    }

    @Nullable
    public Component getDescription() {
        Optional<Component> text = this.deprecations.stream().filter(DeprecationRule::isActive).map(DeprecationRule::getTooltip).filter(t -> t != CommonComponents.EMPTY).findFirst();
        if (text.isPresent()) {
            return text.get();
        }

        Component c = this.getBaseTooltip();
        if (c == CommonComponents.EMPTY) {
            if (this.suffixes.isEmpty()) {
                return null;
            } else if (this.suffixes.size() == 1) {
                return this.suffixes.getFirst().tooltip();
            } else {
                return CommonComponents.joinLines(this.suffixes.stream().map(TooltipSuffix::tooltip).toList());
            }
        } else {
            if (!this.suffixes.isEmpty()) {
                c = CommonComponents.joinLines(c, CommonComponents.NEW_LINE);
            } else {
                return c;
            }
            for (TooltipSuffix t : this.suffixes) {
                c = CommonComponents.joinLines(c, t.tooltip());
            }
        }
        return c;
    }

    protected Component getBaseTooltip() {
        return this.description;
    }

    public boolean mustReloadResource() {
        return this.suffixes.contains(RESOURCE_RELOAD);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConfigEntry<?,?> other)) {
            return false;
        } else if (this == obj) {
            return true;
        } else {
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

    @FunctionalInterface
    public interface Callback<E, T> {
        void call(E entry, T old, T current);
    }
}
