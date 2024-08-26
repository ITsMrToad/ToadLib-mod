package com.mr_toad.lib.mtjava.booleans;

import com.mojang.datafixers.util.Pair;

public class BooleanPair extends Pair<Boolean, Boolean> {

    private BooleanPair(boolean first, boolean second) {
        super(first, second);
    }

    public static BooleanPair of(boolean f, boolean s) {
        return new BooleanPair(f, s);
    }
}
