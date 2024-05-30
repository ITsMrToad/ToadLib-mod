package com.mr_toad.lib.mtjava.bytes.func;

@FunctionalInterface
public interface ByteFunction<R> {
    R apply(byte value);
}
