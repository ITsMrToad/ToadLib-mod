package com.mr_toad.lib.mtjava.math.interpolation;

import com.mr_toad.lib.api.helper.registry.SmallIdRegistry;
import com.mr_toad.lib.api.helper.registry.common.ValueHolder;
import com.mr_toad.lib.mtjava.math.MtMath;
import net.minecraft.util.Mth;

public class Interpolations {

    public static final SmallIdRegistry<Interpolation> INTERPOLATIONS = new SmallIdRegistry<>("toadlib.interpolations");

    //BASE
    public static final ValueHolder<Interpolation> CONST = INTERPOLATIONS.register(Interpolation.of("const", InterpolationContext::last));
    public static final ValueHolder<Interpolation> LINEAR = INTERPOLATIONS.register(Interpolation.of("linear", ctx -> ctx.lerp(ctx.x())));
    public static final ValueHolder<Interpolation> INV_LINEAR = INTERPOLATIONS.register(Interpolation.of("inv_linear", ctx -> ctx.invLerp(ctx.x())));

    // QUAD
    public static final ValueHolder<Interpolation> QUAD_IN = INTERPOLATIONS.register(Interpolation.of("quad_in", ctx -> ctx.lerp(Mth.square(ctx.x()))));
    public static final ValueHolder<Interpolation> QUAD_OUT = INTERPOLATIONS.register(Interpolation.of("quad_out", ctx -> ctx.lerp(ctx.x() * (2.0F - ctx.x()))));
    public static final ValueHolder<Interpolation> QUAD_INOUT = INTERPOLATIONS.register(Interpolation.of("quad_inout", ctx -> {
        float x2 = ctx.x() * 2.0F;
        if (x2 < 1.0F) {
            return ctx.lerp(0.5F * x2 * x2);
        } else {
            x2 -= 1.0F;
            return ctx.lerp(-0.5F * (x2 * (x2 - 2.0F) - 1.0F));
        }
    }));

    // CUBIC
    public static final ValueHolder<Interpolation> CUBIC_IN = INTERPOLATIONS.register(Interpolation.of("cubic_in", ctx -> {
        float x = ctx.x();
        return ctx.lerp(x * x * x);
    }));
    public static final ValueHolder<Interpolation> CUBIC_OUT = INTERPOLATIONS.register(Interpolation.of("cubic_out", ctx -> {
        float f = ctx.x() - 1.0F;
        return ctx.lerp(f * f * f + 1.0F);
    }));
    public static final ValueHolder<Interpolation> CUBIC_INOUT = INTERPOLATIONS.register(Interpolation.of("cubic_inout", ctx -> {
        float x2 = ctx.x() * 2.0F;
        if (x2 < 1.0F) {
            return ctx.lerp(0.5F * x2 * x2 * x2);
        } else {
            x2 -= 2.0F;
            return ctx.lerp(0.5F * (x2 * x2 * x2 + 2.0F));
        }
    }));

    // QUART
    public static final ValueHolder<Interpolation> QUART_IN = INTERPOLATIONS.register(Interpolation.of("quart_in", ctx -> {
        float x2 = Mth.square(ctx.x());
        return ctx.lerp(x2 * x2);
    }));
    public static final ValueHolder<Interpolation> QUART_OUT = INTERPOLATIONS.register(Interpolation.of("quart_out", ctx -> {
        float f = ctx.x() - 1.0F;
        return ctx.lerp(1.0F - f * f * f * f);
    }));
    public static final ValueHolder<Interpolation> QUART_INOUT = INTERPOLATIONS.register(Interpolation.of("quart_inout", ctx -> {
        float x2 = ctx.x() * 2.0F;
        if (x2 < 1.0F) {
            float f = x2 * x2;
            return ctx.lerp(0.5F * f * f);
        } else {
            float f = x2 - 2.0F;
            return ctx.lerp(-0.5F * (f * f * f * f - 2.0F));
        }
    }));

    // QUINT
    public static final ValueHolder<Interpolation> QUINT_IN = INTERPOLATIONS.register(Interpolation.of("quint_in", ctx -> {
        float x = ctx.x();
        float x2 = x * x;
        return ctx.lerp(x2 * x2 * x);
    }));
    public static final ValueHolder<Interpolation> QUINT_OUT = INTERPOLATIONS.register(Interpolation.of("quint_out", ctx -> {
        float f = ctx.x() - 1.0F;
        float f2 = f * f;
        return ctx.lerp(f2 * f2 * f + 1.0F);
    }));
    public static final ValueHolder<Interpolation> QUINT_INOUT = INTERPOLATIONS.register(Interpolation.of("quint_inout", ctx -> {
        float x2 = ctx.x() * 2.0F;
        if (x2 < 1.0F) {
            float f2 = x2 * x2;
            return ctx.lerp(0.5F * f2 * f2 * x2);
        } else {
            float f = x2 - 2.0F;
            float f2 = f * f;
            return ctx.lerp(0.5F * (f2 * f2 * f + 2.0F));
        }
    }));

    // EXPONENTIAL
    public static final ValueHolder<Interpolation> EXP_IN = INTERPOLATIONS.register(Interpolation.of("exp_in", ctx ->
            ctx.x() == 0.0F ? ctx.last() : ctx.lerp(MtMath.pow(2.0F, 10.0F * ctx.x() - 10.0F))
    ));
    public static final ValueHolder<Interpolation> EXP_OUT = INTERPOLATIONS.register(Interpolation.of("exp_out", ctx ->
            ctx.x() == 1.0F ? ctx.current() : ctx.lerp(1.0F - MtMath.pow(2.0F, -10.0F * ctx.x()))
    ));
    public static final ValueHolder<Interpolation> EXP_INOUT = INTERPOLATIONS.register(Interpolation.of("exp_inout", ctx -> {
        if (ctx.x() == 0.0F) return ctx.last();
        if (ctx.x() == 1.0F) return ctx.current();
        float x2 = ctx.x() * 2.0F;
        if (x2 < 1.0F) {
            return ctx.lerp(0.5F * MtMath.pow(2.0F, 10.0F * x2 - 10.0F));
        } else {
            return ctx.lerp(0.5F * (2.0F - MtMath.pow(2.0F, -10.0F * (x2 - 1.0F))));
        }
    }));

    // BACK (s = 1.70158F)
    private static final float BACK_S = 1.70158F;
    private static final float BACK_S2 = BACK_S * 1.525F;

    public static final ValueHolder<Interpolation> BACK_IN = INTERPOLATIONS.register(Interpolation.of("back_in", ctx -> {
        float x = ctx.x();
        return ctx.lerp(x * x * ((BACK_S + 1.0F) * x - BACK_S));
    }));
    public static final ValueHolder<Interpolation> BACK_OUT = INTERPOLATIONS.register(Interpolation.of("back_out", ctx -> {
        float f = ctx.x() - 1.0F;
        return ctx.lerp(f * f * ((BACK_S + 1.0F) * f + BACK_S) + 1.0F);
    }));
    public static final ValueHolder<Interpolation> BACK_INOUT = INTERPOLATIONS.register(Interpolation.of("back_inout", ctx -> {
        float x2 = ctx.x() * 2.0F;
        if (x2 < 1.0F) {
            return ctx.lerp(0.5F * (x2 * x2 * ((BACK_S2 + 1.0F) * x2 - BACK_S2)));
        } else {
            x2 -= 2.0F;
            return ctx.lerp(0.5F * (x2 * x2 * ((BACK_S2 + 1.0F) * x2 + BACK_S2) + 2.0F));
        }
    }));

    // ELASTIC
    public static final ValueHolder<Interpolation> ELASTIC_IN = INTERPOLATIONS.register(Interpolation.of("elastic_in", ctx ->
            ctx.map(-MtMath.pow(2.0F, 10.0F * ctx.x() - 10.0F) * Mth.sin((ctx.x() * 10.0F - 10.75F) * Mth.TWO_PI / 3.0F))
    ));
    public static final ValueHolder<Interpolation> ELASTIC_OUT = INTERPOLATIONS.register(Interpolation.of("elastic_out", ctx ->
            ctx.map(MtMath.pow(2.0F, -10.0F * ctx.x()) * Mth.sin((ctx.x() * 10.0F - 0.75F) * Mth.TWO_PI / 3.0F) + 1.0F)
    ));
    public static final ValueHolder<Interpolation> ELASTIC_INOUT = INTERPOLATIONS.register(Interpolation.of("elastic_inout", ctx -> {
        float f = Mth.TWO_PI / 4.5F;
        float f1 = (20.0F * ctx.x() - 11.125F) * f;
        return ctx.map(ctx.x() < 0.5F ? -(MtMath.pow(2.0F, 20.0F * ctx.x() - 10.0F) * Mth.sin(f1)) / 2.0F : (MtMath.pow(2.0F, -20.0F * ctx.x() + 10.0F) * Mth.sin(f1)) / 2.0F + 1.0F);
    }));

    // BOUNCE
    public static final ValueHolder<Interpolation> BOUNCE_IN = INTERPOLATIONS.register(Interpolation.of("bounce_in", ctx -> ctx.lerp(1.0F - bounce(1.0F - ctx.x(), 0.0F, 1.0F))));
    public static final ValueHolder<Interpolation> BOUNCE_OUT = INTERPOLATIONS.register(Interpolation.of("bounce_out", ctx -> bounce(ctx.x(), ctx.last(), ctx.current())));
    public static final ValueHolder<Interpolation> BOUNCE_INOUT = INTERPOLATIONS.register(Interpolation.of("bounce_inout", ctx -> {
        float f = ctx.x() < 0.5F ? 1.0F - bounce(1.0F - 2.0F * ctx.x(), 0.0F, 1.0F) : 1.0F + bounce(2.0F * ctx.x() - 1.0F, 0.0F, 1.0F);
        return ctx.lerp(f / 2.0F);
    }));

    // SINE
    public static final ValueHolder<Interpolation> SINE_IN = INTERPOLATIONS.register(Interpolation.of("sine_in", ctx -> ctx.lerp(1.0F - Mth.cos((ctx.x() * Mth.PI) / 2.0F))));
    public static final ValueHolder<Interpolation> SINE_OUT = INTERPOLATIONS.register(Interpolation.of("sine_out", ctx -> ctx.lerp(Mth.sin((ctx.x() * Mth.PI) / 2.0F))));
    public static final ValueHolder<Interpolation> SINE_INOUT = INTERPOLATIONS.register(Interpolation.of("sine_inout", ctx -> ctx.lerp(-0.5F * (Mth.cos(Mth.PI * ctx.x()) - 1.0F))));

    // CIRCLE
    public static final ValueHolder<Interpolation> CIRCLE_IN = INTERPOLATIONS.register(Interpolation.of("circle_in", ctx -> {
        float x = Mth.clamp(ctx.x(), 0.0F, 1.0F);
        return ctx.lerp(1.0F - Mth.sqrt(1.0F - x * x));
    }));
    public static final ValueHolder<Interpolation> CIRCLE_OUT = INTERPOLATIONS.register(Interpolation.of("circle_out", ctx -> {
        float f = Mth.clamp(ctx.x(), 0.0F, 1.0F) - 1.0F;
        return ctx.lerp(Mth.sqrt(1.0F - f * f));
    }));
    public static final ValueHolder<Interpolation> CIRCLE_INOUT = INTERPOLATIONS.register(Interpolation.of("circle_inout", ctx -> {
        float x2 = Mth.clamp(ctx.x(), 0.0F, 1.0F) * 2.0F;
        if (x2 < 1.0F) {
            return ctx.lerp(-0.5F * (Mth.sqrt(1.0F - x2 * x2) - 1.0F));
        } else {
            x2 -= 2.0F;
            return ctx.lerp(0.5F * (Mth.sqrt(1.0F - x2 * x2) + 1.0F));
        }
    }));

    // SPECIAL
    public static final ValueHolder<Interpolation> CUBIC = INTERPOLATIONS.register(Interpolation.of("cubic", ctx -> {
        float x2 = Mth.square(ctx.x());
        float x3 = x2 * ctx.x();
        return 0.5F * ((2.0F * ctx.current()) + (-ctx.last() + ctx.next()) * ctx.x() + (2.0F * ctx.last() - 5.0F * ctx.current() + 4.0F * ctx.next() - ctx.postNext()) * x2 + (-ctx.last() + 3.0F * ctx.current() - 3.0F * ctx.next() + ctx.postNext()) * x3);
    }));
    public static final ValueHolder<Interpolation> CATMULLROM = INTERPOLATIONS.register(Interpolation.of("catmullrom", ctx -> Mth.catmullrom(ctx.x(), ctx.last(), ctx.current(), ctx.next(), ctx.postNext())));
    public static final ValueHolder<Interpolation> HERMITE = INTERPOLATIONS.register(new HermiteInterpolation());

    private static float bounce(float x, float a, float b) {
        float f = 7.5625F;
        float f1 = 2.75F;
        float x2;
        if (x < 1.0F / f1) {
            x2 = f * x * x;
        } else if (x < 2.0F / f1) {
            float xSub = x - (1.5F / f1);
            x2 = f * xSub * xSub + 0.75F;
        } else if (x < 2.5F / f1) {
            float xSub = x - (2.25F / f1);
            x2 = f * xSub * xSub + 0.9375F;
        } else {
            float xSub = x - (2.625F / f1);
            x2 = f * xSub * xSub + 0.984375F;
        }
        return Mth.lerp(x2, a, b);
    }
}


