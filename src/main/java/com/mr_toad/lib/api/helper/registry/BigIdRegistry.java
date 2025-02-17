package com.mr_toad.lib.api.helper.registry;

import com.mr_toad.lib.api.helper.registry.common.IdRegistry;
import com.mr_toad.lib.api.helper.registry.common.ValueHolder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.apache.commons.lang3.Validate;

import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

public class BigIdRegistry<O> implements IdRegistry<Integer, O, Int2ObjectMap<ValueHolder<O>>> {

    private final Int2ObjectMap<ValueHolder<O>> registryMap = new Int2ObjectOpenHashMap<>();

    private int defaultValue = -1;

    private final AtomicInteger ids;
    private final String regName;

    public BigIdRegistry(String regName) {
        Validate.notEmpty(regName, "RegName cannot be empty!");
        this.regName = regName;
        this.ids = new AtomicInteger(0);
    }

    @Override
    public Int2ObjectMap<ValueHolder<O>> registry() {
        return this.registryMap;
    }

    @Override
    public String regName() {
        return this.regName;
    }

    @Override
    public @Nullable ValueHolder<O> defaultValue() {
        if (this.defaultValue == -1) {
            return null;
        } else {
            return this.getOrThrow(this.defaultValue);
        }
    }

    @Override
    public void destroyRegistry() {
        IdRegistry.super.destroyRegistry();
        this.ids.set(0);
        this.defaultValue = -1;
    }

    public BigIdRegistry<O> declareDefault() {
        return this.declareDefault(0);
    }

    public BigIdRegistry<O> declareDefault(int id) {
        this.defaultValue = id;
        return this;
    }

    public ValueHolder<O> register(O obj) {
        return this.register(new ValueHolder<>(obj));
    }

    public ValueHolder<O> register(ValueHolder<O> obj) {
        return this.registryMap.putIfAbsent(this.ids.getAndIncrement(), obj);
    }

    public ValueHolder<O> register(int id, O obj) {
        return this.register(id, new ValueHolder<>(obj));
    }

    public ValueHolder<O> register(int id, ValueHolder<O> obj) {
        if (this.registryMap.containsKey(id)) {
            throw new IllegalArgumentException("'" + this.regName + "' already have object with id: '" + id + "'");
        }
        return this.registryMap.putIfAbsent(id, obj);
    }

    public int nextValidId() {
        return this.ids.get();
    }

    public Optional<ValueHolder<O>> getDefault() {
        return Optional.ofNullable(this.defaultValue());
    }

    public OptionalInt getDefaultId() {
        return this.defaultValue == -1 ? OptionalInt.empty() : OptionalInt.of(this.defaultValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof BigIdRegistry<?> other) {
            if (!this.regName.equals(other.regName)) {
                return false;
            } else {
                if (this.size() == other.size()) {
                    return this.values().equals(other.values());
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int i = this.ids.hashCode();
        i = i * 31;
        return i + this.registryMap.values().stream().mapToInt(ValueHolder::hashCode).sum() * 31;
    }

    @Override
    public String toString() {
        return this.regName;
    }
}
