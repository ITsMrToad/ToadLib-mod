package com.mr_toad.lib.api.entity.ai.goal;

import com.mr_toad.lib.api.helper.GoalHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public abstract class FenceGateInteractGoal extends Goal {

    protected Mob mob;
    protected BlockPos gatePos = BlockPos.ZERO;
  
    protected boolean hasGate;
    protected boolean passed;
  
    private float gateOpenDirX;
    private float gateOpenDirZ;

    public FenceGateInteractGoal(Mob m) {
        this.mob = m;

        if (!GoalUtils.hasGroundPathNavigation(m)) {
            throw new IllegalArgumentException("Unsupported mob type for FenceGateInteractGoal");
        }
    }

    @Override
    public boolean canUse() {
        if (!GoalUtils.hasGroundPathNavigation(this.mob)) {
            return false;

        } else if (!this.mob.horizontalCollision) {
            return false;

        } else {

            GroundPathNavigation groundpathnavigation = (GroundPathNavigation) this.mob.getNavigation();
            Path path = groundpathnavigation.getPath();

            if (path != null && !path.isDone() && groundpathnavigation.canOpenDoors()) {
                for (int i = 0; i < Math.min(path.getNextNodeIndex() + 2, path.getNodeCount()); ++i) {
                    Node node = path.getNode(i);
                    this.gatePos = new BlockPos(node.x, node.y + 1, node.z);
                    if (!(this.mob.distanceToSqr((double) this.gatePos.getX(), this.mob.getY(), (double) this.gatePos.getZ()) > 2.25D)) {
                        if (this.hasGate) {
                            return true;
                        }
                    }
                }

                this.gatePos = this.mob.blockPosition().above();
                return this.hasGate;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.passed;
    }

    @Override
    public void start() {
        this.passed = false;
        this.gateOpenDirX = (float) ((double) this.gatePos.getX() + 0.5D - this.mob.getX());
        this.gateOpenDirZ = (float) ((double) this.gatePos.getZ() + 0.5D - this.mob.getZ());
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        float f = (float) ((double) this.gatePos.getX() + 0.5D - this.mob.getX());
        float f1 = (float) ((double) this.gatePos.getZ() + 0.5D - this.mob.getZ());
        float f2 = this.gateOpenDirX * f + this.gateOpenDirZ * f1;
        if (f2 < 0.0F) {
            this.passed = true;
        }
    }


    protected boolean isOpen() {
        if (!this.hasGate) {
            return false;
        } else {
            BlockState blockstate = this.mob.level.getBlockState(this.gatePos);
            if (!(blockstate.getBlock() instanceof FenceGateBlock)) {
                this.hasGate = false;
                return false;
            } else {
                return blockstate.getValue(FenceGateBlock.OPEN);
            }
        }
    }

    protected void setOpen(boolean b) {
        if (this.hasGate) {
            BlockState blockstate = this.mob.level.getBlockState(this.gatePos);
            if (blockstate.getBlock() instanceof FenceGateBlock) {
                GoalHelper.setGateOpen(this.mob, this.mob.level, blockstate, this.gatePos, b);
            }
        }

    }

}
