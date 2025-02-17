package com.mr_toad.lib.api.helper.registry;

import com.mr_toad.lib.api.helper.registry.common.IdRegistry;
import com.mr_toad.lib.api.helper.registry.common.ValueHolder;
import com.mr_toad.lib.mtjava.bytes.OptionalByte;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import org.apache.commons.lang3.Validate;

import org.jetbrains.annotations.Nullable;

public class SmallIdRegistry<O> implements IdRegistry<Byte, O, Byte2ObjectMap<ValueHolder<O>>> {

    private final Byte2ObjectMap<ValueHolder<O>> registryMap = new Byte2ObjectOpenHashMap<>();

    private byte defaultValue = -1;
    private byte nextId = 0;

    private final String regName;

    public SmallIdRegistry(String regName) {
        Validate.notEmpty(regName, "RegName cannot be empty!");
        this.regName = regName;
    }

    @Override
    public Byte2ObjectMap<ValueHolder<O>> registry() {
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
        this.nextId = 0;
        this.defaultValue = -1;
    }

    public void declareDefault() {
        this.declareDefault((byte) 0);
    }

    public void declareDefault(byte id) {
        this.defaultValue = id;
    }

    public ValueHolder<O> register(O obj) {
        ValueHolder<O> holder = new ValueHolder<>(obj);
        this.register(holder);
        return holder;
    }

    public ValueHolder<O> register(ValueHolder<O> obj) {
        this.registryMap.put(this.nextId++, obj);
        return obj;
    }

    public ValueHolder<O> register(byte id, O obj) {
        ValueHolder<O> holder = new ValueHolder<>(obj);
        this.register(id, holder);
        return holder;
    }

    public ValueHolder<O> register(byte id, ValueHolder<O> obj) {
        if (this.registryMap.containsKey(id)) {
            throw new IllegalArgumentException("'" + this.regName + "' already have object with id: '" + id + "'");
        }
        this.registryMap.put(id, obj);
        return obj;
    }

    public OptionalByte getDefaultId() {
        return this.defaultValue == -1 ? OptionalByte.empty() : OptionalByte.of(this.defaultValue);
    }

    public byte nextId() {
        while (this.hasId(this.nextId)) {
            this.nextId++;
        }
        return this.nextId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof SmallIdRegistry<?> other) {
            if (!this.regName.equals(other.regName())) {
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
        int i = this.nextId;
        i = i * 31;
        return i + this.registryMap.values().stream().mapToInt(ValueHolder::hashCode).sum() * 31;
    }

    @Override
    public String toString() {
        return this.regName;
    }
}
