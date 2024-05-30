package com.mr_toad.lib.mtjava.floats.func;

import java.util.function.ToIntBiFunction;

@FunctionalInterface
public interface ToFloatBiFunction<T, U> {
    float applyAsFloat(T t, U u);
}
