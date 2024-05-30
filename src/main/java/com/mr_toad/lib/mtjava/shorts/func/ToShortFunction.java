package com.mr_toad.lib.mtjava.shorts.func;

@FunctionalInterface
public interface ToShortFunction<T> {

    short applyAsShort(T value);
}
