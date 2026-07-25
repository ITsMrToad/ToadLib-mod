package com.mr_toad.lib.api.helper.registry;

import com.mr_toad.lib.api.helper.registry.common.RegistryMemberException;
import com.mr_toad.lib.api.helper.registry.common.ToadRegistry;
import com.mr_toad.lib.api.helper.registry.common.ValueHolder;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.strings.OptionalString;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Optional;

public class NameableRegistry<O> implements ToadRegistry<String, O, Object2ObjectMap<String, ValueHolder<O>>> {

    private final Object2ObjectMap<String, ValueHolder<O>> registry = new Object2ObjectOpenHashMap<>();

    private String defaultValue = "";

    private final String regName;

    public NameableRegistry(String regName) {
        Validate.notEmpty(regName, "RegName cannot be empty!");
        this.regName = regName;
    }

    @Override
    public Object2ObjectMap<String, ValueHolder<O>> registry() {
        return this.registry;
    }

    @Override
    public void destroyRegistry() {
        ToadRegistry.super.destroyRegistry();
        this.defaultValue = "";
    }

    public NameableRegistry<O> declareDefault(String name) {
        Validate.notEmpty(name, "Default of '" + this.regName + "' cannot be empty!");
        this.defaultValue = name;
        return this;
    }

    public ValueHolder<O> register(String name, O obj) {
        Validate.notEmpty(name, "Name of '" + obj + "' cannot be empty!");
        ValueHolder<O> holder = new ValueHolder<>(obj);

        if (this.registry.containsKey(name)) {
            throw new RegistryMemberException("'" + this.regName + "' already have object with name: '" + name + "'");
        }

        this.registry.put(name, holder);
        return holder;
    }

    public ValueHolder<O> getOrDefault(String name) {
        Validate.notEmpty(name, "Name cannot be empty!");
        ValueHolder<O> holder = this.registry.get(name);
        if (holder == null) {
            if (this.getDefault().isPresent()) {
                ToadLib.LOGGER.error("Member with name: '{}' not found in '{}'! Received as default.", name, this.regName);
                return this.getDefault().get();
            } else {
                throw new RegistryMemberException("Default target not declared!");
            }
        }
        return holder;
    }

    public ValueHolder<O> getOrThrow(String name) {
        Validate.notEmpty(name, "Name cannot be empty!");
        if (!this.registry.containsKey(name)) {
            throw new RegistryMemberException("Member of '" + this.regName + "' with name:" + name + "doesn't exists!");
        }
        return this.registry.get(name);
    }

    public String getName(O obj) {
        if (!this.values().contains(obj)) {
            throw new RegistryMemberException("Name of '" + obj  + "' in '" + this.regName + "' not found!");
        }
        return this.inverseValues().get(obj);
    }

    public String getName(ValueHolder<O> obj) {
        if (!this.registry.containsValue(obj)) {
            throw new RegistryMemberException("Name of '" + obj  + "' in '" + this.regName + "' not found!");
        }
        return this.inverse().get(obj);
    }

    public ObjectSet<String> keys() {
        return this.registry.keySet();
    }

    public boolean replace(String name, O newValue) {
        return this.replace(name, new ValueHolder<>(newValue));
    }

    public boolean replace(String name, ValueHolder<O> newValue) {
        Validate.notEmpty(name, "Name of '" + newValue + "' cannot be empty!");
        return this.registry.replace(name, this.getOrThrow(name), newValue);
    }

    public OptionalString getDefaultName() {
        return StringUtils.isEmpty(this.defaultValue) ? OptionalString.empty() : OptionalString.of(this.defaultValue);
    }

    public Optional<ValueHolder<O>> getDefault() {
        if (StringUtils.isEmpty(this.defaultValue)) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.getOrThrow(this.defaultValue));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof NameableRegistry<?> other) {
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
        return this.registry.values().stream().mapToInt(ValueHolder::hashCode).sum() * 31;
    }

    @Override
    public String toString() {
        return this.regName;
    }
}
