package com.mr_toad.lib.api.util;

import net.minecraft.world.entity.LivingEntity;

import java.util.function.DoubleUnaryOperator;

public class CapeableMobListener<E extends LivingEntity> {

    public double prevCapePosX;
    public double prevCapePosY;
    public double prevCapePosZ;

    public double capePosX;
    public double capePosY;
    public double capePosZ;

    public final E entity;

    public double tickPCP = 10.0D;
    public double cpMod = 0.25D;

    public CapeableMobListener(E entity) {
        this.entity = entity;
    }

    public void capeTick() {
        this.prevCapePosX = this.capePosX;
        this.prevCapePosY = this.capePosY;
        this.prevCapePosZ = this.capePosZ;

        double d0 = this.entity.getX() - this.capePosX;
        double d1 = this.entity.getY() - this.capePosY;
        double d2 = this.entity.getZ() - this.capePosZ;

        if (d0 > this.tickPCP) {
            this.capePosX = this.entity.getX();
        }

        if (d2 > this.tickPCP) {
            this.capePosZ = this.entity.getZ();
        }

        if (d1 > this.tickPCP) {
            this.capePosY = this.entity.getY();
        }

        if (d0 < -this.tickPCP) {
            this.capePosX = this.entity.getX();
        }

        if (d2 < -this.tickPCP) {
            this.capePosZ = this.entity.getZ();
        }

        if (d1 < -this.tickPCP) {
            this.capePosY = this.entity.getY();
        }

        this.capePosX += d0 * this.cpMod;
        this.capePosZ += d2 * this.cpMod;
        this.capePosY += d1 * this.cpMod;
    }

    public CapeableMobListener<E> mapTickPCP(DoubleUnaryOperator mapper) {
        mapper.applyAsDouble(this.tickPCP);
        return this;
    }

    public CapeableMobListener<E> mapCPMod(DoubleUnaryOperator mapper) {
        mapper.applyAsDouble(this.cpMod);
        return this;
    }

}
