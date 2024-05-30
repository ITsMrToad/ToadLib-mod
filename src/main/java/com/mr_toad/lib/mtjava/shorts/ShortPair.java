package com.mr_toad.lib.mtjava.shorts;

import com.mojang.datafixers.util.Pair;

public final class ShortPair extends Pair<Short, Short> {

    public ShortPair(short first, short second) {
        super(first, second);
    }

    public static ShortPair of(short first, short second) {
        return new ShortPair(first, second);
    }
}