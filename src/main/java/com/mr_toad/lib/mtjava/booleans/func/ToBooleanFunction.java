package com.mr_toad.lib.mtjava.booleans.func;

@FunctionalInterface
public interface ToBooleanFunction<T> {
    boolean applyAsBoolean(T element);
}
