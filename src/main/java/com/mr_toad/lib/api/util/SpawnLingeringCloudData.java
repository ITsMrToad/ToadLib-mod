package com.mr_toad.lib.api.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Mob;

import java.util.Collection;

public record SpawnLingeringCloudData(Mob mob, float radius, float radiusOnUse, int waitTime) {

    public void spawn() {
        if (!this.activeEffects().isEmpty()) {
            AreaEffectCloud cloud = new AreaEffectCloud(this.mob.level, this.mob.getX(), this.mob.getY(), this.mob.getZ());

            cloud.setRadius(this.radius());
            cloud.setRadiusOnUse(this.radiusOnUse());
            cloud.setRadiusPerTick(cloud.getRadius() / (float) cloud.getDuration());
            cloud.setWaitTime(this.waitTime());
            cloud.setDuration(cloud.getDuration() / 2);

            for (MobEffectInstance effectInstance : this.activeEffects()) {
                cloud.addEffect(new MobEffectInstance(effectInstance));
            }

            this.mob.level.addFreshEntity(cloud);
        }
    }

    private Collection<MobEffectInstance> activeEffects() {
        return this.mob().getActiveEffects();
    }

}
