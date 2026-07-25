package com.mr_toad.lib.mtjava.doubles.func;

@FunctionalInterface
public interface DoubleBiFunction<T> {
    T apply(double d1, double d2);
}
