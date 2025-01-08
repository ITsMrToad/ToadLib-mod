package com.mr_toad.lib.api.util;

import com.mr_toad.lib.mtjava.util.tri.primitive.DoubleTriplet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Mob;

import java.util.function.Function;

public record SpawnLingeringCloudData(Mob mob, float radius, float radiusOnUse, float radiusPerTick, int waitTime, int duration) {

   public static final Logic BY_ACTIVE_EFFECTS = (m, cloud) -> {
        if (!m.getActiveEffects().isEmpty()) {
            for (MobEffectInstance effectInstance : m.getActiveEffects()) {
                cloud.addEffect(new MobEffectInstance(effectInstance));
            }
        }
    };

    public static final Function<MobEffect, Logic> ALWAYS = effect -> (m, cloud) -> cloud.addEffect(new MobEffectInstance(effect));
    public static final Function<MobEffect, Logic> IF_NOT_HAS = effect -> (m, cloud) -> {
        if (!m.hasEffect(effect)) {
            cloud.addEffect(new MobEffectInstance(effect));
        }
    };

    public static SpawnLingeringCloudData withDefaultRadiusPerTick(Mob mob, float radius, float radiusOnUse, int waitTime, int duration) {
        return new SpawnLingeringCloudData(mob, radius, radiusOnUse, radius / duration, waitTime, duration);
    }

    public void spawn(Logic logic) {
        this.spawn(logic, DoubleTriplet.ofDouble(this.mob.getX(), this.mob.getY(), this.mob.getZ()));
    }
    
    public void spawn(Logic logic, DoubleTriplet position) {
        if (!this.activeEffects().isEmpty()) {
            AreaEffectCloud cloud = new AreaEffectCloud(this.mob.level(), this.mob.getX(), this.mob.getY(), this.mob.getZ());

            cloud.setRadius(this.radius());
            cloud.setRadiusOnUse(this.radiusOnUse());
            cloud.setRadiusPerTick(this.radiusPerTick());
            cloud.setWaitTime(this.waitTime());
            cloud.setDuration(this.duration());

            logic.apply(this.mob(), cloud);
        }
    }

    @FunctionalInterface
    public interface Logic {
        void apply(Mob mob, AreaEffectCloud cloud);
    }
}
