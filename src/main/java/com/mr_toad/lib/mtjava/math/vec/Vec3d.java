package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.geo.Axis;
import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3dc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.List;

public class Vec3d implements DoubleVec<Vec3d> {

    public static final Vec3d ZERO = new Vec3d();

    private double x;
    private double y;
    private double z;

    public Vec3d() {
        this(0.0D);
    }

    public Vec3d(List<Double> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
    }

    public Vec3d(DoubleVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
    }

    public Vec3d(double[] a) {
        this(a[0], a[1], a[2]);
    }

    public Vec3d(double v) {
        this(v, v, v);
    }

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vec3d set(double x, double y, double z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        return this;
    }

    @Override
    public Vec3d set(int index, double value) {
        switch (index) {
            case 0 -> this.setX(value);
            case 1 -> this.setY(value);
            case 2 -> this.setZ(value);
        }
        return this;
    }

    @Override
    public Vec3d set(Vec3d other) {
        this.setX(other.x());
        this.setY(other.y());
        this.setZ(other.z());
        return this;
    }

    @Override
    public Vec3d set(double value) {
        this.setX(value);
        this.setY(value);
        this.setZ(value);
        return this;
    }

    @Override
    public Vec3d add(Vec3d other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        this.setZ(this.z() + other.z());
        return this;
    }

    @Override
    public Vec3d sub(Vec3d other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        this.setZ(this.z() - other.z());
        return this;
    }

    @Override
    public Vec3d mul(Vec3d other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        this.setZ(this.z() * other.z());
        return this;
    }

    @Override
    public Vec3d div(Vec3d other) {
        this.setX(this.x() / other.x());
        this.setY(this.y() / other.y());
        this.setZ(this.z() / other.z());
        return this;
    }

    @Override
    public Vec3d scale(double scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        this.setZ(this.z() * scalar);
        return this;
    }

    @Override
    public Vec3d min(Vec3d other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        this.setZ(Math.min(this.z(), other.z()));
        return this;
    }

    @Override
    public Vec3d max(Vec3d other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        this.setZ(Math.max(this.z(), other.z()));
        return this;
    }

    @Override
    public Vec3d abs() {
        this.setX(Math.abs(this.x()));
        this.setY(Math.abs(this.y()));
        this.setZ(Math.abs(this.z()));
        return this;
    }

    @Override
    public Vec3d cross(Vec3d other) {
        return new Vec3d(this.y() * other.z() - this.z() * other.y(), this.z() * other.x() - this.x() * other.z(), this.x() * other.y() - this.y() * other.x());
    }

    @Override
    public DoubleList values() {
        return DoubleList.of(this.x(), this.y(), this.z());
    }

    @Override
    public double length() {
        return MtMath.length(this.x, this.y, this.z);
    }

    @Override
    public double lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y, this.z);
    }

    @Override
    public double dist(Vec3d other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();
        double z = this.z() - other.z();
        return MtMath.lengthSquared(x, y, z);
    }

    @Override
    public double distSqr(Vec3d other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();
        double z = this.z() - other.z();
        return MtMath.length(x, y, z);
    }

    @Override
    public double dot(Vec3d other) {
        return this.x() * other.x() + this.y() * other.y() + this.z() * other.z();
    }

    @Override
    public Vec3d zero() {
        return ZERO;
    }

    @Override
    public Vec3d create() {
        return new Vec3d();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec3d other) {
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

    public Vec3d add(Vector3dc vec) {
        return this.add(new Vec3d(vec.x(), vec.y(), vec.z()));
    }

    public Vec3d rotate(double angle, Axis axis) {
        return switch (axis) {
            case X -> this.rotateX(angle);
            case Y -> this.rotateY(angle);
            case Z -> this.rotateZ(angle);
        };
    }

    public Vec3d rotateX(double angle) {
        double sin = Math.sin(angle);
        double cos = org.joml.Math.cosFromSin(sin, angle);
        double y = this.y * cos - this.z * sin;
        double z = this.y * sin + this.z * cos;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3d rotateY(double angle) {
        double sin = Math.sin(angle);
        double cos = org.joml.Math.cosFromSin(sin, angle);
        double x =  this.x * cos + this.z * sin;
        double z = -this.x * sin + this.z * cos;
        this.x = x;
        this.z = z;
        return this;
    }

    public Vec3d rotateZ(double angle) {
        double sin = Math.sin(angle);
        double cos = org.joml.Math.cosFromSin(sin, angle);
        double x = this.x * cos - this.y * sin;
        double y = this.x * sin + this.y * cos;
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec3d mul(Matrix3dc mat) {
        double lx = x, ly = y, lz = z;
        this.x = Math.fma(mat.m00(), lx, Math.fma(mat.m10(), ly, mat.m20() * lz));
        this.y = Math.fma(mat.m01(), lx, Math.fma(mat.m11(), ly, mat.m21() * lz));
        this.z = Math.fma(mat.m02(), lx, Math.fma(mat.m12(), ly, mat.m22() * lz));
        return this;
    }

    public Vec3d mul(Quaternionf quaternionf) {
        return this.mul(this.x(), this.y(), this.z(), quaternionf);
    }

    public Vec3d mul(double x, double y, double z, Quaternionf quaternionf) {
        double xx = quaternionf.x * quaternionf.x, yy = quaternionf.y * quaternionf.y, zz = quaternionf.z * quaternionf.z, ww = quaternionf.w * quaternionf.w;
        double xy = quaternionf.x * quaternionf.y, xz = quaternionf.x * quaternionf.z, yz = quaternionf.y * quaternionf.z, xw = quaternionf.x * quaternionf.w;
        double zw = quaternionf.z * quaternionf.w, yw = quaternionf.y * quaternionf.w, k = 1 / (xx + yy + zz + ww);
        return this.set(Math.fma((xx - yy - zz + ww) * k, x, Math.fma(2 * (xy - zw) * k, y, (2 * (xz + yw) * k) * z)), Math.fma(2 * (xy + zw) * k, x, Math.fma((yy - xx - zz + ww) * k, y, (2 * (yz - xw) * k) * z)), Math.fma(2 * (xz - yw) * k, x, Math.fma(2 * (yz + xw) * k, y, ((zz - xx - yy + ww) * k) * z)));
    }

    public Vec3d orthogonal() {
        if (Math.abs(this.x()) < Math.abs(this.y())) {
            return new Vec3d(0, -this.z(), this.y()).normalize();
        } else {
            return new Vec3d(-this.z(), 0, this.x()).normalize();
        }
    }

    public Vector3d joml() {
        return new Vector3d(this.x(), this.y(), this.z());
    }

    public Vec3 minecraft() {
        return new Vec3(this.x(), this.y(), this.z());
    }

}
