package com.mr_toad.lib.mtjava.math.vec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.IntVec;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector2i;

import java.util.List;

public class Vec2i implements IntVec<Vec2i> {

    public static final Vec2i ZERO = new Vec2i();

    private int x;
    private int y;

    public Vec2i() {
        this(0);
    }

    public Vec2i(List<Integer> v) {
        this.x = v.get(0);
        this.y = v.get(1);
    }

    public Vec2i(IntVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
    }

    public Vec2i(int[] a) {
        this(a[0], a[1]);
    }

    public Vec2i(int v) {
        this(v, v);
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @CanIgnoreReturnValue
    public Vec2i set(int x, int y) {
        this.setX(x);
        this.setY(y);
        return this;
    }

    @Override
    public Vec2i set(Vec2i other) {
        this.setX(other.x());
        this.setY(other.y());
        return this;
    }

    @Override
    public Vec2i add(Vec2i other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        return this;
    }

    @Override
    public Vec2i sub(Vec2i other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        return this;
    }

    @Override
    public Vec2i mul(Vec2i other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        return this;
    }

    @Override
    public Vec2i scale(int scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        return this;
    }

    @Override
    public Vec2i min(Vec2i other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        return this;
    }

    @Override
    public Vec2i max(Vec2i other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        return this;
    }

    @Override
    public Vec2i abs() {
        this.setX(Mth.abs(this.x()));
        this.setY(Mth.abs(this.y()));
        return this;
    }

    @Override
    public Vec2i cross(Vec2i other) {
        return new Vec2i(this.x() * other.y() - this.y() * other.x());
    }

    @Override
    public IntList values() {
        return IntList.of(this.x(), this.y());
    }

    @Override
    public int length() {
        return MtMath.length(this.x, this.y);
    }

    @Override
    public int lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y);
    }

    @Override
    public int dist(Vec2i other) {
        int x = this.x() - other.x();
        int y = this.y() - other.y();
        return MtMath.length(x, y);
    }

    @Override
    public int distSqr(Vec2i other) {
        int x = this.x() - other.x();
        int y = this.y() - other.y();
        return MtMath.lengthSquared(x, y);
    }

    @Override
    public int dot(Vec2i other) {
        return this.x() * other.x() + this.y() * other.y();
    }

    @Override
    public Vec2i zero() {
        return ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec2i other) {
            return this.x() == other.x() && this.y() == other.y();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = prime * result + this.x();
        result = prime * result + this.y();

        return result;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Vector2i joml() {
        return new Vector2i(this.x(), this.y());
    }

    public Vec2 minecraft() {
        return new Vec2(this.x(), this.y());
    }

}
