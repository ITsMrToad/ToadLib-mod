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

    @CanIgnoreReturnValue
    default S normalize() {
        return this.scale(1 / this.length());
    }

    default CompoundTag save() {
        CompoundTag nbt = new CompoundTag();
        this.values().forEach(d -> nbt.putInt(this.valueName(d), d));
        return nbt;
    }

    default void load(CompoundTag nbt) {
        for (int i = 0; i < this.values().size(); i++) {
            int d1 = this.get(i);
            if (nbt.contains(this.valueName(d1))) {
                int finalI = i;
                this.set(() -> finalI, nbt.getInt(this.valueName(d1)));
            }
        }
    }

    default void write(FriendlyByteBuf buf) {
        this.values().forEach(buf::writeDouble);
    }

    default void read(FriendlyByteBuf buf) {
        this.values().clear();
        this.values().addAll(buf.readList(FriendlyByteBuf::readInt));
    }

    default String valueName(int d) {
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

