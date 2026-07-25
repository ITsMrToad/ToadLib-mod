package com.mr_toad.lib.mtjava.math.interpolation;

import com.mr_toad.lib.mtjava.floats.func.FloatBiFunction;
import com.mr_toad.lib.mtjava.math.MtMath;
import net.minecraft.util.Mth;

public record InterpolationContext(float last, float current, float next, float postNext, float x) {

    public float lerp(float x) {
        return this.lerp((p1, p2) -> p1 + (p2 - p1) * x);
    }

    public float invLerp(float x) {
        return this.lerp((p1, p2) -> Mth.inverseLerp(x, p1, p2));
    }

    public float lerp(FloatBiFunction<Float> lerp) {
        float ab = lerp.apply(this.last(), this.current());
        float bc = lerp.apply(this.current(), this.next());
        float cd = lerp.apply(this.next(), this.postNext());
        float abc = lerp.apply(ab, bc);
        float bcd = lerp.apply(bc, cd);
        return lerp.apply(abc, bcd);
    }

    public float map(float x) {
        if (this.x() == 0.0F) {
            return this.last();
        } else if (this.x() == 1.0F) {
            return this.current();
        } else {
            return this.lerp(x);
        }
    }

    public float pow(int p, float f) {
        float x2 = this.x() * 2;
        if (x2 < 1.0F) {
            f = 0.0F;
        } else {
            x2 -= 2.0F;
        }
        return this.lerp(0.5F * MtMath.pow(x2, p) + f);
    }
}
