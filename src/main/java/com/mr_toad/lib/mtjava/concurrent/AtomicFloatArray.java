package com.mr_toad.lib.mtjava.concurrent;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.floats.FloatBinaryOperator;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class AtomicFloatArray implements Serializable {

    @Serial private static final long serialVersionUID = 0L;
    private transient AtomicIntegerArray ints;

    public AtomicFloatArray(int length) {
        this.ints = new AtomicIntegerArray(length);
    }

    public AtomicFloatArray(float[] array) {
        int len = array.length;
        int[] intArray = new int[len];

        for (int i = 0; i < len; ++i) {
            intArray[i] = Float.floatToRawIntBits(array[i]);
        }

        this.ints = new AtomicIntegerArray(intArray);
    }

    public int length() {
        return this.ints.length();
    }

    public float get(int i) {
        return Float.intBitsToFloat(this.ints.get(i));
    }

    public void set(int i, float newValue) {
        int next = Float.floatToRawIntBits(newValue);
        this.ints.set(i, next);
    }

    public void lazySet(int i, float newValue) {
        int next = Float.floatToRawIntBits(newValue);
        this.ints.lazySet(i, next);
    }

    public float getAndSet(int i, float newValue) {
        int next =  Float.floatToRawIntBits(newValue);
        return Float.intBitsToFloat(this.ints.getAndSet(i, next));
    }

    public boolean compareAndSet(int i, float expect, float update) {
        return this.ints.compareAndSet(i, Float.floatToRawIntBits(expect), Float.floatToRawIntBits(update));
    }

    public float getAndAdd(int i, float delta) {
        return this.getAndAccumulate(i, delta, Float::sum);
    }

    public float addAndGet(int i, float delta) {
        return this.accumulateAndGet(i, delta, Float::sum);
    }

    public float getAndAccumulate(int i, float x, FloatBinaryOperator accumulatorFunction) {
        Preconditions.checkNotNull(accumulatorFunction);
        return this.getAndUpdate(i, oldValue -> accumulatorFunction.apply(oldValue, x));
    }

    public float accumulateAndGet(int i, float x, FloatBinaryOperator accumulatorFunction) {
        Preconditions.checkNotNull(accumulatorFunction);
        return this.updateAndGet(i, oldValue -> accumulatorFunction.apply(oldValue, x));
    }

    public float getAndUpdate(int i, FloatUnaryOperator updaterFunction) {
        int current;
        float currentVal;
        int next;
        do {
            current = this.ints.get(i);
            currentVal = Float.intBitsToFloat(current);
            float nextVal = updaterFunction.apply(currentVal);
            next = Float.floatToIntBits(nextVal);
        } while (!this.ints.compareAndSet(i, current, next));

        return currentVal;
    }

    public float updateAndGet(int i, FloatUnaryOperator updaterFunction) {
        int current;
        float nextVal;
        int next;
        do {
            current = this.ints.get(i);
            float currentVal = Float.intBitsToFloat(current);
            nextVal = updaterFunction.apply(currentVal);
            next = Float.floatToIntBits(nextVal);
        } while (!this.ints.compareAndSet(i, current, next));

        return nextVal;
    }

    @Override
    public String toString() {
        int iMax = this.length() - 1;
        if (iMax == -1) {
            return "[]";
        } else {
            StringBuilder b = new StringBuilder(19 * (iMax + 1));
            b.append('[');
            int i = 0;

            while (true) {
                b.append(Float.intBitsToFloat(this.ints.get(i)));
                if (i == iMax) {
                    return b.append(']').toString();
                }
                b.append(',').append(' ');
                ++i;
            }
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        int length = this.length();
        s.writeInt(length);
        for (int i = 0; i < length; ++i) {
            s.writeFloat(this.get(i));
        }
    }

    @Serial
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int length = s.readInt();
        IntList builder = new IntArrayList();
        for (int i = 0; i < length; ++i) {
            builder.add(Float.floatToRawIntBits(s.readFloat()));
        }

        int[] arr = builder.toIntArray();
        this.ints = new AtomicIntegerArray(arr);
    }
}