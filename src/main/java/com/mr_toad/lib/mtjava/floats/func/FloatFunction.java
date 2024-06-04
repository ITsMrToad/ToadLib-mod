package com.mr_toad.lib.mtjava.floats.func;

@FunctionalInterface
public interface FloatFunction<R> {
    R apply(double value);
}
