package com.mr_toad.lib.api.entity.ai.goal;

import com.mr_toad.lib.api.util.ActionLevelRunner;
import it.unimi.dsi.fastutil.doubles.DoublePredicate;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MobFollowAdultMobGoal extends Goal {

    protected final Mob child;

    @Nullable private Mob parent;
    @Nullable private final Class<? extends Mob> outerClassOfParent;

    private int timeToRecalcPath;
    private final double speedModifier;
    private final DoublePredicate distPredicate;

    public MobFollowAdultMobGoal(Mob child, @Nullable Class<? extends Mob> outerClassOfParent, double speedModifier, DoublePredicate distPredicate) {
        this.child = child;
        this.outerClassOfParent = outerClassOfParent;
        this.speedModifier = speedModifier;
        this.distPredicate = distPredicate;
    }

    @Override
    public boolean canUse() {
        if (!this.child.isBaby()) {
            return false;
        } else {
            Mob monster = null;
            double d0 = Double.MAX_VALUE;
            List<? extends Mob> list = this.getParentList();
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

    @SuppressWarnings("unchecked")
    public<E extends Mob> List<E> getParentList() {
        boolean isNull = outerClassOfParent == null;
        AtomicReference<List<E>> list = new AtomicReference<>(Collections.emptyList());

        ActionLevelRunner.lTry(this.child.level(), level -> {
            if (isNull) {
                list.set((List<E>) level.getEntitiesOfClass(this.child.getClass(), this.child.getBoundingBox().inflate(8.0D, 4.0D, 8.0D)));
            } else {
                list.set((List<E>) level.getEntitiesOfClass(this.outerClassOfParent, this.child.getBoundingBox().inflate(8.0D, 4.0D, 8.0D)));
            }
        });

        return list.get();
    }


}
