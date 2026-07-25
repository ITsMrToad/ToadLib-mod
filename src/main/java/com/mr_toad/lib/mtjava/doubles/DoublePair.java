package com.mr_toad.lib.mtjava.doubles;

import com.mojang.datafixers.util.Pair;
import com.mr_toad.lib.mtjava.floats.FloatPair;
import it.unimi.dsi.fastutil.SafeMath;

///@deprecated This class box primitives. Use {@link com.mr_toad.lib.mtjava.math.vec.Vec2d}
@Deprecated(since = "1.5.0")
public final class DoublePair extends Pair<Double, Double> {

    public DoublePair(double first, double second) {
        super(first, second);
    }

    public static DoublePair of(double first, double second) {
        return new DoublePair(first, second);
    }

    public FloatPair toFloatPair() {
        return FloatPair.of(SafeMath.safeDoubleToFloat(this.getFirst()), SafeMath.safeDoubleToFloat(this.getSecond()));
    }
}