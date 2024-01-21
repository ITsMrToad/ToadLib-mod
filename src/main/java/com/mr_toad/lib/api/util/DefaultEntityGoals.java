package com.mr_toad.lib.api.util;

import com.mr_toad.lib.api.entity.ai.goal.BlowUpGoal;
import com.mr_toad.lib.api.entity.ai.goal.MobFollowAdultMobGoal;
import com.mr_toad.lib.api.entity.ai.goal.StealHPGoal;

import it.unimi.dsi.fastutil.doubles.DoublePredicate;
import it.unimi.dsi.fastutil.floats.FloatPredicate;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;

import java.util.function.Predicate;

public final class DefaultEntityGoals {

    public static<M extends PathfinderMob & BlowUpDataContainer> BlowUpGoal<M> simpleBlowUp(M mob, FloatPredicate hpPredicate, SoundEvent explosionSound, SpawnLingeringCloudData data) {
        return new BlowUpGoal<>(mob, 3.0F, 30, 2.0D, true, false, false, hpPredicate, explosionSound, data);
    }

    public static<M extends PathfinderMob & BlowUpDataContainer> BlowUpGoal<M> genericBlowUp(M mob, FloatPredicate predicate, SpawnLingeringCloudData data) {
        return DefaultEntityGoals.simpleBlowUp(mob, predicate, SoundEvents.GENERIC_EXPLODE, data);
    }

    public static<M extends PathfinderMob & BlowUpDataContainer> BlowUpGoal<M> genericBlowUpWithoutCloud(M mob, FloatPredicate predicate) {
        return DefaultEntityGoals.genericBlowUp(mob, predicate, null);
    }

    public static<M extends PathfinderMob & BlowUpDataContainer> BlowUpGoal<M> simpleBlowUpWithoutCloud(M mob, SoundEvent sound) {
        return DefaultEntityGoals.simpleBlowUp(mob, null, sound, null);
    }

    public static<M extends PathfinderMob & BlowUpDataContainer> BlowUpGoal<M> simpleBlowUpWithCloud(M mob, SoundEvent sound) {
        SpawnLingeringCloudData creeperLike = new SpawnLingeringCloudData(mob, 2.5F, -0.5F, 10);
        return DefaultEntityGoals.simpleBlowUp(mob, null, sound, creeperLike);
    }

    public static StealHPGoal stealHPGoalStandart(PathfinderMob mob, double speed, boolean config, Predicate<Difficulty> difficultyPredicate, Predicate<Entity> entityPredicate) {
        return new StealHPGoal(mob, speed, false, config, hp -> hp < 10.0F, entityPredicate, difficultyPredicate, 2.0F);
    }

    public static StealHPGoal stealHPGoalStandartNoConfig(PathfinderMob mob, double speed, Predicate<Difficulty> difficultyPredicate, Predicate<Entity> entityPredicate) {
        return DefaultEntityGoals.stealHPGoalStandart(mob, speed, true, difficultyPredicate, entityPredicate);
    }

    public static StealHPGoal stealHPWithHardcorePenalty(PathfinderMob mob, double speed, boolean config, Predicate<Difficulty> difficultyPredicate, Predicate<Entity> entityPredicate) {
        StealHPGoal stealHPGoal = DefaultEntityGoals.stealHPGoalStandart(mob, speed, config, difficultyPredicate, entityPredicate);
        if (mob.level.getLevelData().isHardcore()) {
            stealHPGoal.cooldown.minus(50);
        }
        return stealHPGoal;
    }

}
