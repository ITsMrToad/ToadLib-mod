package com.mr_toad.lib.api.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class CopyOwnerTargetGoal<M extends PathfinderMob & TraceableEntity> extends TargetGoal {

    private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private final M mob;

    public CopyOwnerTargetGoal(M mob) {
        super(mob, false);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return this.mob.getOwner() != null && this.mob.getOwner() instanceof Mob owner && owner.getTarget() != null && this.canAttack(owner.getTarget(), this.copyOwnerTargeting);
    }

    @Override
    public void start() {
        if (this.mob.getOwner() != null && this.mob.getOwner() instanceof Mob owner) {
            LivingEntity target = owner.getTarget();
            if (target != null) {
                this.mob.setTarget(target);
            }
        }
        super.start();
    }
}
