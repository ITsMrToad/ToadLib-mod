package com.mr_toad.lib.mtjava.math.interpolation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;

import org.jetbrains.annotations.NotNull;

public final class HermiteInterpolation implements Interpolation {

    private Tension tension = Tension.DEFAULT;
    private float bias = 0.0F;

    @Override
    public float interpolate(InterpolationContext ctx) {
        float x2 = Mth.square(ctx.x());
        float x3 = x2 * ctx.x();

        float tension = this.tension.getValue();
        float nTension = 1.0F - tension;
        float pBias = 1.0F + this.bias;
        float nBias = 1.0F - this.bias;

        float m1 = 0.5F * nTension * ((ctx.current() - ctx.last()) * pBias + (ctx.next() - ctx.current()) * nBias);
        float m2 = 0.5F * nTension * ((ctx.next() - ctx.current()) * pBias + (ctx.postNext() - ctx.next()) * nBias);

        float h00 = Mth.lerp(x3, Mth.lerp(x2, 1.0F, -3.0F), 2.0F);
        float h10 = Mth.lerp(x3, Mth.lerp(x2, 0.0F, -2.0F), 1.0F);
        float h01 = Mth.lerp(x3, Mth.lerp(x2, 0.0F, 3.0F), -2.0F);
        float h11 = Mth.lerp(x3, Mth.lerp(x2, 0.0F, -1.0F), 1.0F);

        return (h00 * ctx.current()) + (h10 * m1) + (h01 * ctx.next()) + (h11 * m2);
    }

    @Override
    public String name() {
        return "hermite";
    }

    @Override
    public Codec<Interpolation> codec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.BYTE.fieldOf("id").forGetter(Interpolations.INTERPOLATIONS::getIdOrThrow),
                Tension.CODEC.fieldOf("tension").forGetter(g -> ((HermiteInterpolation) g).getTension()),
                Codec.FLOAT.fieldOf("bias").forGetter(g -> ((HermiteInterpolation) g).getBias())
        ).apply(instance, (id, tens, bias) -> {
            HermiteInterpolation interpolation = new HermiteInterpolation();
            interpolation.setTension(tens);
            interpolation.setBias(bias);
            return interpolation;
        }));
    }

    public Tension getTension() {
        return this.tension;
    }

    public float getBias() {
        return this.bias;
    }

    public void setTension(Tension tension) {
        this.tension = tension;
    }

    public void setBias(float bias) {
        this.bias = bias;
    }

    public enum Tension implements StringRepresentable {
        HIGH(1.0F),
        DEFAULT(0.5F),
        NORMAL(0.0F),
        LOW(-0.5F),
        EXTREME_LOW(-1.0F);

        private static final Codec<Tension> CODEC = StringRepresentable.fromEnum(Tension::values);
        private final float value;

        Tension(float value) {
            this.value = value;
        }

        public float getValue() {
            return this.value;
        }

        @Override
        public @NotNull String getSerializedName() {
            return Float.toString(this.getValue());
        }

        public Component symbol() {
            return Component.literal(this.getSerializedName());
        }
    }
}
