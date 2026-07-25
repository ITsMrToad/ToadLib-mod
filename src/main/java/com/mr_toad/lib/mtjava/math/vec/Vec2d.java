package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.joml.Vector2d;

import java.util.List;

public class Vec2d implements DoubleVec<Vec2d> {

    public static final Vec2d ZERO = new Vec2d();

    private double x;
    private double y;

    public Vec2d() {
        this(0.0D);
    }

    public Vec2d(List<Double> v) {
        this.x = v.get(0);
        this.y = v.get(1);
    }
    
    public Vec2d(DoubleVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
    }

    public Vec2d(double[] a) {
        this(a[0], a[1]);
    }

    public Vec2d(double v) {
        this(v, v);
    }

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vec2d set(double x, double y) {
        this.setX(x);
        this.setY(y);
        return this;
    }

    @Override
    public Vec2d set(int index, double value) {
        switch (index) {
            case 0 -> this.setX(value);
            case 1 -> this.setY(value);
        }
        return this;
    }

    @Override
    public Vec2d set(Vec2d other) {
        this.setX(other.x());
        this.setY(other.y());
        return this;
    }

    @Override
    public Vec2d set(double value) {
        this.setX(value);
        this.setY(value);
        return this;
    }

    @Override
    public Vec2d add(Vec2d other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        return this;
    }

    @Override
    public Vec2d sub(Vec2d other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        return this;
    }

    @Override
    public Vec2d mul(Vec2d other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        return this;
    }

    @Override
    public Vec2d div(Vec2d other) {
        this.setX(this.x() / other.x());
        this.setY(this.y() / other.y());
        return this;
    }

    @Override
    public Vec2d scale(double scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        return this;
    }

    @Override
    public Vec2d min(Vec2d other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        return this;
    }

    @Override
    public Vec2d max(Vec2d other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        return this;
    }

    @Override
    public Vec2d abs() {
        this.setX(Math.abs(this.x()));
        this.setY(Math.abs(this.y()));
        return this;
    }

    @Override
    public Vec2d cross(Vec2d other) {
        return new Vec2d(this.x() * other.y() - this.y() * other.x());
    }

    @Override
    public DoubleList values() {
        return DoubleList.of(this.x(), this.y());
    }

    @Override
    public double length() {
        return MtMath.length(this.x, this.y);
    }

    @Override
    public double lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y);
    }

    @Override
    public double dist(Vec2d other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();
        return MtMath.length(x, y);
    }

    @Override
    public double distSqr(Vec2d other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();
        return MtMath.lengthSquared(x, y);
    }

    @Override
    public double dot(Vec2d other) {
        return this.x() * other.x() + this.y() * other.y();
    }

    @Override
    public Vec2d zero() {
        return ZERO;
    }

    @Override
    public Vec2d create() {
        return new Vec2d();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec2d other) {
            return this.x() == other.x() && this.y() == other.y();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        double prime = 31;
        double result = 1;

        result = prime * result + this.x();
        result = prime * result + this.y();

        return (int) result;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Vector2d joml() {
        return new Vector2d(this.x(), this.y());
    }

}
