package com.mr_toad.lib.mtjava.util.quad.primitive;

import com.mr_toad.lib.mtjava.util.quad.Quadruplet;
import it.unimi.dsi.fastutil.bytes.Byte2ByteFunction;

public final class ByteQuadruplet extends Quadruplet<Byte, Byte, Byte, Byte> {

    ByteQuadruplet(byte first, byte second, byte third, byte fourth) {
        super(first, second, third, fourth);
    }

    public ByteQuadruplet mapByteFirst(Byte2ByteFunction f) {
        return (ByteQuadruplet) this.mapFirst(f);
    }

    public ByteQuadruplet mapByteSecond(Byte2ByteFunction f) {
        return (ByteQuadruplet) this.mapSecond(f);
    }

    public ByteQuadruplet mapByteThird(Byte2ByteFunction f) {
        return (ByteQuadruplet) this.mapThird(f);
    }

    public ByteQuadruplet mapByteFourth(Byte2ByteFunction f) {
        return (ByteQuadruplet) this.mapFourth(f);
    }

    public IntQuadruplet convertToInt() {
        return new IntQuadruplet(this.first, this.second, this.third, this.fourth);
    }

}
