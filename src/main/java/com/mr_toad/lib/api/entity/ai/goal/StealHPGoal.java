package com.mr_toad.h_plus.api;

import com.mr_toad.h_plus.core.config.HPConfig;
import it.unimi.dsi.fastutil.floats.FloatPredicate;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
public class StealHPGoal extends MeleeAttackGoal {

    public int cooldown = 200;

    public final boolean configValue;

    public final FloatPredicate hpPredicate;
    public final Predicate<Entity> entityPredicate;
    public final Predicate<Difficulty> difficultyPredicate;

    public final float hpRecovered;

    public StealHPGoal(PathfinderMob mob, double speed, FloatPredicate hpPredicate, Predicate<Entity> entityPredicate, Predicate<Difficulty> difficultyPredicate, float hpRecovered) {
        this(mob, speed, false, true, hpPredicate, entityPredicate, difficultyPredicate, hpRecovered);
    }

    public StealHPGoal(PathfinderMob mob, double speed, boolean followIfNotSeen, FloatPredicate hpPredicate, Predicate<Entity> entityPredicate, Predicate<Difficulty> difficultyPredicate, float hpRecovered) {
        this(mob, speed, followIfNotSeen, true, hpPredicate, entityPredicate, difficultyPredicate, hpRecovered);
    }

    public StealHPGoal(PathfinderMob mob, double speed, boolean followIfNotSeen, boolean configValue, FloatPredicate hpPredicate, Predicate<Entity> entityPredicate, Predicate<Difficulty> difficultyPredicate, float hpRecovered) {
        super(mob, speed, followIfNotSeen);
        this.configValue = configValue;
        this.hpPredicate = hpPredicate;
        this.entityPredicate = entityPredicate;
        this.difficultyPredicate = difficultyPredicate;
        this.hpRecovered = hpRecovered;
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                if (this.hpPredicate.test(this.mob.getHealth()) && this.entityPredicate.test(this.mob.getTarget()) && this.difficultyPredicate.test(this.mob.level.getDifficulty()) && this.configValue) {
                    return super.canUse();
                }
            }
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() && super.canContinueToUse();
    }

    @Override
    public void stop() {
        int c1 = HPConfig.nightmareModeIsActive.get() ? 50 : 200;
        int c2 = this.mob.level.getLevelData().isHardcore() ? 100 : 200;
        this.cooldown = c1 + c2;
        super.stop();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity entity, double d0) {
        this.mob.heal(this.hpRecovered);
        super.checkAndPerformAttack(entity, d0);
    }
}
