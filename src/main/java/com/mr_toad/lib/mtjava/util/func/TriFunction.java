package com.mr_toad.lib.mtjava.util.func;

@FunctionalInterface
public interface TriFunction<T, R, C> {
    C apply(T t, R r);
}
