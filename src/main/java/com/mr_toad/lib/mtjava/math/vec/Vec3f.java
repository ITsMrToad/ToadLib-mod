package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.geo.Axis;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3fc;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.List;

public class Vec3f implements FloatVec<Vec3f> {

    public static final Vec3f ZERO = new Vec3f();
    public static final Vec3f ONE = new Vec3f(1.0F);

    private float x;
    private float y;
    private float z;

    public Vec3f() {
        this(0.0F);
    }

    public Vec3f(Vec4f vec) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
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

    public Vec3f(Vec3 vec) {
        this.x = (float) vec.x();
        this.y = (float) vec.y();
        this.z = (float) vec.z();
    }

    public Vec3f(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static Vec3f scale(Vec3f vec) {
        if (vec.x() == 0.0F) {
            vec.setX(1.0F);
        }
        if (vec.y() == 0.0F) {
            vec.setY(1.0F);
        }
        if (vec.z() == 0.0F) {
            vec.setZ(1.0F);
        }
        return vec;
    }

    public static Vec3f scale(float x, float y, float z) {
        if (x == 0.0F) {
            x = 1.0F;
        }
        if (y == 0.0F) {
            y = 1.0F;
        }
        if (z == 0.0F) {
            z = 1.0F;
        }
        return new Vec3f(x, y, z);
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

    
    public Vec3f set(float x, float y, float z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        return this;
    }

    @Override
    public Vec3f set(int index, float value) {
        switch (index) {
            case 0 -> this.setX(value);
            case 1 -> this.setY(value);
            case 2 -> this.setZ(value);
        }
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
    public Vec3f set(float value) {
        this.setX(value);
        this.setY(value);
        this.setZ(value);
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
    public Vec3f div(Vec3f other) {
        this.setX(this.x() / other.x());
        this.setY(this.y() / other.y());
        this.setZ(this.z() / other.z());
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
    public Vec3f create() {
        return new Vec3f();
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

    public Vec3f add(Vector3fc vec) {
        return this.add(new Vec3f(vec.x(), vec.y(), vec.z()));
    }

    public Vec3f rotate(float angle, Axis axis) {
        return switch (axis) {
            case X -> this.rotateX(angle);
            case Y -> this.rotateY(angle);
            case Z -> this.rotateZ(angle);
        };
    }

    public Vec3f rotateX(float angle) {
        float sin = Mth.sin(angle);
        float cos = org.joml.Math.cosFromSin(sin, angle);
        float y = this.y * cos - this.z * sin;
        float z = this.y * sin + this.z * cos;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3f rotateY(float angle) {
        float sin = Mth.sin(angle);
        float cos = org.joml.Math.cosFromSin(sin, angle);
        float x =  this.x * cos + this.z * sin;
        float z = -this.x * sin + this.z * cos;
        this.x = x;
        this.z = z;
        return this;
    }

    public Vec3f rotateZ(float angle) {
        float sin = Mth.sin(angle);
        float cos = org.joml.Math.cosFromSin(sin, angle);
        float x = this.x * cos - this.y * sin;
        float y = this.x * sin + this.y * cos;
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec3f mul(Matrix3fc mat) {
        float lx = x, ly = y, lz = z;
        this.x = Math.fma(mat.m00(), lx, Math.fma(mat.m10(), ly, mat.m20() * lz));
        this.y = Math.fma(mat.m01(), lx, Math.fma(mat.m11(), ly, mat.m21() * lz));
        this.z = Math.fma(mat.m02(), lx, Math.fma(mat.m12(), ly, mat.m22() * lz));
        return this;
    }

    public Vec3f mul(Quaternionf quaternionf) {
        return this.mul(this.x(), this.y(), this.z(), quaternionf);
    }

    public Vec3f mul(float x, float y, float z, Quaternionf quaternionf) {
        float xx = quaternionf.x * quaternionf.x, yy = quaternionf.y * quaternionf.y, zz = quaternionf.z * quaternionf.z, ww = quaternionf.w * quaternionf.w;
        float xy = quaternionf.x * quaternionf.y, xz = quaternionf.x * quaternionf.z, yz = quaternionf.y * quaternionf.z, xw = quaternionf.x * quaternionf.w;
        float zw = quaternionf.z * quaternionf.w, yw = quaternionf.y * quaternionf.w, k = 1 / (xx + yy + zz + ww);
        return this.set(Math.fma((xx - yy - zz + ww) * k, x, Math.fma(2 * (xy - zw) * k, y, (2 * (xz + yw) * k) * z)), Math.fma(2 * (xy + zw) * k, x, Math.fma((yy - xx - zz + ww) * k, y, (2 * (yz - xw) * k) * z)), Math.fma(2 * (xz - yw) * k, x, Math.fma(2 * (yz + xw) * k, y, ((zz - xx - yy + ww) * k) * z)));
    }

    public Vec3f orthogonal() {
        if (Mth.abs(this.x()) < Mth.abs(this.y())) {
            return new Vec3f(0, -this.z(), this.y()).normalize();
        } else {
            return new Vec3f(-this.z(), 0, this.x()).normalize();
        }
    }

    public Vector3f joml() {
        return new Vector3f(this.x(), this.y(), this.z());
    }

    public Vec3d minecraft() {
        return new Vec3d(this.x(), this.y(), this.z());
    }

    public BlockPos blockPos() {
        return new BlockPos((int) this.x(), (int) this.y(), (int) this.z());
    }

}
