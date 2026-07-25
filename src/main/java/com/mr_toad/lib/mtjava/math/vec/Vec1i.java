package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.vec.base.IntVec;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import net.minecraft.util.Mth;

public class Vec1i implements IntVec<Vec1i> {

    public static final Vec1i ZERO = new Vec1i(0);

    private int x;

    public Vec1i() {
        this(0);
    }

    public Vec1i(int x) {
        this.x = x;
    }

    public int x() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public Vec1i set(int index, int value) {
        if (index == 0) {
            this.setX(value);
        }
        return this;
    }

    @Override
    public Vec1i set(Vec1i other) {
        this.setX(other.x());
        return this;
    }

    @Override
    public Vec1i set(int value) {
        this.setX(value);
        return this;
    }

    @Override
    public Vec1i add(Vec1i other) {
        this.setX(this.x() + other.x());
        return this;
    }

    @Override
    public Vec1i sub(Vec1i other) {
        this.setX(this.x() - other.x());
        return this;
    }

    @Override
    public Vec1i mul(Vec1i other) {
        this.setX(this.x() * other.x());
        return this;
    }

    @Override
    public Vec1i div(Vec1i other) {
        this.setX(this.x() / other.x());
        return this;
    }

    @Override
    public Vec1i scale(int scalar) {
        this.setX(this.x() * scalar);
        return this;
    }

    @Override
    public Vec1i min(Vec1i other) {
        this.setX(Math.min(this.x, other.x()));
        return this;
    }

    @Override
    public Vec1i max(Vec1i other) {
        this.setX(Math.max(this.x, other.x()));
        return this;
    }

    @Override
    public Vec1i abs() {
        this.setX(Mth.abs(this.x()));
        return this;
    }

    @Override
    public Vec1i cross(Vec1i other) {
        return new Vec1i();
    }

    @Override
    public IntList values() {
        return IntLists.singleton(this.x());
    }

    @Override
    public int length() {
        return this.abs().x();
    }

    @Override
    public int lengthSqr() {
        return this.x() * this.x();
    }

    @Override
    public int dist(Vec1i other) {
        return Mth.abs(this.x() - other.x());
    }

    @Override
    public int distSqr(Vec1i other) {
        int i = this.dist(other);
        return i * i;
    }

    @Override
    public int dot(Vec1i other) {
        return this.x() * other.x();
    }

    @Override
    public Vec1i zero() {
        return ZERO;
    }

    @Override
    public Vec1i create() {
        return new Vec1i();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec1i other) {
            return this.x() == other.x();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 + this.x();
    }

    @Override
    public String toString() {
        return this.name();
    }
}
