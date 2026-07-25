package com.mr_toad.lib.mtjava.math.vec.base;

import com.mr_toad.lib.mtjava.nio.MTNIO;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatPredicate;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.function.Supplier;

public interface FloatVec<S extends FloatVec<S>> {

    static<S extends FloatVec<S>> S load(Supplier<S> empty, ListTag list) {
        S vec = empty.get();
        List<FloatTag> tags = list.stream().map(FloatTag.class::cast).toList();
        for (int i = 0; i < tags.size(); i++) {
            vec.set(i, tags.get(i).floatValue());
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

    S set(S other);

    S set(int index, float value);

    S set(float value);

    S add(S other);

    S sub(S other);

    S mul(S other);

    S div(S other);

    S scale(float scalar);

    S min(S other);

    S max(S other);

    S abs();

    S cross(S other);

    FloatList values();

    float length();

    float lengthSqr();

    float dist(S other);

    float distSqr(S other);

    float dot(S other);

    S zero();

    S create();

    default float get(int index) {
        return this.values().getFloat(index);
    }

    default int size() {
        return this.values().size();
    }

    default S destroy() {
        return this.set(this.zero());
    }

    default S inverse() {
        return this.scale(-1.0F);
    }

    default S def(float scalar) {
        return this.scale(1.0F / scalar);
    }

    default S normalize() {
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
