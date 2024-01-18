package com.mr_toad.lib.api.entity.ai.goal;

import com.mr_toad.lib.api.util.time.IntegerCooldown;
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

    public final IntegerCooldown cooldown = new IntegerCooldown(200, "StealHPGoalCooldown");

    public final boolean configValue;

    public final FloatPredicate hpPredicate;
    public final Predicate<Entity> entityPredicate;
    public final Predicate<Difficulty> difficultyPredicate;

    public final float hpRecovered;

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
        if (this.cooldown.getCooldown() > 0) {
            this.cooldown.tickDown();
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
        this.cooldown.reset();
        super.stop();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity entity, double d0) {
        this.mob.heal(this.hpRecovered);
        super.checkAndPerformAttack(entity, d0);
    }
}
