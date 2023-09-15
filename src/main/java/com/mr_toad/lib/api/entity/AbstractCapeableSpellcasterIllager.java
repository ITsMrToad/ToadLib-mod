package com.mr_toad.lib.api.entity;

import com.mr_toad.lib.api.ICapeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.level.Level;


public abstract class AbstractCapeableSpellcasterIllager extends SpellcasterIllager implements ICapeableMob {

    public double prevCapePosX;
    public double prevCapePosY;
    public double prevCapePosZ;
    public double capePosX;
    public double capePosY;
    public double capePosZ;

    protected AbstractCapeableSpellcasterIllager(EntityType<? extends SpellcasterIllager> entityType, Level w) {
        super(entityType, w);
    }

    @Override
    public void tick() {
        super.tick();
        this.capeTick();
    }

    @Override
    public void capeTick() {

        this.prevCapePosX = this.capePosX;
        this.prevCapePosY = this.capePosY;
        this.prevCapePosZ = this.capePosZ;

        double d0 = this.getX() - this.capePosX;
        double d1 = this.getY() - this.capePosY;
        double d2 = this.getZ() - this.capePosZ;

        if (d0 > 10.0D) {
            this.capePosX = this.getX();
        }

        if (d2 > 10.0D) {
            this.capePosZ = this.getZ();
        }

        if (d1 > 10.0D) {
            this.capePosY = this.getY();
        }

        if (d0 < -10.0D) {
            this.capePosX = this.getX();
        }

        if (d2 < -10.0D) {
            this.capePosZ = this.getZ();
        }

        if (d1 < -10.0D) {
            this.capePosY = this.getY();
        }

        this.capePosX += d0 * 0.25D;
        this.capePosZ += d2 * 0.25D;
        this.capePosY += d1 * 0.25D;
    }


}
