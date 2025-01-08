package com.mr_toad.lib.mtjava.math.vec.base;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.network.FriendlyByteBuf;

public interface FloatVec<S> {

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

    default void normalize() {
        float f = this.length();
        if (f < 1.0E-4F) {
            this.destroy();
        } else {
            for (int i = 0; i < this.values().size(); i++) {
                float v = this.get(i);
                this.set(i, v / f);
            }
        }
    }

    default void write(FriendlyByteBuf buf) {
        this.values().forEach(buf::writeDouble);
    }

    default void read(FriendlyByteBuf buf) {
        this.values().clear();
        this.values().addAll(buf.readList(FriendlyByteBuf::readFloat));
    }

    @SuppressWarnings("deprecation")
    default String name() {
        return "Vec" + this.size() + "[" + String.join(",", this.values().stream().map(String::valueOf).toList()) + "]";
    }
}
