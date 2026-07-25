package com.mr_toad.lib.mtjava.strings.func;

@FunctionalInterface
public interface ToStringFunction<T> {

    String applyAsString(T value);

}
