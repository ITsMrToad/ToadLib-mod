package com.mr_toad.lib.api.entity;

import com.mr_toad.lib.api.EndWaterBehaviors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@SuppressWarnings("deprecation")
public interface EndWaterMobInteractive<T extends LivingEntity> {

    void getEndWaterBehavior(BlockPos pos, Level world, T entity );

    EndWaterBehaviors getEndWaterBehavior();

    default boolean getEndWaterBehaviors(T entity, ServerLevel level, DamageSource endWaterSource) {
        switch (this.getEndWaterBehavior()) {
            case HURT -> this.waterHurt(entity, level, endWaterSource);
            case REGEN -> this.waterHeal(entity);
            case INSTANT_DEATH -> this.waterKill(entity, level, endWaterSource);
        }
        return false;
    }

    default void waterAddEffect(T entity, ServerLevel level, DamageSource endWaterSource, MobEffectInstance effectInstance) {
        if (!entity.isInvulnerableTo(level, endWaterSource) && this.getEndWaterBehavior() == EndWaterBehaviors.ADD_EFFECT) {
            entity.addEffect(effectInstance);
        }
    }

    default void waterKill(T entity, ServerLevel level, DamageSource endWaterSource) {
        if (!entity.isInvulnerableTo(level, endWaterSource)) {
            entity.hurt(endWaterSource, Float.MAX_VALUE);
        }
    }
    default void waterHurt(T entity, ServerLevel level, DamageSource endWaterSource) {
        if (!entity.isInvulnerableTo(level, endWaterSource)) {
            entity.hurt(endWaterSource, 1.0F);
        }
    }

    default void waterHeal(T entity) {
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0F);
        }
    }
}