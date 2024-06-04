package com.mr_toad.lib.mtjava.bytes.func;

@FunctionalInterface
public interface ToByteFunction<T> {

    byte applyAsByte(T value);
}
