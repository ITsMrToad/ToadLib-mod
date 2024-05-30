package com.mr_toad.lib.mtjava.floats;

import com.mojang.datafixers.util.Pair;

public final class FloatPair extends Pair<Float, Float> {

    public FloatPair(float first, float second) {
        super(first, second);
    }

    public static FloatPair of(float first, float second) {
        return new FloatPair(first, second);
    }
}