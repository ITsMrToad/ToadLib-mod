package com.mr_toad.lib.api;

import com.mr_toad.lib.api.enums.EndWaterBehaviors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public interface IEndMob<T extends LivingEntity> {

    void getEndWaterBehavior(BlockPos pos, Level level, T entity );

    default boolean getEndWaterBehaviors(T entity, DamageSource endWaterSource) {
        switch (getEndWaterBehavior()) {
            case HURT -> waterHurt(entity, endWaterSource);
            case REGEN -> waterHeal(entity);
            case INSTANT_DEATH -> waterKill(entity, endWaterSource);
        }

        return false;
    }

    EndWaterBehaviors getEndWaterBehavior();

    default void waterAddEffect(T entity, DamageSource endWaterSource, MobEffect effect, int lvl, int ticks) {
        if (!entity.isInvulnerableTo(endWaterSource) && getEndWaterBehavior() == EndWaterBehaviors.ADD_EFFECT) entity.addEffect(new MobEffectInstance(effect, lvl, ticks));
    }

    default void waterKill(T entity, DamageSource endWaterSource) {
        if (!entity.isInvulnerableTo(endWaterSource)) entity.hurt(endWaterSource, Float.MAX_VALUE);
    }

    default void waterHurt(T entity, DamageSource endWaterSource) {
        if (!entity.isInvulnerableTo(endWaterSource)) entity.hurt(endWaterSource, 1.0F);
    }

    default void waterHeal(T entity) {
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0F);
        }
    }
}
