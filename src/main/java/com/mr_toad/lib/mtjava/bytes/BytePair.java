package com.mr_toad.lib.mtjava.bytes;

import com.mojang.datafixers.util.Pair;

public final class BytePair extends Pair<Byte, Byte> {

    public BytePair(byte first, byte second) {
        super(first, second);
    }

    public static BytePair of(byte first, byte second) {
        return new BytePair(first, second);
    }

}