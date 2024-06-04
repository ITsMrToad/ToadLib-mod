package com.mr_toad.lib.mtjava.chars.func;

@FunctionalInterface
public interface ToCharFunction<T> {
    char applyAsChar(T c);
}
