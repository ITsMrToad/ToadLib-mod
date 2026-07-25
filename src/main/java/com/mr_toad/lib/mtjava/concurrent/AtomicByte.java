package com.mr_toad.lib.mtjava.concurrent;

import com.google.common.annotations.GwtIncompatible;
import it.unimi.dsi.fastutil.bytes.ByteBinaryOperator;
import it.unimi.dsi.fastutil.bytes.ByteUnaryOperator;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

@GwtIncompatible
public final class AtomicByte extends Number implements Serializable {

    private static final VarHandle VH;

    static {
        try {
            VH = MethodHandles.lookup().findVarHandle(AtomicByte.class, "value", byte.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private volatile byte value;

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
        return value;
    }

    public void set(byte v) {
        this.value = v;
    }

    public byte getVolatile() {
        return (byte) VH.getVolatile(this);
    }

    public void lazySet(byte v) {
        VH.setRelease(this, v);
    }

    public byte getAndSet(byte newValue) {
        return (byte) VH.getAndSet(this, newValue);
    }

    public boolean compareAndSet(byte expected, byte newValue) {
        return VH.compareAndSet(this, expected, newValue);
    }

    public byte compareAndExchange(byte expected, byte newValue) {
        return (byte) VH.compareAndExchange(this, expected, newValue);
    }

    public byte getAndAdd(byte delta) {
        return (byte) VH.getAndAdd(this, delta);
    }

    public byte addAndGet(byte delta) {
        return (byte) ((byte) VH.getAndAdd(this, delta) + delta);
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
        return this.addAndGet((byte) -1);
    }

    public byte getAndUpdate(ByteUnaryOperator updateFunction) {
        byte prev, next;
        do {
            prev = get();
            next = updateFunction.apply(prev);
        } while (!this.compareAndSet(prev, next));
        return prev;
    }

    public byte updateAndGet(ByteUnaryOperator updateFunction) {
        byte prev, next;
        do {
            prev = get();
            next = updateFunction.apply(prev);
        } while (!this.compareAndSet(prev, next));
        return next;
    }

    public byte getAndAccumulate(byte v, ByteBinaryOperator accumulatorFunction) {
        byte prev, next;
        do {
            prev = get();
            next = accumulatorFunction.apply(prev, v);
        } while (!this.compareAndSet(prev, next));
        return prev;
    }

    public byte accumulateAndGet(byte v, ByteBinaryOperator accumulatorFunction) {
        byte prev, next;
        do {
            prev = get();
            next = accumulatorFunction.apply(prev, v);
        } while (!this.compareAndSet(prev, next));
        return next;
    }
}
