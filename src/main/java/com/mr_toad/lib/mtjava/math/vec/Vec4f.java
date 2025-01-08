package com.mr_toad.lib.mtjava.math.vec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import it.unimi.dsi.fastutil.floats.FloatList;
import org.joml.Vector4f;

import java.util.List;

public class Vec4f implements FloatVec<Vec4f> {

    public static final Vec4f ZERO = new Vec4f();

    private float x;
    private float y;
    private float z;
    private float w;

    public Vec4f() {
        this(0.0F);
    }

    public Vec4f(List<Float> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
        this.w = v.get(3);
    }

    public Vec4f(FloatVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
        this.w = v.get(3);
    }

    public Vec4f(float[] a) {
        this(a[0], a[1], a[2], a[3]);
    }

    public Vec4f(float v) {
        this(v, v, v, v);
    }

    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }
    
    public float z() {
        return this.z;
    }
    
    public float w() {
        return this.w;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setW(float w) {
        this.w = w;
    }

    @CanIgnoreReturnValue
    public Vec4f set(float x, float y, float z, float w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
        return this;
    }

    @Override
    public Vec4f set(Vec4f other) {
        this.setX(other.x());
        this.setY(other.y());
        this.setZ(other.z());
        this.setW(other.w());
        return this;
    }

    @Override
    public Vec4f add(Vec4f other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        this.setZ(this.z() + other.z());
        this.setW(this.w() + other.w());
        return this;
    }

    @Override
    public Vec4f sub(Vec4f other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        this.setZ(this.z() - other.z());
        this.setW(this.w() - other.w());
        return this;
    }

    @Override
    public Vec4f mul(Vec4f other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        this.setZ(this.z() * other.z());
        this.setW(this.w() * other.w());
        return this;
    }

    @Override
    public Vec4f scale(float scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        this.setZ(this.z() * scalar);
        this.setW(this.w() * scalar);
        return this;
    }

    @Override
    public Vec4f min(Vec4f other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        this.setZ(Math.min(this.z(), other.z()));
        this.setW(Math.min(this.w(), other.w()));
        return this;
    }

    @Override
    public Vec4f max(Vec4f other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        this.setZ(Math.max(this.z(), other.z()));
        this.setW(Math.max(this.w(), other.w()));
        return this;
    }

    @Override
    public Vec4f abs() {
        this.setX(Math.abs(this.x()));
        this.setY(Math.abs(this.y()));
        this.setZ(Math.abs(this.z()));
        this.setW(Math.abs(this.w()));
        return this;
    }

    @Override
    public Vec4f cross(Vec4f other) {
        float cx = this.y() * other.z() - this.z() * other.y() - this.w() * other.w();
        float cy = this.z() * other.x() - this.x() * other.z() - this.w() * other.w();
        float cz = this.x() * other.y() - this.y() * other.x() - this.w() * other.w();
        float cw = this.w() * other.w();
        return new Vec4f(cx, cy, cz, cw);
    }

    @Override
    public FloatList values() {
        return FloatList.of(this.x(), this.y(), this.z(), this.w());
    }

    @Override
    public float length() {
        return MtMath.length(this.x, this.y, this.z, this.w);
    }

    @Override
    public float lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y, this.z, this.w);
    }

    @Override
    public float dist(Vec4f other) {
        float x = this.x() - other.x();
        float y = this.y() - other.y();
        float z = this.z() - other.z();
        float w = this.w() - other.w();
        return MtMath.lengthSquared(x, y, z, w);
    }

    @Override
    public float distSqr(Vec4f other) {
        float x = this.x() - other.x();
        float y = this.y() - other.y();
        float z = this.z() - other.z();
        float w = this.w() - other.w();
        return MtMath.length(x, y, z, w);
    }

    @Override
    public float dot(Vec4f other) {
        return this.x() * other.x() + this.y() * other.y() + this.z() * other.z() + this.w() * other.w();
    }

    @Override
    public Vec4f zero() {
        return ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec4f other) {
            return this.x() == other.x() && this.y() == other.y() && this.z() == other.z() && this.w() == other.w();
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        
        result = (int) (prime * result + this.x());
        result = (int) (prime * result + this.y());
        result = (int) (prime * result + this.z());
        result = (int) (prime * result + this.w());

        return result;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Vector4f joml() {
        return new Vector4f(this.x(), this.y(), this.z(), this.w());
    }

}
