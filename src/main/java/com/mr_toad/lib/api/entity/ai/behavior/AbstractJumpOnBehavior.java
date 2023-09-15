package com.mr_toad.lib.api.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
public abstract class AbstractJumpOnBehavior extends Behavior<Mob> {

    protected static final int MAX_TIME_TO_TARGET = 100;
    protected static final int MIN_JUMPS = 3;
    protected static final int COOLDOWN_BETWEEN_JUMPS = 5;
    protected final float speedModifier;
    protected int remainingTimeToReach;
    protected int remainingJumps;
    protected int remainingCooldownUntilNextJump;

    protected static MemoryModuleType<BlockPos> targetMemory;
    @Nullable protected BlockPos target;

    public AbstractJumpOnBehavior(float speedModifier) {
        super(ImmutableMap.of(getMemory(), MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));

        this.speedModifier = speedModifier;
    }

    @Override
    protected void start(ServerLevel serv, Mob mob, long l0) {
        super.start(serv, mob, l0);
        this.getNearestTarget(mob).ifPresent((t) -> {
            this.target = t;
            this.remainingTimeToReach = MAX_TIME_TO_TARGET;
            this.remainingJumps = MIN_JUMPS + serv.random.nextInt(4);
            this.remainingCooldownUntilNextJump = 0;
            this.startWalkingTowardsTarget(mob, t);
        });
    }

    @Override
    protected void stop(ServerLevel serv, Mob mob, long l) {
        super.stop(serv, mob, l);
        this.target = null;
        this.remainingTimeToReach = 0;
        this.remainingJumps = 0;
        this.remainingCooldownUntilNextJump = 0;
    }

    @Override
    protected void tick(ServerLevel serv, Mob mob, long l0) {
        if (!this.onOrOverTarget(serv, mob)) {
            --this.remainingTimeToReach;
        } else if (this.remainingCooldownUntilNextJump > 0) {
            --this.remainingCooldownUntilNextJump;
        } else {
            if (this.onTargetSurface(serv, mob)) {
                mob.getJumpControl().jump();
                --this.remainingJumps;
                this.remainingCooldownUntilNextJump = COOLDOWN_BETWEEN_JUMPS;
            }

        }
    }

    protected void startWalkingTowardsTarget(Mob mob, BlockPos pos) {
        mob.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(pos, this.speedModifier, 0));
    }

    protected boolean onTargetSurface(ServerLevel serverLevel, Mob mob) {
        return this.isTarget(serverLevel, mob.blockPosition());
    }

    protected Optional<BlockPos> getNearestTarget(Mob mob) {
        return mob.getBrain().getMemory(targetMemory);
    }

    protected boolean tiredOfWalking(ServerLevel serv, Mob mob) {
        return !this.onOrOverTarget(serv, mob) && this.remainingTimeToReach <= 0;
    }

    protected boolean tiredOfJumping(ServerLevel serv, Mob mob) {
        return this.onOrOverTarget(serv, mob) && this.remainingJumps <= 0;
    }

    protected boolean nearTarget(ServerLevel serv, Mob m) {
        return this.onOrOverTarget(serv, m) || this.getNearestTarget(m).isPresent();
    }

    protected boolean onOrOverTarget(ServerLevel serv, Mob mob) {
        BlockPos blockPos = mob.blockPosition();
        BlockPos blockPos1 = blockPos.below();
        return this.isTarget(serv, blockPos) || this.isTarget(serv, blockPos1);
    }

    public static MemoryModuleType<BlockPos> getMemory() {
        return targetMemory;
    }

    @Override
    protected abstract boolean canStillUse(ServerLevel serv, Mob mob, long l0);

    @Override
    protected boolean timedOut(long l0) {
        return false;
    }

    @Override
    protected abstract boolean checkExtraStartConditions(@NotNull ServerLevel s, Mob mob);

    protected abstract boolean isTarget(ServerLevel serv, BlockPos blockPos);

    public abstract MemoryModuleType<BlockPos> setMemory(MemoryModuleType<BlockPos> moduleType);

}
