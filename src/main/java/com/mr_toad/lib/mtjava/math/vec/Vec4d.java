package com.mr_toad.lib.mtjava.math.vec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.joml.Vector4d;

import java.util.List;

public class Vec4d implements DoubleVec<Vec4d> {

    public static final Vec4d ZERO = new Vec4d();

    private double x;
    private double y;
    private double z;
    private double w;

    public Vec4d() {
        this(0.0D);
    }

    public Vec4d(List<Double> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
        this.w = v.get(3);
    }

    public Vec4d(DoubleVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
        this.w = v.get(3);
    }

    public Vec4d(double[] a) {
        this(a[0], a[1], a[2], a[3]);
    }

    public Vec4d(double v) {
        this(v, v, v, v);
    }

    public Vec4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }
    
    public double z() {
        return this.z;
    }

    public double w() {
        return this.w;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setW(double w) {
        this.w = w;
    }

    @CanIgnoreReturnValue
    public Vec4d set(double x, double y, double z, double w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
        return this;
    }

    @Override
    public Vec4d set(Vec4d other) {
        this.setX(other.x());
        this.setY(other.y());
        this.setZ(other.z());
        this.setW(other.w());
        return this;
    }

    @Override
    public Vec4d add(Vec4d other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        this.setZ(this.z() + other.z());
        this.setW(this.w() + other.w());
        return this;
    }

    @Override
    public Vec4d sub(Vec4d other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        this.setZ(this.z() - other.z());
        this.setW(this.w() - other.w());
        return this;
    }

    @Override
    public Vec4d mul(Vec4d other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        this.setZ(this.z() * other.z());
        this.setW(this.w() * other.w());
        return this;
    }

    @Override
    public Vec4d scale(double scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        this.setZ(this.z() * scalar);
        this.setW(this.w() * scalar);
        return this;
    }

    @Override
    public Vec4d min(Vec4d other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        this.setZ(Math.min(this.z(), other.z()));
        this.setW(Math.min(this.w(), other.w()));
        return this;
    }

    @Override
    public Vec4d max(Vec4d other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        this.setZ(Math.max(this.z(), other.z()));
        this.setW(Math.max(this.w(), other.w()));
        return this;
    }

    @Override
    public Vec4d abs() {
        this.setX(Math.abs(this.x()));
        this.setY(Math.abs(this.y()));
        this.setZ(Math.abs(this.z()));
        this.setW(Math.abs(this.w()));
        return this;
    }

    @Override
    public Vec4d cross(Vec4d other) {
        double cx = this.y() * other.z() - this.z() * other.y() - this.w() * other.w();
        double cy = this.z() * other.x() - this.x() * other.z() - this.w() * other.w();
        double cz = this.x() * other.y() - this.y() * other.x() - this.w() * other.w();
        double cw = this.w() * other.w();
        return new Vec4d(cx, cy, cz, cw);
    }

    @Override
    public DoubleList values() {
        return DoubleList.of(this.x(), this.y(), this.z(), this.w());
    }

    @Override
    public double length() {
        return MtMath.length(this.x, this.y, this.z, this.w);
    }

    @Override
    public double lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y, this.z, this.w);
    }

    @Override
    public double dist(Vec4d other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();
        double z = this.z() - other.z();
        double w = this.w() - other.w();
        return MtMath.length(x, y, z, w);
    }

    @Override
    public double distSqr(Vec4d other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();
        double z = this.z() - other.z();
        double w = this.w() - other.w();
        return MtMath.lengthSquared(x, y, z, w);
    }

    @Override
    public double dot(Vec4d other) {
        return this.x() * other.x() + this.y() * other.y() + this.z() * other.z() + this.w() * other.w();
    }

    @Override
    public Vec4d zero() {
        return ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec4d other) {
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

    public Vector4d joml() {
        return new Vector4d(this.x(), this.y(), this.z(), this.w());
    }

}
