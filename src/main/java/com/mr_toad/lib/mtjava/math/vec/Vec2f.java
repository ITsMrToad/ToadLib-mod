package com.mr_toad.lib.mtjava.math.vec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.util.Mth;
import org.joml.Vector2f;

import java.util.List;

public class Vec2f implements FloatVec<Vec2f> {

    public static final Vec2f ZERO = new Vec2f();

    private float x;
    private float y;

    public Vec2f() {
        this(0.0F);
    }

    public Vec2f(List<Float> v) {
        this.x = v.get(0);
        this.y = v.get(1);
    }

    public Vec2f(FloatVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
    }

    public Vec2f(float[] a) {
        this(a[0], a[1]);
    }

    public Vec2f(float v) {
        this(v, v);
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @CanIgnoreReturnValue
    public Vec2f set(float x, float y) {
        this.setX(x);
        this.setY(y);
        return this;
    }

    @Override
    public Vec2f set(Vec2f other) {
        this.setX(other.x());
        this.setY(other.y());
        return this;
    }

    @Override
    public Vec2f add(Vec2f other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        return this;
    }

    @Override
    public Vec2f sub(Vec2f other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        return this;
    }

    @Override
    public Vec2f mul(Vec2f other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        return this;
    }

    @Override
    public Vec2f scale(float scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        return this;
    }

    @Override
    public Vec2f min(Vec2f other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        return this;
    }

    @Override
    public Vec2f max(Vec2f other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        return this;
    }

    @Override
    public Vec2f abs() {
        this.setX(Mth.abs(this.x()));
        this.setY(Mth.abs(this.y()));
        return this;
    }

    @Override
    public Vec2f cross(Vec2f other) {
        return new Vec2f(this.x() * other.y() - this.y() * other.x());
    }

    @Override
    public FloatList values() {
        return FloatList.of(this.x(), this.y());
    }

    @Override
    public float length() {
        return MtMath.length(this.x, this.y);
    }

    @Override
    public float lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y);
    }

    @Override
    public float dist(Vec2f other) {
        float x = this.x() - other.x();
        float y = this.y() - other.y();
        return MtMath.length(x, y);
    }

    @Override
    public float distSqr(Vec2f other) {
        float x = this.x() - other.x();
        float y = this.y() - other.y();
        return MtMath.lengthSquared(x, y);
    }

    @Override
    public float dot(Vec2f other) {
        return this.x() * other.x() + this.y() * other.y();
    }

    @Override
    public Vec2f zero() {
        return ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec2f other) {
            return this.x() == other.x() && this.y() == other.y();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        float prime = 31;
        float result = 1;

        result = prime * result + this.x();
        result = prime * result + this.y();

        return (int) result;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Vector2f joml() {
        return new Vector2f(this.x(), this.y());
    }

}
