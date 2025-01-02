package com.mr_toad.lib.mtjava.ints.func;

@FunctionalInterface
public interface IntBiFunction<T> {
    T apply(int a, int b);
}
