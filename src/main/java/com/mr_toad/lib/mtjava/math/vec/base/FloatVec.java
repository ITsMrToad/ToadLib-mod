package com.mr_toad.lib.mtjava.math.vec.base;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.nio.MTNIO;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatPredicate;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.function.Supplier;

public interface FloatVec<S> {

    static<S extends FloatVec<S>> S load(Supplier<S> empty, ListTag list) {
        S vec = empty.get();
        List<FloatTag> tags = list.stream().map(FloatTag.class::cast).toList();
        for (int i = 0; i < tags.size(); i++) {
            vec.set(i, tags.get(i).getAsFloat());
        }
        return vec;
    }

    static<S extends FloatVec<S>> S read(Supplier<S> empty, FriendlyByteBuf buf) {
        S vec = empty.get();
        FloatList floats = MTNIO.readFloatList(buf);
        for (int i = 0; i < floats.size(); i++) {
            vec.set(i, floats.getFloat(i));
        }
        return vec;
    }

    @CanIgnoreReturnValue S set(S other);

    @CanIgnoreReturnValue S add(S other);

    @CanIgnoreReturnValue S sub(S other);

    @CanIgnoreReturnValue S mul(S other);

    @CanIgnoreReturnValue S scale(float scalar);

    @CanIgnoreReturnValue S min(S other);

    @CanIgnoreReturnValue S max(S other);

    @CanIgnoreReturnValue S abs();

    @CanIgnoreReturnValue S cross(S other);

    FloatList values();

    float length();

    float lengthSqr();

    float dist(S other);

    float distSqr(S other);

    float dot(S other);

    S zero();

    default float get(int index) {
        return this.values().getFloat(index);
    }

    default float set(int index, float value) {
        return this.values().set(index, value);
    }

    default int size() {
        return this.values().size();
    }

    @CanIgnoreReturnValue default S destroy() {
        return this.set(this.zero());
    }

    @CanIgnoreReturnValue default S inverse() {
        return this.scale(-1.0F);
    }

    @CanIgnoreReturnValue default S destroy() {
        return this.set(this.zero());
    }

    @CanIgnoreReturnValue default S inverse() {
        return this.scale(-1.0F);
    }

    @CanIgnoreReturnValue default S def(float scalar) {
        return this.scale(1.0F / scalar);
    }

    @CanIgnoreReturnValue default S normalize() {
        return this.def(this.length());
    }

    @SuppressWarnings("deprecation")
    default void setIf(FloatPredicate predicate, FloatUnaryOperator v) {
        for (int i = 0; i < this.values().doubleStream().filter(predicate::test).toArray().length; i++) {
            this.set(i, v.apply(this.get(i)));
        }
    }

    @SuppressWarnings("deprecation")
    default void setIf(FloatPredicate predicate, float v) {
        for (int i = 0; i < this.values().doubleStream().filter(predicate::test).toArray().length; i++) {
            this.set(i, v);
        }
    }

    default boolean anyEqual(float v) {
        return this.values().doubleStream().anyMatch(d -> d == v);
    }

    default boolean allEqual(float v) {
        return this.values().doubleStream().allMatch(d -> d == v);
    }

    default boolean noneEqual(float v) {
        return this.values().doubleStream().noneMatch(d -> d == v);
    }

    default ListTag save() {
        ListTag list = new ListTag();
        for (float value : this.values()) {
            list.add(FloatTag.valueOf(value));
        }
        return list;
    }

    default void write(FriendlyByteBuf buf) {
        buf.writeCollection(this.values(), FriendlyByteBuf::writeFloat);
    }

    @SuppressWarnings("deprecation")
    default String name() {
        return "Vec" + this.size() + "[" + String.join(",", this.values().stream().map(String::valueOf).toList()) + "]";
    }
}

