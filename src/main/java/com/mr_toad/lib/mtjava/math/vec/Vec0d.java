package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleLists;

import javax.annotation.concurrent.Immutable;

//Vector null
@Immutable
public final class Vec0d implements DoubleVec<Vec0d> {

    public static final Vec0d INSTANCE = new Vec0d();

    private Vec0d() {}

    @Override
    public double get(int index) {
        return 0.0D;
    }

    @Override
    public double set(int index, double value) {
        return 0.0D;
    }

    @Override
    public Vec0d set(Vec0d other) {
        return this;
    }

    @Override
    public Vec0d add(Vec0d other) {
        return this;
    }

    @Override
    public Vec0d sub(Vec0d other) {
        return this;
    }

    @Override
    public Vec0d mul(Vec0d other) {
        return this;
    }

    @Override
    public Vec0d scale(double scalar) {
        return this;
    }

    @Override
    public Vec0d min(Vec0d other) {
        return this;
    }

    @Override
    public Vec0d max(Vec0d other) {
        return this;
    }

    @Override
    public Vec0d abs() {
        return this;
    }

    @Override
    public Vec0d cross(Vec0d other) {
        return this;
    }

    @Override
    public DoubleList values() {
        return DoubleLists.emptyList();
    }

    @Override
    public double length() {
        return 0.0D;
    }

    @Override
    public double lengthSqr() {
        return 0.0D;
    }

    @Override
    public double dist(Vec0d other) {
        return 0.0D;
    }

    @Override
    public double distSqr(Vec0d other) {
        return 0.0D;
    }

    @Override
    public double dot(Vec0d other) {
        return 0.0D;
    }

    @Override
    public Vec0d zero() {
        return this;
    }
}
