package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleLists;
import net.minecraft.util.Mth;

public class Vec1d implements DoubleVec<Vec1d> {

    public static final Vec1d ZERO = new Vec1d();

    private double x;

    public Vec1d() {
        this(0);
    }

    public Vec1d(double x) {
        this.x = x;
    }

    public double x() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public Vec1d set(int index, double value) {
        if (index == 0) {
            this.setX(value);
        }
        return this;
    }

    @Override
    public Vec1d set(Vec1d other) {
        this.setX(other.x());
        return this;
    }

    @Override
    public Vec1d set(double value) {
        this.setX(value);
        return this;
    }

    @Override
    public Vec1d add(Vec1d other) {
        this.setX(this.x() + other.x());
        return this;
    }

    @Override
    public Vec1d sub(Vec1d other) {
        this.setX(this.x() - other.x());
        return this;
    }

    @Override
    public Vec1d mul(Vec1d other) {
        this.setX(this.x() * other.x());
        return this;
    }

    @Override
    public Vec1d div(Vec1d other) {
        this.setX(this.x() / other.x());
        return this;
    }

    @Override
    public Vec1d scale(double scalar) {
        this.setX(this.x() * scalar);
        return this;
    }

    @Override
    public Vec1d min(Vec1d other) {
        this.setX(Math.min(this.x, other.x()));
        return this;
    }

    @Override
    public Vec1d max(Vec1d other) {
        this.setX(Math.max(this.x, other.x()));
        return this;
    }

    @Override
    public Vec1d abs() {
        this.setX(Math.abs(this.x()));
        return this;
    }

    @Override
    public Vec1d cross(Vec1d other) {
        return new Vec1d();
    }

    @Override
    public DoubleList values() {
        return DoubleLists.singleton(this.x());
    }

    @Override
    public double length() {
        return this.abs().x();
    }

    @Override
    public double lengthSqr() {
        return this.x() * this.x();
    }

    @Override
    public double dist(Vec1d other) {
        return Math.abs(this.x() - other.x());
    }

    @Override
    public double distSqr(Vec1d other) {
        double i = this.dist(other);
        return i * i;
    }

    @Override
    public double dot(Vec1d other) {
        return this.x() * other.x();
    }

    @Override
    public Vec1d zero() {
        return ZERO;
    }

    @Override
    public Vec1d create() {
        return new Vec1d();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec1d other) {
            return this.x() == other.x();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 + Mth.floor(this.x());
    }

    @Override
    public String toString() {
        return this.name();
    }
}
