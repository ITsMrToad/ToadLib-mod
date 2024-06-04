package com.mr_toad.lib.mtjava.util.quad.primitive;

import com.mr_toad.lib.mtjava.util.quad.Quadruplet;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;

public final class FloatQuadruplet extends Quadruplet<Float, Float, Float, Float> {

    FloatQuadruplet(float first, float second, float third, float fourth) {
        super(first, second, third, fourth);
    }

    public FloatQuadruplet mapFloatFirst(Float2FloatFunction f) {
        return (FloatQuadruplet) this.mapFirst(f);
    }

    public FloatQuadruplet mapFloatSecond(Float2FloatFunction f) {
        return (FloatQuadruplet) this.mapSecond(f);
    }

    public FloatQuadruplet mapFloatThird(Float2FloatFunction f) {
        return (FloatQuadruplet) this.mapThird(f);
    }

    public FloatQuadruplet mapFloatFourth(Float2FloatFunction f) {
        return (FloatQuadruplet) this.mapFourth(f);
    }

    public DoubleQuadruplet convertToDouble() {
        return new DoubleQuadruplet(this.first, this.second, this.third, this.fourth);
    }

}
