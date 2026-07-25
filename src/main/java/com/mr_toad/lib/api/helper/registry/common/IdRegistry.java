package com.mr_toad.lib.api.helper.registry.common;

import com.mr_toad.lib.core.ToadLib;

import org.jetbrains.annotations.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface IdRegistry<N extends Number, O, M extends Map<N, ValueHolder<O>>> extends ToadRegistry<N, O, M> {

    String regName();

    @Nullable ValueHolder<O> defaultValue();

    default ValueHolder<O> getOrDefault(N id) {
        ValueHolder<O> holder = this.registry().get(id);
        if (holder == null) {
            if (this.defaultValue() != null) {
                ToadLib.LOGGER.error("Member with id: '{}' not found in '{}'! Received as default.", id, this.regName());
                return this.defaultValue();
            } else {
                throw new RegistryMemberException("Member of '" + this.regName() + "' with id:" + id + "doesn't exists! Default target not declared.");
            }
        }
        return holder;
    }

    default ValueHolder<O> getOrThrow(N id) {
        if (!this.registry().containsKey(id)) {
            throw new RegistryMemberException("Member of '" + this.regName() + "' with id:" + id + "doesn't exists!");
        }
        return this.registry().get(id);
    }

    default Optional<N> getId(O obj) {
        if (!this.values().contains(obj)) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.inverseValues().get(obj));
    }

    default N getIdOrThrow(O obj) {
        if (!this.values().contains(obj)) {
            throw new RegistryMemberException("ID of '" + obj  + "' in '" + this.regName() + "' not found!");
        }
        return this.inverseValues().get(obj);
    }

    default N getIdOrThrow(ValueHolder<O> obj) {
        if (!this.registry().containsValue(obj)) {
            throw new RegistryMemberException("ID of '" + obj  + "' in '" + this.regName() + "' not found!");
        }
        return this.inverse().get(obj);
    }

    default boolean replace(N id, O newValue) {
        return this.replace(id, new ValueHolder<>(newValue));
    }

    default boolean replace(N id, ValueHolder<O> newValue) {
        return this.registry().replace(id, this.getOrThrow(id), newValue);
    }

    default Stream<O> sortByID() {
        return this.values().stream().sorted((o, o2) -> {
            N id1 = this.getId(o).orElse(null);
            N id2 = this.getId(o2).orElse(null);

            if (id1 == null || id2 == null) {
                return 0;
            }

            return Integer.compare(id1.intValue(), id2.intValue());
        });
    }
}
