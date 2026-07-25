package com.mr_toad.lib.mtjava.floats.func;

@FunctionalInterface
public interface ToFloatBiFunction<T, U> {
    float applyAsFloat(T t, U u);
}
