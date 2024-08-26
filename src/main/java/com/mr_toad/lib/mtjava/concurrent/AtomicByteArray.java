package com.mr_toad.lib.mtjava.concurrent;

import com.google.common.annotations.GwtIncompatible;
import it.unimi.dsi.fastutil.bytes.ByteBinaryOperator;
import it.unimi.dsi.fastutil.bytes.ByteUnaryOperator;

import java.io.Serial;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

@GwtIncompatible
public final class AtomicByteArray implements Serializable {

    @Serial private static final long serialVersionUID = 2862133569453604235L;
    private static final VarHandle AA = MethodHandles.arrayElementVarHandle(byte[].class);

    private byte[] array;

    public AtomicByteArray(int length) {
        this.array = new byte[length];
    }

    public AtomicByteArray(byte[] array) {
        this.array = array.clone();
    }

    public byte get(int i) {
        return (byte) AA.getVolatile(this.array, i);
    }

    public byte getAndSet(int i, byte newValue) {
        return (byte) AA.getAndSet(this.array, i, newValue);
    }

    public void set(int i, byte newValue) {
        AA.setVolatile(this.array, i, newValue);
    }

    public void lazySet(int i, byte newValue) {
        AA.setRelease(this.array, i, newValue);
    }

    public byte[] get() {
        return this.array;
    }

    public void set(byte[] bytes) {
        this.array = bytes;
    }

    public boolean compareAndSet(int i, byte expectedValue, byte newValue) {
        return AA.compareAndSet(this.array, i, expectedValue, newValue);
    }

    public boolean weakCompareAndSetPlain(int i, byte expectedValue, byte newValue) {
        return AA.weakCompareAndSetPlain(this.array, i, expectedValue, newValue);
    }

    public byte getAndAdd(int i, byte delta) {
        return (byte)AA.getAndAdd(this.array, i, delta);
    }

    public byte addAndGet(int i, byte delta) {
        return (byte) ((byte) AA.getAndAdd(this.array, i, delta) + delta);
    }

    public byte getAndIncrement(int i) {
        return this.getAndAdd(i, (byte) 1);
    }

    public byte getAndDecrement(int i) {
        return this.getAndAdd(i, (byte) -1);
    }

    public byte incrementAndGet(int i) {
        return this.addAndGet(i, (byte) 1);
    }

    public byte decrementAndGet(int i) {
        return this.addAndGet(i, (byte) -1);
    }

    public int length() {
        return this.array.length;
    }

    public byte getAndUpdate(int i, ByteUnaryOperator updateFunction) {
        byte prev = this.get(i), next = 0;
        for (boolean haveNext = false;;) {
            if (!haveNext)
                next = updateFunction.apply(prev);
            if (this.weakCompareAndSetVolatile(i, prev, next))
                return prev;
            haveNext = (prev == (prev = this.get(i)));
        }
    }

    public byte updateAndGet(int i, ByteUnaryOperator updateFunction) {
        byte prev = this.get(i), next = 0;
        for (boolean haveNext = false;;) {
            if (!haveNext) {
                next = updateFunction.apply(prev);
            }
            if (this.weakCompareAndSetVolatile(i, prev, next)) {
                return next;
            }
            haveNext = (prev == (prev = this.get(i)));
        }
    }

    public byte getAndAccumulate(int i, byte x, ByteBinaryOperator accumulatorFunction) {
        byte prev = this.get(i), next = 0;
        for (boolean haveNext = false;;) {
            if (!haveNext) {
                next = accumulatorFunction.apply(prev, x);
            }
            if (this.weakCompareAndSetVolatile(i, prev, next)) {
                return prev;
            }
            haveNext = (prev == (prev = this.get(i)));
        }
    }

    public byte accumulateAndGet(int i, byte x, ByteBinaryOperator accumulatorFunction) {
        byte prev = this.get(i), next = 0;
        for (boolean haveNext = false;;) {
            if (!haveNext) {
                next = accumulatorFunction.apply(prev, x);
            }
            if (this.weakCompareAndSetVolatile(i, prev, next)) {
                return next;
            }
            haveNext = (prev == (prev = this.get(i)));
        }
    }

    public String toString() {
        int iMax = this.length() - 1;
        if (iMax == -1) {
            return "[]";
        }

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(get(i));
            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(',').append(' ');
        }
    }

    public byte getPlain(int i) {
        return (byte) AA.get(array, i);
    }

    public void setPlain(int i, byte newValue) {
        AA.set(array, i, newValue);
    }

    public byte getOpaque(int i) {
        return (byte) AA.getOpaque(array, i);
    }

    public void setOpaque(int i, byte newValue) {
        AA.setOpaque(array, i, newValue);
    }

    public byte getAcquire(int i) {
        return (byte) AA.getAcquire(this.array, i);
    }

    public void setRelease(int i, byte newValue) {
        AA.setRelease(this.array, i, newValue);
    }

    public byte compareAndExchange(int i, byte expectedValue, byte newValue) {
        return (byte) AA.compareAndExchange(this.array, i, expectedValue, newValue);
    }

    public byte compareAndExchangeAcquire(int i, byte expectedValue, byte newValue) {
        return (byte) AA.compareAndExchangeAcquire(this.array, i, expectedValue, newValue);
    }

    public int compareAndExchangeRelease(int i, byte expectedValue, byte newValue) {
        return (byte) AA.compareAndExchangeRelease(this.array, i, expectedValue, newValue);
    }

    public boolean weakCompareAndSetVolatile(int i, byte expectedValue, byte newValue) {
        return AA.weakCompareAndSet(this.array, i, expectedValue, newValue);
    }

    public boolean weakCompareAndSetAcquire(int i, byte expectedValue, byte newValue) {
        return AA.weakCompareAndSetAcquire(this.array, i, expectedValue, newValue);
    }

    public boolean weakCompareAndSetRelease(int i, byte expectedValue, byte newValue) {
        return AA.weakCompareAndSetRelease(this.array, i, expectedValue, newValue);
    }

}
