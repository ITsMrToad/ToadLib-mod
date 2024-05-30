package com.mr_toad.lib.mtjava.strings.func;

@FunctionalInterface
public interface StringFunction<R> {
    R apply(String value);
}
