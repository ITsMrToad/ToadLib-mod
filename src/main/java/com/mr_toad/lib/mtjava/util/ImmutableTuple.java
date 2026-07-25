package com.mr_toad.lib.mtjava.util;

import net.minecraft.util.Tuple;

import org.jetbrains.annotations.NotNull;

public class ImmutableTuple<A, B> extends Tuple<A, B> {

    public ImmutableTuple(A left, B right) {
        super(left, right);
    }

    @Deprecated(since = "1.0.0")
    @Override
    public void setA(@NotNull A left) {
        throw new UnsupportedOperationException();
    }

    @Deprecated(since = "1.0.0")
    @Override
    public final void setB(@NotNull B right) {
        throw new UnsupportedOperationException();
    }
}
