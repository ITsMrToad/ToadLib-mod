package com.mr_toad.lib.api.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.DoubleUnaryOperator;

public class CapeableMobListener<E extends LivingEntity> {

    private double prevCapePosX;
    private double prevCapePosY;
    private double prevCapePosZ;
    private double capePosX;
    private double capePosY;
    private double capePosZ;

    private final E entity;

    private double tickPCP = 10.0D;
    private double cpMod = 0.25D;

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

    public double getDCapeX(float x) {
        return Mth.lerp(x, this.prevCapePosX, this.capePosX);
    }

    public double getDCapeY(float x) {
        return Mth.lerp(x, this.prevCapePosY, this.capePosY);
    }

    public double getDCapeZ(float x) {
        return Mth.lerp(x, this.prevCapePosZ, this.capePosZ);
    }

    public CapeableMobListener<E> mapTickPCP(DoubleUnaryOperator mapper) {
        this.tickPCP = mapper.applyAsDouble(this.tickPCP);
        return this;
    }

    public CapeableMobListener<E> mapCPMod(DoubleUnaryOperator mapper) {
        this.cpMod = mapper.applyAsDouble(this.cpMod);
        return this;
    }
}
