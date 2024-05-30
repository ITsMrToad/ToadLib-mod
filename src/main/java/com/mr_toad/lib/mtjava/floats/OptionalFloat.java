package com.mr_toad.lib.mtjava.floats;

import com.google.common.base.MoreObjects;
import com.mr_toad.lib.mtjava.floats.func.FloatSupplier;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.floats.FloatConsumer;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

public final class OptionalFloat {

    private static final OptionalFloat EMPTY = new OptionalFloat();

    private final boolean isPresent;
    private final float value;

    private OptionalFloat() {
        this.isPresent = false;
        this.value = Float.NaN;
    }

    public static OptionalFloat empty() {
        return EMPTY;
    }

    private OptionalFloat(float value) {
        this.isPresent = true;
        this.value = value;
    }

    public static OptionalFloat of(float value) {
        return new OptionalFloat(value);
    }

    public float getAsFloat() {
        if (!this.isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public boolean isEmpty() {
        return !this.isPresent;
    }

    public void ifPresent(FloatConsumer action) {
        if (this.isPresent) {
            action.accept(this.value);
        }
    }

    public void ifPresentOrElse(FloatConsumer action, Runnable emptyAction) {
        if (this.isPresent) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    public float orElse(float other) {
        return this.isPresent ? this.value : other;
    }

    public float orElseGet(FloatSupplier supplier) {
        return this.isPresent ? this.value : supplier.getAsFloat();
    }

    public float orElseThrow() {
        if (!this.isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    public <X extends Throwable> float orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.isPresent) {
            return this.value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public DoubleStream stream() {
        if (this.isPresent) {
            return DoubleStream.of(SafeMath.safeDoubleToFloat(this.value));
        } else {
            return DoubleStream.empty();
        }
    }

    public OptionalFloat map(Float2FloatFunction mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return OptionalFloat.of(mapper.apply(this.value));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof OptionalFloat other && (this.isPresent && other.isPresent ? Float.compare(this.value, other.value) == 0 : this.isPresent == other.isPresent);
    }

    @Override
    public int hashCode() {
        return this.isPresent ? Float.hashCode(this.value) : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add(this.isPresent ? String.format("OptionalFloat[%s]", this.value) : "Empty", this.value).toString();
    }
}
