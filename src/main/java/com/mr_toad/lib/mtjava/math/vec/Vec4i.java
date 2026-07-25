package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.IntVec;
import com.mr_toad.lib.mtjava.util.SimpleARGB;
import it.unimi.dsi.fastutil.ints.IntList;
import org.joml.Vector4i;

import java.util.List;

public class Vec4i implements IntVec<Vec4i> {

    public static final Vec4i ZERO = new Vec4i();

    private int x;
    private int y;
    private int z;
    private int w;

    public Vec4i() {
        this(0);
    }

    public Vec4i(List<Integer> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
        this.w = v.get(3);
    }

    public Vec4i(IntVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
        this.w = v.get(3);
    }

    public Vec4i(int[] a) {
        this(a[0], a[1], a[2], a[3]);
    }

    public Vec4i(int v) {
        this(v, v, v, v);
    }

    public Vec4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }
    
    public int z() {
        return this.z;
    }
    
    public int w() {
        return this.w;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setW(int w) {
        this.w = w;
    }

    public Vec4i set(int x, int y, int z, int w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
        return this;
    }

    @Override
    public Vec4i set(int index, int value) {
        switch (index) {
            case 0 -> this.setX(value);
            case 1 -> this.setY(value);
            case 2 -> this.setZ(value);
            case 3 -> this.setW(value);
        }
        return this;
    }

    @Override
    public Vec4i set(Vec4i other) {
        this.setX(other.x());
        this.setY(other.y());
        this.setZ(other.z());
        this.setW(other.w());
        return this;
    }

    @Override
    public Vec4i set(int value) {
        this.setX(value);
        this.setY(value);
        this.setZ(value);
        this.setW(value);
        return this;
    }

    @Override
    public Vec4i add(Vec4i other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        this.setZ(this.z() + other.z());
        this.setW(this.w() + other.w());
        return this;
    }

    @Override
    public Vec4i sub(Vec4i other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        this.setZ(this.z() - other.z());
        this.setW(this.w() - other.w());
        return this;
    }

    @Override
    public Vec4i mul(Vec4i other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        this.setZ(this.z() * other.z());
        this.setW(this.w() * other.w());
        return this;
    }

    @Override
    public Vec4i div(Vec4i other) {
        this.setX(this.x() / other.x());
        this.setY(this.y() / other.y());
        this.setZ(this.z() / other.z());
        this.setW(this.w() / other.w());
        return this;
    }

    @Override
    public Vec4i scale(int scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        this.setZ(this.z() * scalar);
        this.setW(this.w() * scalar);
        return this;
    }

    @Override
    public Vec4i min(Vec4i other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        this.setZ(Math.min(this.z(), other.z()));
        this.setW(Math.min(this.w(), other.w()));
        return this;
    }

    @Override
    public Vec4i max(Vec4i other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        this.setZ(Math.max(this.z(), other.z()));
        this.setW(Math.max(this.w(), other.w()));
        return this;
    }

    @Override
    public Vec4i abs() {
        this.setX(Math.abs(this.x()));
        this.setY(Math.abs(this.y()));
        this.setZ(Math.abs(this.z()));
        this.setW(Math.abs(this.w()));
        return this;
    }

    @Override
    public Vec4i cross(Vec4i other) {
        int cx = this.y() * other.z() - this.z() * other.y() - this.w() * other.w();
        int cy = this.z() * other.x() - this.x() * other.z() - this.w() * other.w();
        int cz = this.x() * other.y() - this.y() * other.x() - this.w() * other.w();
        int cw = this.w() * other.w();
        return new Vec4i(cx, cy, cz, cw);
    }

    @Override
    public IntList values() {
        return IntList.of(this.x(), this.y(), this.z(), this.w());
    }

    @Override
    public int length() {
        return MtMath.length(this.x, this.y, this.z, this.w);
    }

    @Override
    public int lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y, this.z, this.w);
    }

    @Override
    public int dist(Vec4i other) {
        int x = this.x() - other.x();
        int y = this.y() - other.y();
        int z = this.z() - other.z();
        int w = this.w() - other.w();
        return MtMath.length(x, y, z, w);
    }

    @Override
    public int distSqr(Vec4i other) {
        int x = this.x() - other.x();
        int y = this.y() - other.y();
        int z = this.z() - other.z();
        int w = this.w() - other.w();
        return MtMath.lengthSquared(x, y, z, w);
    }

    @Override
    public int dot(Vec4i other) {
        return this.x() * other.x() + this.y() * other.y() + this.z() * other.z() + this.w() * other.w();
    }

    @Override
    public Vec4i zero() {
        return ZERO;
    }

    @Override
    public Vec4i create() {
        return new Vec4i();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec4i other) {
            return this.x() == other.x() && this.y() == other.y() && this.z() == other.z() && this.w() == other.w();
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
        result = prime * result + this.z();
        result = prime * result + this.w();

        return result;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Vector4i joml() {
        return new Vector4i(this.x(), this.y(), this.z(), this.w());
    }

    public SimpleARGB color() {
        return new SimpleARGB(this.x(), this.y(), this.z(), this.w());
    }
}
