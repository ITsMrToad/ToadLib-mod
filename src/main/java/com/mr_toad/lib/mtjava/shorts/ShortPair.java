package com.mr_toad.lib.mtjava.shorts;

import com.mojang.datafixers.util.Pair;

///@deprecated Class box primitives.
@Deprecated(since = "1.5.0")
public final class ShortPair extends Pair<Short, Short> {

    public ShortPair(short first, short second) {
        super(first, second);
    }

    public static ShortPair of(short first, short second) {
        return new ShortPair(first, second);
    }
}