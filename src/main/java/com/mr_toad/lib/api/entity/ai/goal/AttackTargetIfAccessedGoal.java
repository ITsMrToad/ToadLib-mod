package com.mr_toad.lib.api.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class AttackTargetIfAccessedGoal<E extends LivingEntity> extends NearestAttackableTargetGoal<E> {

    public final Goal access;

    public AttackTargetIfAccessedGoal(Mob mob, Class<E>  clazz, Goal access) {
        super(mob, clazz, true);
        this.access = access;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.access.canUse();
    }
}
