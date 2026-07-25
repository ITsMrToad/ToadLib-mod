package com.mr_toad.lib.mtjava.keyframe;

import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import com.mr_toad.lib.mtjava.math.vec.Vec2f;
import com.mr_toad.lib.mtjava.math.vec.Vec2i;
import com.mr_toad.lib.mtjava.math.vec.Vec3d;
import com.mr_toad.lib.mtjava.math.vec.Vec3f;
import it.unimi.dsi.fastutil.floats.Float2LongMap;
import net.minecraft.util.Mth;

public class KeyframeConverter {

    public static Vec2f interpolatef(Float2LongMap x, Float2LongMap y, Interpolation interpolation) {
        return interpolatef(new KeyframeChannel(x, interpolation), new KeyframeChannel(y, interpolation));
    }

    public static Vec2i interpolate(Float2LongMap x, Float2LongMap y, Interpolation interpolation) {
        return interpolate(new KeyframeChannel(x, interpolation), new KeyframeChannel(y, interpolation));
    }

    public static Vec3f interpolatef(Float2LongMap x, Float2LongMap y, Float2LongMap z, Interpolation interpolation) {
        return interpolatef(new KeyframeChannel(x, interpolation), new KeyframeChannel(y, interpolation), new KeyframeChannel(z, interpolation));
    }

    public static Vec3d interpolate(Float2LongMap x, Float2LongMap y, Float2LongMap z, Interpolation interpolation) {
        return interpolate(new KeyframeChannel(x, interpolation), new KeyframeChannel(y, interpolation), new KeyframeChannel(z, interpolation));
    }

    public static Vec2f interpolatef(KeyframeChannel x, KeyframeChannel y) {
        return new Vec2f(x.getValue(), y.getValue());
    }

    public static Vec2i interpolate(KeyframeChannel x, KeyframeChannel y) {
        return new Vec2i(Mth.floor(x.getValue()), Mth.floor(y.getValue()));
    }

    public static Vec3d interpolate(KeyframeChannel x, KeyframeChannel y, KeyframeChannel z) {
        return new Vec3d(interpolate(x), interpolate(y), interpolate(z));
    }

    public static Vec3f interpolatef(KeyframeChannel x, KeyframeChannel y, KeyframeChannel z) {
        return new Vec3f(x.getValue(), y.getValue(), z.getValue());
    }

    public static double interpolate(KeyframeChannel channel) {
        return channel.getValue();
    }
}
