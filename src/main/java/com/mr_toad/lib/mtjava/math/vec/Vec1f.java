package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatLists;
import net.minecraft.util.Mth;

public class Vec1f implements FloatVec<Vec1f> {

    public static final Vec1f ZERO = new Vec1f();

    private float x;

    public Vec1f() {
        this(0);
    }

    public Vec1f(float x) {
        this.x = x;
    }

    public float x() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @Override
    public Vec1f set(Vec1f other) {
        this.setX(other.x());
        return this;
    }

    @Override
    public Vec1f add(Vec1f other) {
        this.setX(this.x() + other.x());
        return this;
    }

    @Override
    public Vec1f sub(Vec1f other) {
        this.setX(this.x() - other.x());
        return this;
    }

    @Override
    public Vec1f mul(Vec1f other) {
        this.setX(this.x() * other.x());
        return this;
    }

    @Override
    public Vec1f scale(float scalar) {
        this.setX(this.x() * scalar);
        return this;
    }

    @Override
    public Vec1f min(Vec1f other) {
        this.setX(Math.min(this.x, other.x()));
        return this;
    }

    @Override
    public Vec1f max(Vec1f other) {
        this.setX(Math.max(this.x, other.x()));
        return this;
    }

    @Override
    public Vec1f abs() {
        this.setX(Mth.abs(this.x()));
        return this;
    }

    @Override
    public Vec1f cross(Vec1f other) {
        return new Vec1f();
    }

    @Override
    public FloatList values() {
        return FloatLists.singleton(this.x());
    }

    @Override
    public float length() {
        return this.abs().x();
    }

    @Override
    public float lengthSqr() {
        return this.x() * this.x();
    }

    @Override
    public float dist(Vec1f other) {
        return Mth.abs(this.x() - other.x());
    }

    @Override
    public float distSqr(Vec1f other) {
        float i = this.dist(other);
        return i * i;
    }

    @Override
    public float dot(Vec1f other) {
        return this.x() * other.x();
    }

    @Override
    public Vec1f zero() {
        return ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec1f other) {
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
