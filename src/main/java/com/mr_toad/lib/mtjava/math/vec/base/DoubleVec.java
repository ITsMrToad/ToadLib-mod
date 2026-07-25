package com.mr_toad.lib.mtjava.math.vec.base;

import com.mr_toad.lib.mtjava.nio.MTNIO;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoublePredicate;
import it.unimi.dsi.fastutil.doubles.DoubleUnaryOperator;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.function.Supplier;

public interface DoubleVec<S extends DoubleVec<S>> {

    static<S extends DoubleVec<S>> S load(Supplier<S> empty, ListTag list) {
        S vec = empty.get();
        List<DoubleTag> tags = list.stream().map(DoubleTag.class::cast).toList();
        for (int i = 0; i < tags.size(); i++) {
            vec.set(i, tags.get(i).doubleValue());
        }
        return vec;
    }

    static<S extends DoubleVec<S>> S read(Supplier<S> empty, FriendlyByteBuf buf) {
        S vec = empty.get();
        DoubleList list = MTNIO.readDoubleList(buf);
        for (int i = 0; i < list.size(); i++) {
            vec.set(i, list.getDouble(i));
        }
        return vec;
    }

    S set(S other);

    S set(int index, double value);

    S set(double value);

    S add(S other);

    S sub(S other);

    S mul(S other);

    S div(S other);

    S scale(double scalar);

    S min(S other);

    S max(S other);

    S abs();

    S cross(S other);

    DoubleList values();

    double length();

    double lengthSqr();

    double dist(S other);

    double distSqr(S other);

    double dot(S other);

    S zero();

    S create();

    default double get(int index) {
        return this.values().getDouble(index);
    }

    default int size() {
        return this.values().size();
    }

    default S destroy() {
        return this.set(this.zero());
    }

    default S inverse() {
        return this.scale(-1.0D);
    }

    default S def(double scalar) {
        return this.scale(1.0D / scalar);
    }

    default S normalize() {
        return this.def(this.length());
    }

    default void setIf(DoublePredicate predicate, DoubleUnaryOperator v) {
        for (int i = 0; i < this.values().doubleStream().filter(predicate::test).toArray().length; i++) {
            this.set(i, v.apply(this.get(i)));
        }
    }

    default void setIf(DoublePredicate predicate, double v) {
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
        for (double value : this.values()) {
            list.add(DoubleTag.valueOf(value));
        }
        return list;
    }

    default void write(FriendlyByteBuf buf) {
        buf.writeCollection(this.values(), FriendlyByteBuf::writeDouble);
    }

    @SuppressWarnings("deprecation")
    default String name() {
        return "Vec" + this.size() + "[" + String.join(",", this.values().stream().map(String::valueOf).toList()) + "]";
    }
}
