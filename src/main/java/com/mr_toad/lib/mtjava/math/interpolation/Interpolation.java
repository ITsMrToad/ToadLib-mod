package com.mr_toad.lib.mtjava.math.interpolation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mr_toad.lib.mtjava.floats.func.ToFloatFunction;
import net.minecraft.network.chat.Component;

public interface Interpolation {

    static Interpolation of(String name, ToFloatFunction<InterpolationContext> getter) {
        return new Interpolation() {
            @Override
            public float interpolate(InterpolationContext ctx) {
                return getter.applyAsFloat(ctx);
            }

            @Override
            public String name() {
                return name;
            }

            @Override
            public Codec<Interpolation> codec() {
                return RecordCodecBuilder.create(instance -> instance.group(Codec.BYTE.fieldOf("interpolation").forGetter(Interpolations.INTERPOLATIONS::getIdOrThrow)).apply(instance, id -> Interpolations.INTERPOLATIONS.getOrThrow(id).get()));
            }
        };
    }

    float interpolate(InterpolationContext ctx);

    String name();

    Codec<Interpolation> codec();

    default float interpolate(float last, float current, float next, float postNext, float x) {
        return this.interpolate(new InterpolationContext(last, current, next, postNext, x));
    }

    default Component getName() {
        return Component.translatable("toadlib.interpolation." + this.name());
    }

    default Component getTooltip() {
        return Component.translatable("toadlib.interpolation." + this.name() + ".tooltip");
    }
}
