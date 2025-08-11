package com.mr_toad.lib.mtjava.math.vec.base;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.nio.MTNIO;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

public interface IntVec<S> {

    @CanIgnoreReturnValue S set(S other);

    @CanIgnoreReturnValue S add(S other);

    @CanIgnoreReturnValue S sub(S other);

    @CanIgnoreReturnValue S mul(S other);

    @CanIgnoreReturnValue S scale(int scalar);

    @CanIgnoreReturnValue S min(S other);

    @CanIgnoreReturnValue S max(S other);

    @CanIgnoreReturnValue S abs();

    @CanIgnoreReturnValue S cross(S other);

    IntList values();

    int length();

    int lengthSqr();

    int dist(S other);

    int distSqr(S other);

    int dot(S other);

    S zero();

    default int get(int index) {
        return this.values().getInt(index);
    }

    default int set(IntSupplier index, int value) {
        return this.values().set(index.getAsInt(), value);
    }

    default int size() {
        return this.values().size();
    }

    @CanIgnoreReturnValue
    default S destroy() {
        return this.set(this.zero());
    }

    @CanIgnoreReturnValue
    default S inverse() {
        return this.scale(-1);
    }

    @CanIgnoreReturnValue default S destroy() {
        return this.set(this.zero());
    }

    @CanIgnoreReturnValue default S inverse() {
        return this.scale(-1);
    }

    @CanIgnoreReturnValue default S def(int scalar) {
        return this.scale(1 / scalar);
    }

    @CanIgnoreReturnValue default S normalize() {
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


