package com.mr_toad.lib.mtjava.floats;

import com.mojang.datafixers.util.Pair;

///@deprecated This class box primitives. Use {@link com.mr_toad.lib.mtjava.math.vec.Vec2f}
@Deprecated(since = "1.5.0")
public final class FloatPair extends Pair<Float, Float> {

    public FloatPair(float first, float second) {
        super(first, second);
    }

    public static FloatPair of(float first, float second) {
        return new FloatPair(first, second);
    }
}