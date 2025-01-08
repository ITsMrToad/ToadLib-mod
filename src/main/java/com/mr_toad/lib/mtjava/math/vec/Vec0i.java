package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.vec.base.IntVec;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import oshi.annotation.concurrent.Immutable;

import java.util.function.IntSupplier;

//Vector null
@Immutable
public final class Vec0i implements IntVec<Vec0i> {

    public static final Vec0i INSTANCE = new Vec0i();

    private Vec0i() {}

    @Override
    public int get(int index) {
        return 0;
    }

    @Override
    public int set(IntSupplier index, int value) {
        return 0;
    }

    @Override
    public Vec0i set(Vec0i other) {
        return this;
    }

    @Override
    public Vec0i add(Vec0i other) {
        return this;
    }

    @Override
    public Vec0i sub(Vec0i other) {
        return this;
    }

    @Override
    public Vec0i mul(Vec0i other) {
        return this;
    }

    @Override
    public Vec0i scale(int scalar) {
        return this;
    }

    @Override
    public Vec0i min(Vec0i other) {
        return this;
    }

    @Override
    public Vec0i max(Vec0i other) {
        return this;
    }

    @Override
    public Vec0i abs() {
        return this;
    }

    @Override
    public Vec0i cross(Vec0i other) {
        return this;
    }

    @Override
    public IntList values() {
        return IntLists.emptyList();
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public int lengthSqr() {
        return 0;
    }

    @Override
    public int dist(Vec0i other) {
        return 0;
    }

    @Override
    public int distSqr(Vec0i other) {
        return 0;
    }

    @Override
    public int dot(Vec0i other) {
        return 0;
    }

    @Override
    public Vec0i zero() {
        return this;
    }

}
