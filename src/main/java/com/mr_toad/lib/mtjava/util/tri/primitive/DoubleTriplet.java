package com.mr_toad.lib.mtjava.util.tri.primitive;

import com.mr_toad.lib.mtjava.util.tri.Triplet;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;

public final class DoubleTriplet extends Triplet<Double, Double, Double> {

    DoubleTriplet(double first, double second, double third) {
        super(first, second, third);
    }

    public static DoubleTriplet ofDouble(final double first, final double second, final double third) {
        return new DoubleTriplet(first, second, third);
    }

    public Triplet<Double, Double, Double> mapDoubleFirst(Double2DoubleFunction f) {
        return this.mapFirst(f);
    }

    public Triplet<Double, Double, Double> mapDoubleSecond(Double2DoubleFunction f) {
        return this.mapSecond(f);
    }

    public Triplet<Double, Double, Double> mapDoubleThird(Double2DoubleFunction f) {
        return this.mapThird(f);
    }

    public FloatTriplet convertToDouble() {
        return FloatTriplet.ofFloat(SafeMath.safeDoubleToFloat(this.first), SafeMath.safeDoubleToFloat(this.second), SafeMath.safeDoubleToFloat(this.third));
    }

}
