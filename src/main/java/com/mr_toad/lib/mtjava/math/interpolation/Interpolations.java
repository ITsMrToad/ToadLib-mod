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
    //QUAD
    public static final ValueHolder<Interpolation> QUAD_IN = INTERPOLATIONS.register(Interpolation.of("quad_in", ctx -> ctx.lerp(Mth.square(ctx.x()))));
    public static final ValueHolder<Interpolation> QUAD_OUT = INTERPOLATIONS.register(Interpolation.of("quad_out", ctx -> ctx.lerp(ctx.x() * (ctx.x() - 2.0F))));
    public static final ValueHolder<Interpolation> QUAD_INOUT = INTERPOLATIONS.register(Interpolation.of("quad_inout", ctx -> {
        float x2 = ctx.x() * 2.0F;
        if (x2 < 1.0F) {
            return ctx.lerp(0.5F * Mth.square(x2));
        } else {
            x2 -= 1.0F;
            return ctx.lerp(0.5F * (x2 * (x2 - 2.0F) - 1.0F));
        }
    }));
    //CUBIC
    public static final ValueHolder<Interpolation> CUBIC_IN = INTERPOLATIONS.register(Interpolation.of("cubic_in", ctx -> ctx.lerp(MtMath.pow(ctx.x(), 3.0F))));
    public static final ValueHolder<Interpolation> CUBIC_OUT = INTERPOLATIONS.register(Interpolation.of("cubic_out", ctx -> ctx.lerp(MtMath.pow(ctx.x() - 1.0F, 3) + 1.0F)));
    public static final ValueHolder<Interpolation> CUBIC_INOUT = INTERPOLATIONS.register(Interpolation.of("cubic_inout", ctx -> ctx.pow(3, 2.0F)));
    //QUART
    public static final ValueHolder<Interpolation> QUART_IN = INTERPOLATIONS.register(Interpolation.of("quart_in", ctx -> ctx.lerp(MtMath.pow(ctx.x(), 4))));
    public static final ValueHolder<Interpolation> QUART_OUT = INTERPOLATIONS.register(Interpolation.of("quart_out", ctx -> ctx.lerp(MtMath.pow(ctx.x() - 1.0F, 4) - 1.0F)));
    public static final ValueHolder<Interpolation> QUART_INOUT = INTERPOLATIONS.register(Interpolation.of("quart_inout", ctx -> ctx.pow(4, -2.0F)));
    //QUINT
    public static final ValueHolder<Interpolation> QUINT_IN = INTERPOLATIONS.register(Interpolation.of("quint_in", ctx -> ctx.lerp(MtMath.pow(ctx.x(), 5))));
    public static final ValueHolder<Interpolation> QUINT_OUT = INTERPOLATIONS.register(Interpolation.of("quint_out", ctx -> ctx.lerp(MtMath.pow(ctx.x() - 1.0F, 5) - 1.0F)));
    public static final ValueHolder<Interpolation> QUINT_INOUT = INTERPOLATIONS.register(Interpolation.of("quint_inout", ctx -> ctx.pow(5, 2.0F)));
    //EXPONENTIAL
    public static final ValueHolder<Interpolation> EXP_IN = INTERPOLATIONS.register(Interpolation.of("exp_in", ctx -> ctx.x() == 0.0F ? ctx.last() : ctx.lerp(MtMath.pow(MtMath.E, 10 * ctx.x() - 10))));
    public static final ValueHolder<Interpolation> EXP_OUT = INTERPOLATIONS.register(Interpolation.of("exp_out", ctx -> ctx.x() == 1.0F ? ctx.current() : ctx.lerp(1 - MtMath.pow(MtMath.E, -10 * ctx.x()))));
    public static final ValueHolder<Interpolation> EXP_INOUT = INTERPOLATIONS.register(Interpolation.of("exp_inout", ctx -> {
        float r;
        if (ctx.x() > 0.5F) {
            r = MtMath.pow(MtMath.E, 20 * ctx.x() - 10);
        } else {
            r = 2 - MtMath.pow(MtMath.E, -20 * ctx.x() + 10);
        }
        return ctx.map(r / 2);
    }));
    //BACK
    public static final ValueHolder<Interpolation> BACK_IN = INTERPOLATIONS.register(Interpolation.of("back_in", ctx -> ctx.lerp(MtMath.E * ctx.x() * ctx.x() * ctx.x() - MtMath.HALF_E * ctx.x() * ctx.x())));
    public static final ValueHolder<Interpolation> BACK_OUT = INTERPOLATIONS.register(Interpolation.of("back_out", ctx -> ctx.lerp(1.0F + MtMath.E * MtMath.pow(ctx.x() - 1.0F, 3) + MtMath.HALF_E * MtMath.pow(ctx.x() - 1.0F, 2))));
    public static final ValueHolder<Interpolation> BACK_INOUT = INTERPOLATIONS.register(Interpolation.of("back_inout", ctx -> {
        float f = ctx.x() < 0.5F ? MtMath.pow(2.0F * ctx.x(), 2) * ((MtMath.E + 1) * 2.0F * ctx.x() - MtMath.E) : MtMath.pow(2.0F * ctx.x() - 2.0F, 2) * ((MtMath.E + 1.0F) * (ctx.x() * 2.0F - 2.0F) + MtMath.E) + 2.0F;
        return ctx.lerp(f / 2.0F);
    }));
    public static final ValueHolder<Interpolation> ELASTIC_IN = INTERPOLATIONS.register(Interpolation.of("elastic_in", ctx -> ctx.map(-MtMath.pow(2.0F, 10 * ctx.x() - 10.0F) * Mth.sin((ctx.x() * 10.0F - 10.75F) * Mth.TWO_PI / 3))));
    public static final ValueHolder<Interpolation> ELASTIC_OUT = INTERPOLATIONS.register(Interpolation.of("elastic_out", ctx -> ctx.map(MtMath.pow(2.0F, -10 * ctx.x()) * Mth.sin((ctx.x() * 10.0F - 0.75F) * Mth.TWO_PI / 3.0F) + 1.0F)));
    public static final ValueHolder<Interpolation> ELASTIC_INOUT = INTERPOLATIONS.register(Interpolation.of("elastic_inout", ctx -> {
        float f = Mth.TWO_PI / 4.5F;
        float f1 = (20.0F * ctx.x() - 11.125F) * f;
        return ctx.map(ctx.x() < 0.5F ? -(MtMath.pow(2.0F, 20 * ctx.x() - 10.0F) * Mth.sin(f1)) / 2.0F : (MtMath.pow(2.0F, -20 * ctx.x() + 10.0F) * Mth.sin(f1)) / 2.0F + 1.0F);
    }));
    //BOUNCE
    public static final ValueHolder<Interpolation> BOUNCE_IN = INTERPOLATIONS.register(Interpolation.of("bounce_in", ctx -> ctx.lerp(1.0F - bounce(1.0F - ctx.x(), 0.0F, 1.0F))));
    public static final ValueHolder<Interpolation> BOUNCE_OUT = INTERPOLATIONS.register(Interpolation.of("bounce_out", ctx -> bounce(ctx.x(), ctx.last(), ctx.current())));
    public static final ValueHolder<Interpolation> BOUNCE_INOUT = INTERPOLATIONS.register(Interpolation.of("bounce_inout", ctx -> {
        float f = ctx.x() < 0.5F ? 1.0F - bounce(1.0F - 2.0F * ctx.x(), 0.0F, 1.0F) : 1.0F + bounce(2.0F * ctx.x() - 1.0F, 0.0F, 1.0F);
        return ctx.lerp(f / 2.0F);
    }));
    //SINE
    public static final ValueHolder<Interpolation> SINE_IN = INTERPOLATIONS.register(Interpolation.of("sine_in", ctx -> ctx.lerp(1.0F - Mth.cos((ctx.x() * Mth.PI) / 2))));
    public static final ValueHolder<Interpolation> SINE_OUT = INTERPOLATIONS.register(Interpolation.of("sine_out", ctx -> ctx.lerp(Mth.sin((ctx.x() * Mth.PI) / 2))));
    public static final ValueHolder<Interpolation> SINE_INOUT = INTERPOLATIONS.register(Interpolation.of("sine_inout", ctx -> ctx.lerp(-0.5F * (Mth.cos(Mth.PI * ctx.x()) - 1.0F))));
    //CIRCLE
    public static final ValueHolder<Interpolation> CIRCLE_IN = INTERPOLATIONS.register(Interpolation.of("circle_in", ctx -> ctx.lerp(1.0F - Mth.sqrt(1 - MtMath.pow(Mth.clamp(ctx.x(), 0.0F, 1.0F), 2)))));
    public static final ValueHolder<Interpolation> CIRCLE_OUT = INTERPOLATIONS.register(Interpolation.of("circle_out", ctx -> ctx.lerp(Mth.sqrt(1 - MtMath.pow(Mth.clamp(ctx.x(), 0.0F, 1.0F) - 1.0F, 2)))));
    public static final ValueHolder<Interpolation> CIRCLE_INOUT = INTERPOLATIONS.register(Interpolation.of("circle_inout", ctx -> {
        float x2 = Mth.clamp(ctx.x(), 0.0F, 1.0F);
        float f = x2 < 0.5F ? 1.0F - Mth.sqrt(1.0F - MtMath.pow(2.0F * x2, 2)) : Mth.sqrt(1.0F - MtMath.pow(-2.0F * x2 + 2, 2)) + 1.0F;
        return ctx.lerp(f / 2.0F);
    }));
    //SPECIAL
    public static final ValueHolder<Interpolation> CUBIC = INTERPOLATIONS.register(Interpolation.of("cubic", ctx -> {
        float x2 = Mth.square(ctx.x());
        float x3 = x2 * ctx.x();
        return 0.5F * ((2.0F * ctx.current()) + (-ctx.last() + ctx.next()) * ctx.x() + (2.0F * ctx.last() - 5.0F * ctx.current() + 4 * ctx.next() - ctx.postNext()) * x2 + (-ctx.last() + 3 * ctx.current() - 3 * ctx.next() + ctx.postNext()) * x3);
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
            x2 = f * (x -= 1.5F / f1) * x + 0.75F;
        } else if (x < 2.5F / f1) {
            x2 = f * (x -= 2.25F / f1) * x + 0.9375F;
        } else {
            x2 = f * (x -= 2.625F / f1) * x + 0.984375F;
        }

        return Mth.lerp(x2, a, b);
    }
}


