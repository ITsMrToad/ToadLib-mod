package com.mr_toad.lib.mtjava.util.func;

@FunctionalInterface
public interface SelfFunction<T> {
    T apply(T t);
}
