package com.mr_toad.lib.mtjava.math.vec.base;

import com.mr_toad.lib.mtjava.nio.MTNIO;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

public interface IntVec<S extends IntVec<S>> {

    static<S extends IntVec<S>> S load(Supplier<S> empty, ListTag list) {
        S vec = empty.get();
        List<IntTag> tags = list.stream().map(IntTag.class::cast).toList();
        for (int i = 0; i < tags.size(); i++) {
            vec.set(i, tags.get(i).intValue());
        }
        return vec;
    }

    static<S extends IntVec<S>> S read(Supplier<S> empty, FriendlyByteBuf buf) {
        S vec = empty.get();
        IntList ints = MTNIO.readIntList(buf);
        for (int i = 0; i < ints.size(); i++) {
            vec.set(i, ints.getInt(i));
        }
        return vec;
    }

    S set(S other);

    S set(int index, int value);

    S set(int value);

    S add(S other);

    S sub(S other);

    S mul(S other);

    S div(S other);

    S scale(int scalar);

    S min(S other);

    S max(S other);

    S abs();

    S cross(S other);

    IntList values();

    int length();

    int lengthSqr();

    int dist(S other);

    int distSqr(S other);

    int dot(S other);

    S zero();

    S create();

    default int get(int index) {
        return this.values().getInt(index);
    }

    default int size() {
        return this.values().size();
    }

    default S destroy() {
        return this.set(this.zero());
    }

    default S inverse() {
        return this.scale(-1);
    }

    default S def(int scalar) {
        return this.scale(1 / scalar);
    }

    default S normalize() {
        return this.def(this.length());
    }

    default void setIf(IntPredicate predicate, IntUnaryOperator v) {
        for (int i = 0; i < this.values().intStream().filter(predicate).toArray().length; i++) {
            this.set(i, v.applyAsInt(this.get(i)));
        }
    }

    default void setIf(IntPredicate predicate, int v) {
        for (int i = 0; i < this.values().intStream().filter(predicate).toArray().length; i++) {
            this.set(i, v);
        }
    }

    default boolean anyEqual(float v) {
        return this.values().intStream().anyMatch(d -> d == v);
    }

    default boolean allEqual(float v) {
        return this.values().intStream().allMatch(d -> d == v);
    }

    default boolean noneEqual(float v) {
        return this.values().intStream().noneMatch(d -> d == v);
    }

    default ListTag save() {
        ListTag list = new ListTag();
        for (int value : this.values()) {
            list.add(IntTag.valueOf(value));
        }
        return list;
    }

    default void write(FriendlyByteBuf buf) {
        buf.writeCollection(this.values(), FriendlyByteBuf::writeInt);
    }

    @SuppressWarnings("deprecation")
    default String name() {
        return "Vec" + this.size() + "[" + String.join(",", this.values().stream().map(String::valueOf).toList()) + "]";
    }
}
