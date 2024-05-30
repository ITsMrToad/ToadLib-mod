package com.mr_toad.lib.mtjava.util.tri.primitive;

import com.mr_toad.lib.mtjava.util.tri.Triplet;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;

public final class IntTriplet extends Triplet<Integer, Integer, Integer> {

    IntTriplet(int first, int second, int third) {
        super(first, second, third);
    }

    public IntTriplet mapIntFirst(Int2IntFunction f) {
        return (IntTriplet) this.mapFirst(f);
    }

    public IntTriplet mapIntSecond(Int2IntFunction f) {
        return (IntTriplet) this.mapSecond(f);
    }

    public IntTriplet mapIntThird(Int2IntFunction f) {
        return (IntTriplet) this.mapThird(f);
    }

    public ByteTriplet convertToByte() {
        return new ByteTriplet(SafeMath.safeIntToByte(this.first), SafeMath.safeIntToByte(this.second), SafeMath.safeIntToByte(this.third));
    }
}
