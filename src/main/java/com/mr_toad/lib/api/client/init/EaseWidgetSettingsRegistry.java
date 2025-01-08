package com.mr_toad.lib.api.client.init;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mr_toad.lib.api.helper.registry.common.ValueHolder;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ForgeSlider;

import org.jetbrains.annotations.ApiStatus;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class EaseWidgetSettingsRegistry {

    public static final Map<Interpolation, EnumSet<Arg>> EASE_REGISTRY = Maps.newHashMap();

    @ApiStatus.Internal
    public static void init() {
        registerEase(ImmutableSet.of(Interpolations.BACK_IN, Interpolations.BACK_OUT, Interpolations.BACK_INOUT, Interpolations.ELASTIC_IN, Interpolations.ELASTIC_OUT, Interpolations.ELASTIC_INOUT), EnumSet.of(Arg.A));
        registerEase(ImmutableSet.of(Interpolations.BOUNCE_IN, Interpolations.BOUNCE_OUT, Interpolations.BOUNCE_INOUT), EnumSet.of(Arg.A, Arg.B));
        registerEase(Interpolations.CUBIC, EnumSet.of(Arg.A, Arg.B));
    }

    public static void registerEase(Set<ValueHolder<Interpolation>> interpolations, EnumSet<Arg> args) {
        interpolations.forEach(interpolation -> registerEase(interpolation, args));
    }

    public static void registerEase(ValueHolder<Interpolation> interpolation, EnumSet<Arg> args) {
        Interpolation i = interpolation.get();

        if (EASE_REGISTRY.containsKey(i)) {
            ToadLib.LOGGER.error("Widget ease settings is already registered for '{}'", i.name());
        } else {
            EASE_REGISTRY.put(i, args);
        }
    }

    @ApiStatus.Internal
    public static void resolve(ForgeSlider a, ForgeSlider b, ForgeSlider c, ForgeSlider d, Interpolation interpolation) {
        EnumSet<Arg> args = EASE_REGISTRY.get(interpolation);
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
