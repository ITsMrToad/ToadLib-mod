package com.mr_toad.lib.mtjava.ints;

import com.mojang.datafixers.util.Pair;

public final class IntPair extends Pair<Integer, Integer> {

    public IntPair(int first, int second) {
        super(first, second);
    }

    public static IntPair of(int first, int second) {
        return new IntPair(first, second);
    }
}