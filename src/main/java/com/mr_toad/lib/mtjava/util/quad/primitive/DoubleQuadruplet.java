package com.mr_toad.lib.mtjava.util.quad.primitive;

import com.mr_toad.lib.mtjava.util.quad.Quadruplet;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;

public final class DoubleQuadruplet extends Quadruplet<Double, Double, Double, Double> {

    DoubleQuadruplet(double first, double second, double third, double fourth) {
        super(first, second, third, fourth);
    }

    public DoubleQuadruplet mapDoubleFirst(Double2DoubleFunction f) {
        return (DoubleQuadruplet) this.mapFirst(f);
    }

    public DoubleQuadruplet mapDoubleSecond(Double2DoubleFunction f) {
        return (DoubleQuadruplet) this.mapSecond(f);
    }

    public DoubleQuadruplet mapDoubleThird(Double2DoubleFunction f) {
        return (DoubleQuadruplet) this.mapThird(f);
    }

    public DoubleQuadruplet mapDoubleFourth(Double2DoubleFunction f) {
        return (DoubleQuadruplet) this.mapFourth(f);
    }

    public FloatQuadruplet convertToDouble() {
        return new FloatQuadruplet(SafeMath.safeDoubleToFloat(this.first), SafeMath.safeDoubleToFloat(this.second), SafeMath.safeDoubleToFloat(this.third), SafeMath.safeDoubleToFloat(this.fourth));
    }
}