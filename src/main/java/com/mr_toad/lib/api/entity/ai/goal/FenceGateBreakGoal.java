package com.mr_toad.lib.api.entity.ai.goal;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeHooks;

import java.util.function.Predicate;

public class FenceGateBreakGoal extends FenceGateInteractGoal {

   rivate final Predicate<Difficulty> validDifficulties;
    protected int breakTime;
    protected int lastBreakProgress = -1;
    protected int gateBreakTime = -1;

    public FenceGateBreakGoal(Mob m, Predicate<Difficulty> d) {
        super(m);
        this.validDifficulties = d;
    }

    public FenceGateBreakGoal(Mob m, int t, Predicate<Difficulty> d) {
        this(m, d);
        this.gateBreakTime = t;
    }

    protected int getGateBreakTime() {
        return Math.max(240, this.gateBreakTime);
    }

    @Override
    public boolean canUse() {
        if (!super.canUse()) {
            return false;
        } else if (!ForgeHooks.canEntityDestroy(this.mob.level(), this.gatePos, this.mob)) {
            return false;
        } else {
            return this.isValidDifficulty(this.mob.level().getDifficulty()) && !this.isOpen();
        }
    }

    @Override
    public void start() {
        super.start();
        this.breakTime = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.breakTime <= this.getGateBreakTime() && !this.isOpen() && this.gatePos.closerToCenterThan(this.mob.position(), 2.0D) && this.isValidDifficulty(this.mob.level().getDifficulty());
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.level().destroyBlockProgress(this.mob.getId(), this.gatePos, -1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.mob.getRandom().nextInt(20) == 0) {
            this.mob.level().levelEvent(1019, this.gatePos, 0);
            if (!this.mob.swinging) {
                this.mob.swing(this.mob.getUsedItemHand());
            }
        }

        ++this.breakTime;
        int i = (int)((float)this.breakTime / (float)this.getGateBreakTime() * 10.0F);
        if (i != this.lastBreakProgress) {
            this.mob.level().destroyBlockProgress(this.mob.getId(), this.gatePos, i);
            this.lastBreakProgress = i;
        }

        if (this.breakTime == this.getGateBreakTime() && this.isValidDifficulty(this.mob.level().getDifficulty())) {
            this.mob.level().removeBlock(this.gatePos, false);
            this.mob.level().levelEvent(1021, this.gatePos, 0);
            this.mob.level().levelEvent(2001, this.gatePos, Block.getId(this.mob.level().getBlockState(this.gatePos)));
        }

    }


    private boolean isValidDifficulty(Difficulty d) {
        return this.validDifficulties.test(d);
    }


}
