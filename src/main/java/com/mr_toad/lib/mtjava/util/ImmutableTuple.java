package com.mr_toad.lib.mtjava.util;

import net.minecraft.util.Pair;

public class ImmutableTuple<A, B> extends Pair<A, B> {

    public ImmutableTuple(A left, B right) {
        super(left, right);
    }

    @Deprecated
    @Override
    public void setLeft(A left) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final void setRight(B right) {
        throw new UnsupportedOperationException();
    }
}
