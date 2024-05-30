package com.mr_toad.lib.mtjava.util.tri.primitive;

import com.mr_toad.lib.mtjava.util.tri.Triplet;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;

public final class FloatTriplet extends Triplet<Float, Float, Float> {
    FloatTriplet(float first, float second, float third) {
        super(first, second, third);
    }

    public static FloatTriplet ofFloat(final float first, final float second, final float third) {
        return new FloatTriplet(first, second, third);
    }

    public FloatTriplet mapFloatFirst(Float2FloatFunction f) {
        return (FloatTriplet) this.mapFirst(f);
    }

    public FloatTriplet mapFloatSecond(Float2FloatFunction f) {
        return (FloatTriplet) this.mapSecond(f);
    }

    public FloatTriplet mapFloatThird(Float2FloatFunction f) {
        return (FloatTriplet) this.mapThird(f);
    }

    public DoubleTriplet convertToDouble() {
        return DoubleTriplet.ofDouble(this.first, this.second, this.third);
    }

}
