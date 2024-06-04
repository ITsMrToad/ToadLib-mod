package com.mr_toad.lib.mtjava.util.quad.primitive;

import com.mr_toad.lib.mtjava.util.quad.Quadruplet;
import it.unimi.dsi.fastutil.longs.Long2LongFunction;

public final class LongQuadruplet extends Quadruplet<Long, Long, Long, Long> {

    LongQuadruplet(long first, long second, long third, long fourth) {
        super(first, second, third, fourth);
    }

    public LongQuadruplet mapLongFirst(Long2LongFunction f) {
        return (LongQuadruplet) this.mapFirst(f);
    }

    public LongQuadruplet mapLongSecond(Long2LongFunction f) {
        return (LongQuadruplet) this.mapSecond(f);
    }

    public LongQuadruplet mapLongThird(Long2LongFunction f) {
        return (LongQuadruplet) this.mapThird(f);
    }

    public LongQuadruplet mapLongFourth(Long2LongFunction f) {
        return (LongQuadruplet) this.mapFourth(f);
    }


}
