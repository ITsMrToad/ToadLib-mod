package com.mr_toad.lib.mtjava.util.func;

@FunctionalInterface
public interface TriFunction<T, R, K, C> {
    C apply(T t, R r, K k);
}
