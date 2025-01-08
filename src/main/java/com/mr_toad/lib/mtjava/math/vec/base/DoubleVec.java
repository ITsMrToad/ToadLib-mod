package com.mr_toad.lib.mtjava.math.vec.base;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public interface DoubleVec<S> {

    @CanIgnoreReturnValue S set(S other);

    @CanIgnoreReturnValue S add(S other);

    @CanIgnoreReturnValue S sub(S other);

    @CanIgnoreReturnValue S mul(S other);

    @CanIgnoreReturnValue S scale(double scalar);

    @CanIgnoreReturnValue S min(S other);

    @CanIgnoreReturnValue S max(S other);

    @CanIgnoreReturnValue S abs();

    @CanIgnoreReturnValue S cross(S other);

    DoubleList values();

    double length();

    double lengthSqr();

    double dist(S other);

    double distSqr(S other);

    double dot(S other);

    S zero();

    default double get(int index) {
        return this.values().getDouble(index);
    }

    default double set(int index, double value) {
        return this.values().set(index, value);
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
        return this.scale(-1.0F);
    }

    default void normalize() {
        double f = this.length();
        if (f < 1.0E-4F) {
            this.destroy();
        } else {
            for (int i = 0; i < this.values().size(); i++) {
                double v = this.get(i);
                this.set(i, v / f);
            }
        }
    }

    default CompoundTag save() {
        CompoundTag nbt = new CompoundTag();
        this.values().forEach(d -> nbt.putDouble(this.valueName(d), d));
        return nbt;
    }

    default void load(CompoundTag nbt) {
        for (int i = 0; i < this.values().size(); i++) {
            double d1 = this.get(i);
            if (nbt.contains(this.valueName(d1))) {
                this.set(i, nbt.getDouble(this.valueName(d1)));
            }
        }
    }

    default void write(FriendlyByteBuf buf) {
        this.values().forEach(buf::writeDouble);
    }

    default void read(FriendlyByteBuf buf) {
        this.values().clear();
        this.values().addAll(buf.readList(FriendlyByteBuf::readDouble));
    }

    default String valueName(double d) {
        return switch (this.values().indexOf(d)) {
            case 0 -> "x";
            case 1 -> "y";
            case 2 -> "z";
            case 3 -> "w";
            default -> "unexpected";
        };
    }

    @SuppressWarnings("deprecation")
    default String name() {
        return "Vec" + this.size() + "[" + String.join(",", this.values().stream().map(String::valueOf).toList()) + "]";
    }
}
