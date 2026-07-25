package com.mr_toad.lib.mtjava.ints;

import com.mojang.datafixers.util.Pair;

///@deprecated This class box primitives. Use {@link com.mr_toad.lib.mtjava.math.vec.Vec2i}
@Deprecated(since = "1.5.0")
public final class IntPair extends Pair<Integer, Integer> {

    public IntPair(int first, int second) {
        super(first, second);
    }

    public static IntPair of(int first, int second) {
        return new IntPair(first, second);
    }
}