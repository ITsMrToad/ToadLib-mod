package com.mr_toad.lib.api.util;

import com.mr_toad.lib.mtjava.util.tri.primitive.DoubleTriplet;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Mob;

import org.jetbrains.annotations.NotNull;
import java.util.function.Function;

public record SpawnLingeringCloudData(Mob mob, float radius, float radiusOnUse, float radiusGrowth, int waitTime, int duration) {

    public static final Logic BY_ACTIVE_EFFECTS = (m, cloud) -> {
        if (!m.getActiveEffects().isEmpty()) {
            for (MobEffectInstance effectInstance : m.getActiveEffects()) {
                cloud.addEffect(new MobEffectInstance(effectInstance));
            }
        }
    };

    public static final Function<Holder<@NotNull MobEffect>, Logic> ALWAYS = effect -> (m, cloud) -> cloud.addEffect(new MobEffectInstance(effect));
    public static final Function<Holder<@NotNull MobEffect>, Logic> IF_NOT_HAS = effect -> (m, cloud) -> {
        if (!m.hasEffect(effect)) {
            cloud.addEffect(new MobEffectInstance(effect));
        }
    };

    public static SpawnLingeringCloudData withDefaultRadiusPerTick(Mob mob, float radius, float radiusOnUse, int waitTime, int duration) {
        return new SpawnLingeringCloudData(mob, radius, radiusOnUse, radius / duration, waitTime, duration);
    }

    public void spawn(Logic logic) {
        this.spawn(logic, DoubleTriplet.of(this.mob.getX(), this.mob.getY(), this.mob.getZ()));
    }

    public void spawn(Logic logic, DoubleTriplet position) {
        AreaEffectCloud cloud = new AreaEffectCloud(this.mob.level(), position.getFirst(), position.getSecond(),position.getThird());

        cloud.setRadius(this.radius());
        cloud.setRadiusOnUse(this.radiusOnUse());
        cloud.setRadiusPerTick(this.radiusGrowth());
        cloud.setWaitTime(this.waitTime());
        cloud.setDuration(this.duration());

        logic.apply(this.mob(), cloud);

        this.mob().level().addFreshEntity(cloud);
    }

    @FunctionalInterface
    public interface Logic {
        void apply(Mob mob, AreaEffectCloud cloud);
    }
}
