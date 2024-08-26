package com.mr_toad.lib.mtjava.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.annotations.FabricCanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.annotations.ReflectionSupport;
import it.unimi.dsi.fastutil.bytes.ByteBinaryOperator;
import it.unimi.dsi.fastutil.bytes.ByteUnaryOperator;
import sun.misc.Unsafe;

import java.io.Serial;
import java.io.Serializable;
import java.nio.ByteOrder;

@GwtIncompatible
@ReflectionSupport(ReflectionSupport.SupportLevel.FULL)
public final class AtomicByte extends Number implements Serializable {

    @Serial private static final long serialVersionUID = 6214790243416807050L;
    private static final Unsafe UNSAFE = Unsafe.getUnsafe();

    private static final long VALUE;
    private volatile byte value;

    static {
        try {
            VALUE = UNSAFE.objectFieldOffset(AtomicByte.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | SecurityException e) {
            ToadLib.LOGGER.error("Cannot get object field offset of AtomicByte");
            throw new RuntimeException(e);
        }
    }

    public AtomicByte(byte initial) {
        this.value = initial;
    }

    public AtomicByte() {}

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public byte get() {
        return this.value;
    }

    public void set(byte v) {
        this.value = v;
    }

    public byte getByteVolatile() {
        return UNSAFE.getByteVolatile(this, VALUE);
    }

    public void lazySet(byte v) {
        UNSAFE.putByteVolatile(this, VALUE, v);
    }

    public byte getAndSet(byte newValue) {
        return this.getAndAdd(newValue);
    }

    public byte getAndAdd(byte newValue) {
        byte v;
        do {
            v = this.getByteVolatile();
        } while (!this.compareAndSet(v, newValue));
        return v;
    }

    public byte addAndGet(byte delta) {
        return (byte) (this.getAndAdd(delta) + delta);
    }

    public byte getAndIncrement() {
        return this.getAndAdd((byte) 1);
    }

    public byte getAndDecrement() {
        return this.getAndAdd((byte) -1);
    }

    public byte incrementAndGet() {
        return this.addAndGet((byte) 1);
    }

    public byte decrementAndGet() {
        return this.addAndGet((byte) -11);
    }

    public boolean compareAndSet(byte expected, byte newValue) {
        return this.compareAndExchange(expected, newValue) == expected;
    }

    public byte compareAndExchange(byte expected, byte newValue) {
        long wordOffset = VALUE & ~3;
        int shift = (int) (VALUE & 3) << 3;

        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            shift = 24 - shift;
        }

        int mask = 0xFF << shift;
        int maskedExpected = (expected & 0xFF) << shift;
        int maskedX = (newValue & 0xFF) << shift;

        int fullWord;

        do {
            fullWord = UNSAFE.getIntVolatile(this, wordOffset);
            if ((fullWord & mask) != maskedExpected) {
                return (byte) ((fullWord & mask) >> shift);
            }
        } while (!UNSAFE.compareAndSwapInt(this, wordOffset, fullWord, (fullWord & ~mask) | maskedX));
        return expected;
    }

    @FabricCanIgnoreReturnValue
    public byte getAndUpdate(ByteUnaryOperator updateFunction) {
        byte prev = this.get(), next = 0;
        for (boolean haveNext = false;;) {
            if (!haveNext) {
                next = updateFunction.apply(prev);
            }
            if (this.compareAndSet(prev, next)) {
                return prev;
            }
            haveNext = (prev == (prev = get()));
        }
    }

    @FabricCanIgnoreReturnValue
    public byte updateAndGet(ByteUnaryOperator updateFunction) {
        byte prev = this.get(), next = 0;
        for (boolean haveNext = false;;) {
            if (!haveNext) {
                next = updateFunction.apply(prev);
            }
            if (this.compareAndSet(prev, next)) {
                return next;
            }
            haveNext = (prev == (prev = get()));
        }
    }

    @FabricCanIgnoreReturnValue
    public byte getAndAccumulate(byte x, ByteBinaryOperator accumulatorFunction) {
        byte prev = this.get(), next = 0;
        for (boolean haveNext = false;;) {
            if (!haveNext) {
                next = accumulatorFunction.apply(prev, x);
            }
            if (this.compareAndSet(prev, next)) {
                return prev;
            }
            haveNext = (prev == (prev = this.get()));
        }
    }

    @FabricCanIgnoreReturnValue
    public byte accumulateAndGet(byte x, ByteBinaryOperator accumulatorFunction) {
        byte prev = this.get(), next = 0;
        for (boolean haveNext = false;;) {
            if (!haveNext) {
                next = accumulatorFunction.apply(prev, x);
            }
            if (this.compareAndSet(prev, next)) {
                return next;
            }
            haveNext = (prev == (prev = this.get()));
        }
    }

}
