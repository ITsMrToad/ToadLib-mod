package com.mr_toad.lib.api.helper.registry.common;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mr_toad.lib.mtjava.MtJava;

import java.util.Map;

public interface ToadRegistry<N, O, M extends Map<N, ValueHolder<O>>> {

    M registry();

    default ImmutableSet<O> values() {
        return ImmutableSet.copyOf(this.registry().values().stream().map(ValueHolder::get).iterator());
    }

    default ImmutableSet<ValueHolder<O>> valueHolders() {
        return ImmutableSet.copyOf(this.registry().values());
    }

    default ImmutableMap<O, N> inverseValues() {
        ImmutableMap.Builder<O, N> builder = ImmutableMap.builder();
        this.inverse().forEach((holder, id) -> builder.put(holder.get(), id));
        return builder.build();
    }

    default void destroyRegistry() {
        this.registry().clear();
    }

    default ImmutableMap<ValueHolder<O>, N> inverse() {
        return MtJava.inverseMap(this.registry());
    }

    default boolean hasId(N id) {
        return this.registry().containsKey(id);
    }

    default boolean has(O obj) {
        return this.values().contains(obj);
    }

    default boolean has(ValueHolder<O> obj) {
        return this.registry().containsValue(obj);
    }

    default int size() {
        return this.values().size();
    }

}
