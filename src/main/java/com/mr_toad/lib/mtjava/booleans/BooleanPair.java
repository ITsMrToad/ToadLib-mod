package com.mr_toad.lib.mtjava.booleans;

import com.mojang.datafixers.util.Pair;

///@deprecated Class box primitives.
@Deprecated(since = "1.5.0")
public class BooleanPair extends Pair<Boolean, Boolean> {

    private BooleanPair(boolean first, boolean second) {
        super(first, second);
    }

    public static BooleanPair of(boolean f, boolean s) {
        return new BooleanPair(f, s);
    }
}
