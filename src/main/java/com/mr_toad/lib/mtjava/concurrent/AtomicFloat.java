package com.mr_toad.lib.mtjava.concurrent;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import it.unimi.dsi.fastutil.floats.FloatBinaryOperator;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicFloat extends Number implements Serializable {

    @Serial private static final long serialVersionUID = 0L;
    private volatile int value;

    private static final AtomicIntegerFieldUpdater<AtomicFloat> UPDATER = AtomicIntegerFieldUpdater.newUpdater(AtomicFloat.class, "value");

    public AtomicFloat(float initialValue) {
        this.value = Float.floatToIntBits(initialValue);
    }

    public AtomicFloat() {}

    public final float get() {
        return Float.intBitsToFloat(this.value);
    }

    public final void set(float newValue) {
        this.value = Float.floatToRawIntBits(newValue);
    }

    public final void lazySet(float newValue) {
        int next = Float.floatToIntBits(newValue);
        UPDATER.lazySet(this, next);
    }

    public final float getAndSet(float newValue) {
        int next = Float.floatToIntBits(newValue);
        return Float.intBitsToFloat(UPDATER.getAndSet(this, next));
    }

    public final boolean compareAndSet(float expect, float update) {
        return UPDATER.compareAndSet(this, Float.floatToRawIntBits(expect), Float.floatToRawIntBits(update));
    }

    public final boolean weakCompareAndSet(float expect, float update) {
        return UPDATER.weakCompareAndSet(this, Float.floatToRawIntBits(expect), Float.floatToRawIntBits(update));
    }

    @CanIgnoreReturnValue
    public final float getAndAdd(float delta) {
        return this.getAndAccumulate(delta, Float::sum);
    }

    @CanIgnoreReturnValue
    public final float addAndGet(float delta) {
        return this.accumulateAndGet(delta, Float::sum);
    }

    @CanIgnoreReturnValue
    public final float getAndAccumulate(float x, FloatBinaryOperator accumulatorFunction) {
        Preconditions.checkNotNull(accumulatorFunction);
        return this.getAndUpdate((oldValue) -> accumulatorFunction.apply(oldValue, x));
    }

    @CanIgnoreReturnValue
    public final float accumulateAndGet(float x, FloatBinaryOperator accumulatorFunction) {
        Preconditions.checkNotNull(accumulatorFunction);
        return this.updateAndGet((oldValue) -> accumulatorFunction.apply(oldValue, x));
    }

    @CanIgnoreReturnValue
    public final float getAndUpdate(FloatUnaryOperator updateFunction) {
        int current;
        float currentVal;
        int next;
        do {
            current = this.value;
            currentVal = Float.intBitsToFloat(current);
            float nextVal = updateFunction.apply(currentVal);
            next = Float.floatToRawIntBits(nextVal);
        } while (!UPDATER.compareAndSet(this, current, next));

        return currentVal;
    }

    @CanIgnoreReturnValue
    public final float updateAndGet(FloatUnaryOperator updateFunction) {
        int current;
        float nextVal;
        int next;
        do {
            current = this.value;
            float currentVal = Float.intBitsToFloat(current);
            nextVal = updateFunction.apply(currentVal);
            next = Float.floatToRawIntBits(nextVal);
        } while (!UPDATER.compareAndSet(this, current, next));

        return nextVal;
    }

    public String toString() {
        return Float.toString(this.get());
    }

    public int intValue() {
        return (int) this.get();
    }

    public long longValue() {
        return (long) this.get();
    }

    public double doubleValue() {
        return this.get();
    }

    public float floatValue() {
        return this.get();
    }

    @Serial
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeFloat(this.get());
    }

    @Serial
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.set(s.readFloat());
    }
}