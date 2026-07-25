package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.joml.Matrix4dc;
import org.joml.Quaterniond;
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

    
    public Vec4d set(double x, double y, double z, double w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
        return this;
    }

    @Override
    public Vec4d set(int index, double value) {
        switch (index) {
            case 0 -> this.setX(value);
            case 1 -> this.setY(value);
            case 2 -> this.setZ(value);
            case 3 -> this.setW(value);
        }
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
    public Vec4d set(double value) {
        this.setX(value);
        this.setY(value);
        this.setZ(value);
        this.setW(value);
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
    public Vec4d div(Vec4d other) {
        this.setX(this.x() / other.x());
        this.setY(this.y() / other.y());
        this.setZ(this.z() / other.z());
        this.setW(this.w() / other.w());
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
    public Vec4d create() {
        return new Vec4d();
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

    public Vec4d mul(Quaterniond quaterniond) {
        return this.mul(this.x(), this.y(), this.z(), quaterniond);
    }

    public Vec4d mul(double x, double y, double z, Quaterniond quaterniond) {
        double xx = quaterniond.x * quaterniond.x, yy = quaterniond.y * quaterniond.y, zz = quaterniond.z * quaterniond.z, ww = quaterniond.w * quaterniond.w;
        double xy = quaterniond.x * quaterniond.y, xz = quaterniond.x * quaterniond.z, yz = quaterniond.y * quaterniond.z, xw = quaterniond.x * quaterniond.w;
        double zw = quaterniond.z * quaterniond.w, yw = quaterniond.y * quaterniond.w, k = 1 / (xx + yy + zz + ww);
        this.setX(Math.fma((xx - yy - zz + ww) * k, x, Math.fma(2 * (xy - zw) * k, y, (2 * (xz + yw) * k) * z)));
        this.setY(Math.fma(2 * (xy + zw) * k, x, Math.fma((yy - xx - zz + ww) * k, y, (2 * (yz - xw) * k) * z)));
        this.setZ(Math.fma(2 * (xz - yw) * k, x, Math.fma(2 * (yz + xw) * k, y, ((zz - xx - yy + ww) * k) * z)));
        return this;
    }

    public Vec4d mul(Matrix4dc mat) {
        if ((mat.properties() & Matrix4dc.PROPERTY_AFFINE) != 0) {
            return this.mulAffine(mat);
        }
        return this.mulGeneric(mat);
    }

    public Vec4d mulTranspose(Matrix4dc mat) {
        if ((mat.properties() & Matrix4dc.PROPERTY_AFFINE) != 0) {
            return this.mulAffineTranspose(mat);
        }
        return this.mulGenericTranspose(mat);
    }

    private Vec4d mulAffine(Matrix4dc mat) {
        double x = this.x, y = this.y, z = this.z, w = this.w;
        this.x = Math.fma(mat.m00(), x, Math.fma(mat.m10(), y, Math.fma(mat.m20(), z, mat.m30() * w)));
        this.y = Math.fma(mat.m01(), x, Math.fma(mat.m11(), y, Math.fma(mat.m21(), z, mat.m31() * w)));
        this.z = Math.fma(mat.m02(), x, Math.fma(mat.m12(), y, Math.fma(mat.m22(), z, mat.m32() * w)));
        this.w = w;
        return this;
    }

    private Vec4d mulGeneric(Matrix4dc mat) {
        double x = this.x, y = this.y, z = this.z, w = this.w;
        this.x = Math.fma(mat.m00(), x, Math.fma(mat.m10(), y, Math.fma(mat.m20(), z, mat.m30() * w)));
        this.y = Math.fma(mat.m01(), x, Math.fma(mat.m11(), y, Math.fma(mat.m21(), z, mat.m31() * w)));
        this.z = Math.fma(mat.m02(), x, Math.fma(mat.m12(), y, Math.fma(mat.m22(), z, mat.m32() * w)));
        this.w = Math.fma(mat.m03(), x, Math.fma(mat.m13(), y, Math.fma(mat.m23(), z, mat.m33() * w)));
        return this;
    }

    private Vec4d mulAffineTranspose(Matrix4dc mat) {
        double x = this.x, y = this.y, z = this.z, w = this.w;
        this.x = Math.fma(mat.m00(), x, Math.fma(mat.m01(), y, mat.m02() * z));
        this.y = Math.fma(mat.m10(), x, Math.fma(mat.m11(), y, mat.m12() * z));
        this.z = Math.fma(mat.m20(), x, Math.fma(mat.m21(), y, mat.m22() * z));
        this.w = Math.fma(mat.m30(), x, Math.fma(mat.m31(), y, mat.m32() * z + w));
        return this;
    }

    private Vec4d mulGenericTranspose(Matrix4dc mat) {
        double x = this.x, y = this.y, z = this.z, w = this.w;
        this.x = Math.fma(mat.m00(), x, Math.fma(mat.m01(), y, Math.fma(mat.m02(), z, mat.m03() * w)));
        this.y = Math.fma(mat.m10(), x, Math.fma(mat.m11(), y, Math.fma(mat.m12(), z, mat.m13() * w)));
        this.z = Math.fma(mat.m20(), x, Math.fma(mat.m21(), y, Math.fma(mat.m22(), z, mat.m23() * w)));
        this.w = Math.fma(mat.m30(), x, Math.fma(mat.m31(), y, Math.fma(mat.m32(), z, mat.m33() * w)));
        return this;
    }

    public Vector4d joml() {
        return new Vector4d(this.x(), this.y(), this.z(), this.w());
    }

}
