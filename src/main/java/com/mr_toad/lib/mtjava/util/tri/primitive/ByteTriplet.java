package com.mr_toad.lib.mtjava.util.tri.primitive;

import com.mr_toad.lib.mtjava.util.tri.Triplet;
import it.unimi.dsi.fastutil.bytes.Byte2ByteFunction;

public final class ByteTriplet extends Triplet<Byte, Byte, Byte> {

    ByteTriplet(byte first, byte second, byte third) {
        super(first, second, third);
    }

    public static ByteTriplet of(final byte first, final byte second, final byte third) {
        return new ByteTriplet(first, second, third);
    }

    public ByteTriplet mapByteFirst(Byte2ByteFunction f) {
        return (ByteTriplet) this.mapFirst(f);
    }

    public ByteTriplet mapByteSecond(Byte2ByteFunction f) {
        return (ByteTriplet) this.mapSecond(f);
    }

    public ByteTriplet mapByteThird(Byte2ByteFunction f) {
        return (ByteTriplet) this.mapThird(f);
    }

    public IntTriplet convertToInt() {
        return new IntTriplet(this.first, this.second, this.third);
    }

}
