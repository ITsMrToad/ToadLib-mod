package com.mr_toad.lib.mtjava.util;

import com.mojang.datafixers.util.Pair;

import java.util.function.Function;

public class ImmutablePair<F, S> extends Pair<F, S> {

    private ImmutablePair(F first, S second) {
        super(first, second);
    }

    public static<F, S> ImmutablePair<F, S> of(F f, S s) {
        return new ImmutablePair<>(f, s);
    }

    @Deprecated
    @Override
    public final <F2> Pair<F2, S> mapFirst(Function<? super F, ? extends F2> function) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final  <S2> Pair<F, S2> mapSecond(Function<? super S, ? extends S2> function) {
        throw new UnsupportedOperationException();
    }
}
