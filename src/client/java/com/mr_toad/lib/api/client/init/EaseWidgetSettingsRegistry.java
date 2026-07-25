package com.mr_toad.lib.api.client.init;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mr_toad.lib.api.client.screen.ex.widget.ExSlider;
import com.mr_toad.lib.api.helper.registry.common.ValueHolder;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.jetbrains.annotations.ApiStatus;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class EaseWidgetSettingsRegistry {

    public static final Map<Interpolation, Set<Arg>> EASE_REGISTRY = Maps.newHashMap();

    @ApiStatus.Internal
    public static void init() {
        registerEase(ImmutableSet.of(Interpolations.BACK_IN, Interpolations.BACK_OUT, Interpolations.BACK_INOUT, Interpolations.ELASTIC_IN, Interpolations.ELASTIC_OUT, Interpolations.ELASTIC_INOUT), EnumSet.of(Arg.A));
        registerEase(ImmutableSet.of(Interpolations.BOUNCE_IN, Interpolations.BOUNCE_OUT, Interpolations.BOUNCE_INOUT), EnumSet.of(Arg.A, Arg.B));
        registerEase(Interpolations.CUBIC, EnumSet.of(Arg.A, Arg.B));
    }

    public static void registerEase(Set<ValueHolder<Interpolation>> interpolations, Set<Arg> args) {
        interpolations.forEach(interpolation -> registerEase(interpolation, args));
    }

    public static void registerEase(ValueHolder<Interpolation> interpolation, Set<Arg> args) {
        Interpolation i = interpolation.get();

        if (EASE_REGISTRY.containsKey(i)) {
            ToadLib.LOGGER.error("Widget ease settings is already registered for '{}'", i.name());
        } else {
            EASE_REGISTRY.put(i, args);
        }
    }

    @ApiStatus.Internal
    public static void resolve(ExSlider a, ExSlider b, ExSlider c, ExSlider d, Interpolation interpolation) {
        Set<Arg> args = EASE_REGISTRY.get(interpolation);
        if (args == null || args.isEmpty()) {
            return;
        }

        a.active = args.contains(Arg.A);
        b.active = args.contains(Arg.B);
        c.active = args.contains(Arg.C);
        d.active = args.contains(Arg.D);
    }

    public enum Arg {
        A,
        B,
        C,
        D
    }
}
