package com.mr_toad.lib.mtjava.util.tri.primitive;

import com.mr_toad.lib.mtjava.util.tri.Triplet;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.longs.Long2LongFunction;

public final class LongTriplet extends Triplet<Long, Long, Long> {

    LongTriplet(long first, long second, long third) {
        super(first, second, third);
    }

    public LongTriplet mapLongFirst(Long2LongFunction f) {
        return (LongTriplet) this.mapFirst(f);
    }

    public LongTriplet mapLongSecond(Long2LongFunction f) {
        return (LongTriplet) this.mapSecond(f);
    }

    public LongTriplet mapLongThird(Long2LongFunction f) {
        return (LongTriplet) this.mapThird(f);
    }

}
