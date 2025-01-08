package com.mr_toad.lib.mtjava.math.vec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

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

    @CanIgnoreReturnValue
    public Vec3d set(double x, double y, double z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
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
        return Mth.length(this.x, this.y, this.z);
    }

    @Override
    public double lengthSqr() {
        return Mth.lengthSquared(this.x, this.y, this.z);
    }

    @Override
    public double dist(Vec3d other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();
        double z = this.z() - other.z();
        return Mth.lengthSquared(x, y, z);
    }

    @Override
    public double distSqr(Vec3d other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();
        double z = this.z() - other.z();
        return Mth.length(x, y, z);
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

    public Vector3d joml() {
        return new Vector3d(this.x(), this.y(), this.z());
    }

    public Vec3 minecraft() {
        return new Vec3(this.x(), this.y(), this.z());
    }

}
