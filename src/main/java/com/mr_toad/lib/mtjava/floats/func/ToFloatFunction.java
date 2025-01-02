package com.mr_toad.lib.mtjava.floats.func;

@FunctionalInterface
public interface ToFloatFunction<T> {
    float applyAsFloat(T t);
}
