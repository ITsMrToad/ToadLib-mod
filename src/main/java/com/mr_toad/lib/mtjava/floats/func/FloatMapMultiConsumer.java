package com.mr_toad.lib.mtjava.floats.func;

import java.util.function.DoubleConsumer;

@FunctionalInterface
public interface FloatMapMultiConsumer {
    void accept(double value, DoubleConsumer dc);
}
