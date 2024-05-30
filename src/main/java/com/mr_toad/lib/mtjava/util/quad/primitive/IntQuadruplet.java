package com.mr_toad.lib.mtjava.util.quad.primitive;

import com.mr_toad.lib.mtjava.util.quad.Quadruplet;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;

public final class IntQuadruplet extends Quadruplet<Integer, Integer, Integer, Integer> {

    IntQuadruplet(int first, int second, int third, int fourth) {
        super(first, second, third, fourth);
    }

    public IntQuadruplet mapIntFirst(Int2IntFunction f) {
        return (IntQuadruplet) this.mapFirst(f);
    }

    public IntQuadruplet mapIntSecond(Int2IntFunction f) {
        return (IntQuadruplet) this.mapSecond(f);
    }

    public IntQuadruplet mapIntThird(Int2IntFunction f) {
        return (IntQuadruplet) this.mapThird(f);
    }

    public IntQuadruplet mapIntFourth(Int2IntFunction f) {
        return (IntQuadruplet) this.mapFourth(f);
    }

    public ByteQuadruplet convertToByte() {
        return new ByteQuadruplet(SafeMath.safeIntToByte(this.first), SafeMath.safeIntToByte(this.second), SafeMath.safeIntToByte(this.third), SafeMath.safeIntToByte(this.fourth));
    }
}