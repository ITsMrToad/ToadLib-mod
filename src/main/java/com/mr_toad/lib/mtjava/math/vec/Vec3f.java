package com.mr_toad.lib.mtjava.math.vec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import it.unimi.dsi.fastutil.floats.FloatList;
import org.joml.Vector3f;

import java.util.List;

public class Vec3f implements FloatVec<Vec3f> {

    public static final Vec3f ZERO = new Vec3f();

    private float x;
    private float y;
    private float z;

    public Vec3f() {
        this(0.0F);
    }

    public Vec3f(List<Float> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
    }

    public Vec3f(FloatVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
    }

    public Vec3f(float[] a) {
        this(a[0], a[1], a[2]);
    }

    public Vec3f(float v) {
        this(v, v, v);
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @CanIgnoreReturnValue
    public Vec3f set(float x, float y, float z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        return this;
    }

    @Override
    public Vec3f set(Vec3f other) {
        this.setX(other.x());
        this.setY(other.y());
        this.setZ(other.z());
        return this;
    }

    @Override
    public Vec3f add(Vec3f other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        this.setZ(this.z() + other.z());
        return this;
    }

    @Override
    public Vec3f sub(Vec3f other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        this.setZ(this.z() - other.z());
        return this;
    }

    @Override
    public Vec3f mul(Vec3f other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        this.setZ(this.z() * other.z());
        return this;
    }

    @Override
    public Vec3f scale(float scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        this.setZ(this.z() * scalar);
        return this;
    }

    @Override
    public Vec3f min(Vec3f other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        this.setZ(Math.min(this.z(), other.z()));
        return this;
    }

    @Override
    public Vec3f max(Vec3f other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        this.setZ(Math.max(this.z(), other.z()));
        return this;
    }

    @Override
    public Vec3f abs() {
        this.setX(Math.abs(this.x()));
        this.setY(Math.abs(this.y()));
        this.setZ(Math.abs(this.z()));
        return this;
    }

    @Override
    public Vec3f cross(Vec3f other) {
        return new Vec3f(this.y() * other.z() - this.z() * other.y(), this.z() * other.x() - this.x() * other.z(), this.x() * other.y() - this.y() * other.x());
    }

    @Override
    public FloatList values() {
        return FloatList.of(this.x(), this.y(), this.z());
    }

    @Override
    public float length() {
        return MtMath.length(this.x, this.y, this.z);
    }

    @Override
    public float lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y, this.z);
    }

    @Override
    public float dist(Vec3f other) {
        float x = this.x() - other.x();
        float y = this.y() - other.y();
        float z = this.z() - other.z();
        return MtMath.length(x, y, z);
    }

    @Override
    public float distSqr(Vec3f other) {
        float x = this.x() - other.x();
        float y = this.y() - other.y();
        float z = this.z() - other.z();
        return MtMath.lengthSquared(x, y, z);
    }

    @Override
    public float dot(Vec3f other) {
        return this.x() * other.x() + this.y() * other.y() + this.z() * other.z();
    }

    @Override
    public Vec3f zero() {
        return ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec3f other) {
            return this.x() == other.x() && this.y() == other.y() && this.z() == other.z();
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
      
        return result;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Vector3f joml() {
        return new Vector3f(this.x(), this.y(), this.z());
    }

}
