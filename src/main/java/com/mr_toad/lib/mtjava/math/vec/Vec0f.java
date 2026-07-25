package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatLists;

//Vector null
public final class Vec0f implements FloatVec<Vec0f> {

    public static final Vec0f INSTANCE = new Vec0f();

    private Vec0f() {}

    @Override
    public float get(int index) {
        return 0.0F;
    }

    @Override
    public Vec0f set(int index, float value) {
        return this;
    }

    @Override
    public Vec0f set(Vec0f other) {
        return this;
    }

    @Override
    public Vec0f set(float value) {
        return this;
    }

    @Override
    public Vec0f add(Vec0f other) {
        return this;
    }

    @Override
    public Vec0f sub(Vec0f other) {
        return this;
    }

    @Override
    public Vec0f mul(Vec0f other) {
        return this;
    }

    @Override
    public Vec0f div(Vec0f other) {
        return this;
    }

    @Override
    public Vec0f scale(float scalar) {
        return this;
    }

    @Override
    public Vec0f min(Vec0f other) {
        return this;
    }

    @Override
    public Vec0f max(Vec0f other) {
        return this;
    }

    @Override
    public Vec0f abs() {
        return this;
    }

    @Override
    public Vec0f cross(Vec0f other) {
        return this;
    }

    @Override
    public FloatList values() {
        return FloatLists.emptyList();
    }

    @Override
    public float length() {
        return 0.0F;
    }

    @Override
    public float lengthSqr() {
        return 0.0F;
    }

    @Override
    public float dist(Vec0f other) {
        return 0.0F;
    }

    @Override
    public float distSqr(Vec0f other) {
        return 0.0F;
    }

    @Override
    public float dot(Vec0f other) {
        return 0.0F;
    }

    @Override
    public Vec0f zero() {
        return this;
    }

    @Override
    public Vec0f create() {
        return this;
    }
}
