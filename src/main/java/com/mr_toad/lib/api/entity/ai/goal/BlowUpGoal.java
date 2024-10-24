package com.mr_toad.lib.api.entity.ai.goal;

import com.mr_toad.lib.api.entity.entitydata.BlowUpDataContainer;
import com.mr_toad.lib.api.util.ActionLevelRunner;
import com.mr_toad.lib.api.util.SpawnLingeringCloudData;
import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.floats.FloatPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.EnumSet;

public class BlowUpGoal<M extends PathfinderMob & BlowUpDataContainer> extends Goal {

    private int oldSwell;
    private int swell;
    private int maxSwell;

    private final M mob;
    private float explosionRadius;
    private final double distToSqr;

    private final boolean shouldStopOnExplosion;
    private final boolean shouldExplodeIfNotSee;
    private final boolean invulnerableIfIgnited;

    @Nullable private final FloatPredicate hpPredicate;
    @Nullable private final SoundEvent explosionSound;
    @Nullable private final SpawnLingeringCloudData lingeringCloudData;

    @Nullable private LivingEntity target;

    public BlowUpGoal(M mob, float explosionRadius, int maxSwell, double distToSqr, boolean shouldStopOnExplosion, boolean shouldExplodeIfNotSee, boolean invulnerableIfIgnited, @Nullable FloatPredicate hpPredicate, @Nullable SoundEvent explosionSound, @Nullable SpawnLingeringCloudData lingeringCloudData) {
        this.mob = mob;
        this.explosionRadius = explosionRadius;
        this.maxSwell = maxSwell;
        this.distToSqr = distToSqr;
        this.shouldStopOnExplosion = shouldStopOnExplosion;
        this.shouldExplodeIfNotSee = shouldExplodeIfNotSee;
        this.invulnerableIfIgnited = invulnerableIfIgnited;
        this.hpPredicate = hpPredicate;
        this.explosionSound = explosionSound;
        this.lingeringCloudData = lingeringCloudData;

        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.shouldExplodeIfNotSee) {
            return this.canExplode() && !this.mob.getNavigation().isDone();
        }

        if (this.hpPredicate != null) {
            return this.canExplode() && this.hpPredicate.test(this.mob.getMaxHealth());
        }

        return this.canExplode();
    }

    @Override
    public void start() {
        super.start();
        if (this.shouldStopOnExplosion) {
            this.mob.getNavigation().stop();
        }
        this.target = this.mob.getTarget();
        this.mob.setAggressive(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.target = null;
        this.mob.setAggressive(false);
    }

    @Override
    public void tick() {
        if (this.target == null) {
            this.mob.setSwellDir(-1);
        } else if (this.mob.distanceToSqr(this.target) > 49.0) {
            this.mob.setSwellDir(-1);
        } else if (!this.shouldExplodeIfNotSee && !this.mob.getSensing().hasLineOfSight(this.target)) {
            this.mob.setSwellDir(-1);
        } else {
            this.mob.setSwellDir(1);
        }

        if (this.mob.isAlive()) {
            this.oldSwell = this.swell;
            if (this.mob.isIgnited()) {
                this.mob.setSwellDir(1);
            }

            if (this.invulnerableIfIgnited) {
                this.mob.isInvulnerableTo(this.mob.damageSources().mobAttack(this.target));
            }

            int $$0 = this.mob.getSwellDir();
            if ($$0 > 0 && this.swell == 0) {
                if (this.explosionSound != null) {
                    this.mob.playSound(this.explosionSound, 1.0F, 0.5F);
                }
                this.mob.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += $$0;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explode();
            }
        }

        super.tick();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public boolean canExplode() {
        return this.mob.getSwellDir() > 0 || this.mob.getTarget() != null && this.mob.distanceToSqr(this.mob.getTarget()) < this.distToSqr;
    }

    public float getSwelling(float sw) {
        return Mth.lerp(sw, (float) this.oldSwell, (float) this.swell) / (float) (this.maxSwell - 2);
    }

    public void saveBlowUpData(CompoundTag nbt) {
        nbt.putShort("Fuse", (short) this.maxSwell);
        nbt.putFloat("ExplosionRadius", this.explosionRadius);
        nbt.putBoolean("Ignited", this.mob.isIgnited());
    }

    public void loadBlowUpData(CompoundTag nbt) {
        if (nbt.contains("Fuse", 99)) this.maxSwell = nbt.getShort("Fuse");
        if (nbt.contains("ExplosionRadius", 99)) this.explosionRadius = nbt.getFloat("ExplosionRadius");
        if (nbt.getBoolean("Ignited")) this.mob.ignite();
    }

    private void explode() {
        ActionLevelRunner.serverTry((ServerLevel) this.mob.level(), level -> {
            if (!level.isClientSide()) {
                level.explode(this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.explosionRadius, Level.ExplosionInteraction.MOB);
                this.mob.discard();
                if (this.lingeringCloudData != null) {
                    this.lingeringCloudData.spawn();
                }
            }
        });
    }
}