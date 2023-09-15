package com.mr_toad.lib.api.entity.ai.goal;

import net.minecraft.world.entity.Mob;

public class FenceGateOpenGoal extends FenceGateInteractGoal {

    private final boolean closeGate;
    private int forgetTime;

    public FenceGateOpenGoal(Mob m, boolean b) {
        super(m);
        this.mob = m;
        this.closeGate = b;
    }

    @Override
    public boolean canContinueToUse() {
        return this.closeGate && this.forgetTime > 0 && super.canContinueToUse();
    }

    @Override
    public void start() {
        this.forgetTime = 20;
        this.setOpen(true);
    }

    @Override
    public void stop() {
        this.setOpen(false);
    }

    @Override
    public void tick() {
        --this.forgetTime;
        super.tick();
    }
}
