package com.mr_toad.lib.mtjava.util;

import com.mojang.datafixers.util.Pair;

public class MtJava {

    public static final Runnable NOTHING = () -> {};

    public static<F, S, P extends Pair<F, S>> ImmutablePair<F, S> toImmutablePair(P pair) {
        return ImmutablePair.of(pair.getFirst(), pair.getSecond());
    }

    public static<A, B, T extends net.minecraft.util.Pair<A, B>> ImmutableTuple<A, B> toImmutableTuple(T tuple) {
        return new ImmutableTuple<>(tuple.getLeft(), tuple.getRight());
    }
}
