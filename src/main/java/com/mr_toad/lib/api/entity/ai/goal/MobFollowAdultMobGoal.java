package com.mr_toad.lib.api.entity.ai.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.DoublePredicate;

public class MobFollowAdultMobGoal extends Goal {

    protected final Mob child;
    @Nullable private Mob parent;
    private final double speedModifier;
    private int timeToRecalcPath;
    private final DoublePredicate distPredicate;

    public MobFollowAdultMobGoal(Mob child, double speedModifier, DoublePredicate distPredicate) {
        this.child = child;
        this.speedModifier = speedModifier;
        this.distPredicate = distPredicate;
    }

    @Override
    public boolean canUse() {
        if (!this.child.isBaby()) {
            return false;
        } else {
            List<? extends Mob> list = this.child.level.getEntitiesOfClass(this.child.getClass(), this.child.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
            Mob monster = null;
            double d0 = Double.MAX_VALUE;

            for(Mob monster01 : list) {
                if (!monster01.isBaby()) {
                    double d1 = this.child.distanceToSqr(monster01);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        monster = monster01;
                    }
                }
            }

            if (monster == null) {
                return false;
            } else if (d0 < 11.0D) {
                return false;
            } else {
                this.parent = monster;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.child.isBaby() || this.parent == null) {
            return false;
        } else if (!this.parent.isAlive()) {
            return false;
        } else {
            double d0 = this.child.distanceToSqr(this.parent);
            return this.distPredicate.test(d0);
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.parent = null;
    }

    @Override
    public void tick() {
        if (--this.timeToRecalcPath <= 0 && this.parent != null) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.child.getNavigation().moveTo(this.parent, this.speedModifier);
        }
    }
}
