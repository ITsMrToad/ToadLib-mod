package com.mr_toad.lib.mtjava.math;

import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mr_toad.lib.mtjava.math.vec.Vec2f;
import com.mr_toad.lib.mtjava.math.vec.Vec3f;
import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;

import java.math.BigDecimal;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class MtMath {

    public static final Codec<BigDecimal> BIG_DECIMAL = ExtraCodecs.JSON.flatXmap(f -> {
        try {
            return DataResult.success(f.getAsBigDecimal());
        } catch (JsonParseException | UnsupportedOperationException e) {
            return DataResult.error(e::getMessage);
        }
    }, d -> {
        try {
            return DataResult.success(new JsonPrimitive(d));
        } catch (IllegalArgumentException e) {
            return DataResult.error(e::getMessage);
        }
    });

    public static final float EPSILON = 1E-6F;
    public static final float EPSILON2 = 1E-4F;
    public static final float E = 2.7182818284590452354F;
    public static final float HALF_E = E / 2.0F;
    public static final float PI_INV = 1.0F / Mth.PI;

    public static float pow(float value, float pow) {
        return (float) Math.pow(value, pow);
    }

    public static float exp(float v) {
        return (float) Math.exp(v);
    }

    public static float tan(float x) {
        return Mth.sin(x) / Mth.cos(x);
    }

    public static float cot(float x) {
        return 1 / tan(x);
    }

    public static int sqrt(int i) {
        return (int) Mth.sqrt(i);
    }

    public static int cbrt(int i) {
        return (int) Math.cbrt(i);
    }

    public static int sin(int x) {
        return (int) Mth.sin(x);
    }

    public static int cos(int x) {
        return (int) Mth.cos(x);
    }

    public static int tan(int x) {
        return sin(x) / cos(x);
    }

    public static int cot(int x) {
        return 1 / tan(x);
    }

    public static double cot(double x) {
        return 1.0D / Math.tan(x);
    }

    public static float acos(float x) {
        return Mth.HALF_PI - asin(x);
    }

    public static float asin(float x) {
        boolean negate = x < 0;
        x = Mth.abs(x);

        float ret = Mth.sqrt(1.0F - x) * (-0.0187293F * x + 0.0742610F);
        ret = ret * x - 0.2121144F;
        ret = ret * x + 1.5707288F;
        ret = 1.5707963F - ret * Mth.sqrt(1.0F - x);

        return negate ? -ret : ret;
    }

    public static byte bfloor(double v) {
        byte i = (byte) v;
        return v < i ? (byte) (i - 1) : i;
    }

    public static short sfloor(double v) {
        short i = (short) v;
        return v < i ? (short) (i - 1) : i;
    }

    public static short clamp(short v, short min, short max) {
        return min(max(v, min), max);
    }

    public static short min(short a, short b) {
        return a <= b ? a : b;
    }

    public static short max(short a, short b) {
        return a >= b ? a : b;
    }

    public static byte clamp(byte v, byte min, byte max) {
        return min(max(v, min), max);
    }

    public static byte min(byte a, byte b) {
        return a <= b ? a : b;
    }

    public static byte max(byte a, byte b) {
        return a >= b ? a : b;
    }

    public static int sign(float v) {
        if (v == 0.0F) {
            return 0;
        } else {
            return v > 0.0F ? 1 : -1;
        }
    }

    public static int hypot(int x, int y) {
        return (int) Math.hypot(x, y);
    }

    public static int trunc(int value) {
        return value - value % 2;
    }

    public static int average(int... i) {
        return IntStream.of(i).sum() / i.length;
    }

    public static float average(float... floats) {
        float f = 0.0F;
        for (float f1 : floats) f += f1;
        return f / floats.length;
    }

    public static double average(double... doubles) {
        OptionalDouble optionalDouble = DoubleStream.of(doubles).average();
        if (optionalDouble.isPresent()) {
            return optionalDouble.getAsDouble();
        }
        return 0;
    }

    public static int length(int x, int y) {
        return sqrt(lengthSquared(x, y));
    }

    public static int length(int x, int y, int z) {
        return sqrt(lengthSquared(x, y, z));
    }

    public static int length(int x, int y, int z, int w) {
        return sqrt(lengthSquared(x, y, z, w));
    }

    public static int lengthSquared(int x, int y) {
        return x * x + y * y;
    }

    public static int lengthSquared(int x, int y, int z) {
        return x * x + y * y + z * z;
    }

    public static int lengthSquared(int x, int y, int z, int w) {
        return x * x + y * y + z * z + w * w;
    }

    public static float length(float x, float y) {
        return Mth.sqrt(lengthSquared(x, y));
    }

    public static float length(float x, float y, float z) {
        return Mth.sqrt(lengthSquared(x, y, z));
    }

    public static float length(float x, float y, float z, float w) {
        return Mth.sqrt(lengthSquared(x, y, z, w));
    }

    public static float lengthSquared(float x, float y) {
        return x * x + y * y;
    }

    public static float lengthSquared(float x, float y, float z) {
        return x * x + y * y + z * z;
    }

    public static float lengthSquared(float x, float y, float z, float w) {
        return x * x + y * y + z * z + w * w;
    }

    public static double length(double x, double y) {
        return Math.sqrt(lengthSquared(x, y));
    }

    public static double length(double x, double y, double z) {
        return Math.sqrt(lengthSquared(x, y, z));
    }

    public static double length(double x, double y, double z, double w) {
        return Math.sqrt(lengthSquared(x, y, z, w));
    }

    public static double lengthSquared(double x, double y) {
        return x * x + y * y;
    }

    public static double lengthSquared(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static double lengthSquared(double x, double y, double z, double w) {
        return x * x + y * y + z * z + w * w;
    }

    public static float fact(float v) {
        int fact = 1;
        for (int i = 1; i <= v; i++) {
            fact *= i;
        }
        return fact;
    }

    public static boolean isNanOrInfinite(BigDecimal decimal) {
        return isNan(decimal) || isInfinite(decimal);
    }

    public static boolean isInfinite(BigDecimal decimal) {
        return Double.isInfinite(decimal.doubleValue());
    }

    public static boolean isNan(BigDecimal decimal) {
        return Double.isNaN(decimal.doubleValue());
    }

    public static <V extends DoubleVec<V>> V ceil(V vec) {
        return mapValuesVec(vec, Math::ceil);
    }

    public static <V extends FloatVec<V>> V ceil(V vec) {
        return mapValuesVec(vec, Mth::ceil);
    }

    public static <V extends DoubleVec<V>> V floor(V vec) {
        return mapValuesVec(vec, Math::floor);
    }

    public static <V extends FloatVec<V>> V floor(V vec) {
        return mapValuesVec(vec, Mth::floor);
    }

    public static <V extends DoubleVec<V>> V round(V vec) {
        return mapValuesVec(vec, Math::round);
    }

    public static <V extends FloatVec<V>> V round(V vec) {
        return mapValuesVec(vec, Math::round);
    }

    public static <V extends DoubleVec<V>> V mapValuesVec(V vec, Double2DoubleFunction mapper) {
        for (int i = 0; i < vec.values().size(); i++) {
            double old = vec.get(i);
            double newValue = mapper.apply(old);
            vec.set(i, newValue);
        }
        return vec;
    }

    public static <V extends FloatVec<V>> V mapValuesVec(V vec, Float2FloatFunction mapper) {
        for (int i = 0; i < vec.values().size(); i++) {
            float old = vec.get(i);
            float newValue = mapper.apply(old);
            vec.set(i, newValue);
        }
        return vec;
    }

    public static Vec2f getCenter(Vec2f vec1, Vec2f vec2) {
        float x = Mth.lerp(0.5F, vec1.x(), vec2.x());
        float y = Mth.lerp(0.5F, vec1.y(), vec2.y());
        return new Vec2f(x, y);
    }

    public static Vec3f getCenter(Vec3f vec1, Vec3f vec2) {
        float x = Mth.lerp(0.5F, vec1.x(), vec2.x());
        float y = Mth.lerp(0.5F, vec1.y(), vec2.y());
        float z = Mth.lerp(0.5F, vec1.z(), vec2.z());
        return new Vec3f(x, y, z);
    }
}
