package com.mr_toad.lib.mtjava;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mr_toad.lib.mtjava.util.ImmutablePair;
import com.mr_toad.lib.mtjava.util.ImmutableTuple;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import org.apache.commons.lang3.StringUtils;

import org.jetbrains.annotations.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Pattern;

public class MtJava {

    public static final Pattern HEXADECIMAL = Pattern.compile("\\p{XDigit}+");
    public static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("HH:mm:ss:SS");
    public static final Runnable NOTHING = () -> {};

    public static<F, S, P extends Pair<F, S>> ImmutablePair<F, S> toImmutablePair(P pair) {
        return ImmutablePair.of(pair.getFirst(), pair.getSecond());
    }

    public static<A, B, T extends Tuple<@NotNull A, @NotNull B>> ImmutableTuple<A, B> toImmutableTuple(T tuple) {
        return new ImmutableTuple<>(tuple.getA(), tuple.getB());
    }

    public static double percents(double percent) {
        return Mth.clamp(percent, 0.0D, 1.0D);
    }

    public static float percents(float percent) {
        return Mth.clamp(percent, 0.0F, 1.0F);
    }

    public static double degrees(double deg) {
        return Mth.clamp(deg, -180.0D, 180.0D);
    }

    public static float degrees(float deg) {
        return Mth.clamp(deg, -180.0F, 180.0F);
    }

    ///@deprecated throw exception
    @Deprecated(since = "1.5.0")
    public static void validateDegrees(float degrees) {
        if (degrees > 180 || degrees < -180) {
            throw new IllegalArgumentException("Illegal degree value: '" + degrees + "'");
        }
    }

    ///@deprecated throw exception
    @Deprecated(since = "1.5.0")
    public static void validatePercents(double percent) {
        if (percent < 0.0D || percent > 1.0D) {
            throw new IllegalArgumentException("Illegal percent value: '" + percent + "'");
        }
    }

    public static<K, V>ImmutableMap<@NotNull K, @NotNull V> inverseMap(Map<V, K> source) {
        ImmutableMap.Builder<@NotNull K, @NotNull V> builder = ImmutableMap.builder();
        source.forEach((v, k) -> builder.put(k, v));
        return builder.build();
    }

    public static String timestamp(long millis) {
        return TIMESTAMP_FORMAT.format(millis);
    }

    public static int rgb2Argb(int input) {
        int r = (input >> 16) & 255;
        int g = (input >> 8) & 255;
        int b = input & 255;
        return ARGB.color(255, r, g, b);
    }

    public static String rgb2Hex(int color, boolean hasAlpha) {
        return "#" + StringUtils.leftPad(Integer.toHexString(color), hasAlpha ? 8 : 6, '0');
    }
}
