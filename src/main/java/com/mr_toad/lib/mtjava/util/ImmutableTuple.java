package com.mr_toad.lib.mtjava.util;

import net.minecraft.util.Tuple;

public class ImmutableTuple<A, B> extends Tuple<A, B> {

    public ImmutableTuple(A left, B right) {
        super(left, right);
    }

    @Deprecated
    @Override
    public void setA(A left) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final void setB(B right) {
        throw new UnsupportedOperationException();
    }
}
