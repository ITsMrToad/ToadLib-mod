package com.mr_toad.lib.mtjava.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mr_toad.lib.mtjava.util.ImmutablePair;
import com.mr_toad.lib.mtjava.util.ImmutableTuple;
import net.minecraft.util.FastColor;
import net.minecraft.util.Tuple;
import org.apache.commons.lang3.StringUtils;

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

    public static<A, B, T extends Tuple<A, B>> ImmutableTuple<A, B> toImmutableTuple(T tuple) {
        return new ImmutableTuple<>(tuple.getA(), tuple.getB());
    }

    public static void validateDegrees(float... degreesArr) {
        for (float degrees : degreesArr) {
            if (degrees > 180 || degrees < -180) {
                throw new IllegalArgumentException("Illegal degree value: '" + degrees + "'");
            }
        }
    }

    public static void validatePercents(double... percentArr) {
        Arrays.stream(percentArr).forEach(percent -> {
            if (percent < 0.0D || percent > 1.0D) {
                throw new IllegalArgumentException("Illegal percent value: '" + percent + "'");
            }
        });
    }

    public static void validateDegrees(float degrees) {
        if (degrees > 180 || degrees < -180) {
            throw new IllegalArgumentException("Illegal degree value: '" + degrees + "'");
        }
    }

    public static void validatePercents(double percent) {
        if (percent < 0.0D || percent > 1.0D) {
            throw new IllegalArgumentException("Illegal percent value: '" + percent + "'");
        }
    }

    public static<K, V>ImmutableMap<K, V> inverseMap(Map<V, K> source) {
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
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
        return FastColor.ARGB32.color(255, r, g, b);
    }

    public static String rgb2Hex(int color, boolean hasAlpha) {
        return "#" + StringUtils.leftPad(Integer.toHexString(color), hasAlpha ? 8 : 6, '0');
    }
}
