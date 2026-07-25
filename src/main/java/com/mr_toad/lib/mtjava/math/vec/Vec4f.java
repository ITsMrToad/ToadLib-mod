package com.mr_toad.lib.mtjava.math.vec;

import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import it.unimi.dsi.fastutil.floats.FloatList;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
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

    public Vec4f(Vec3f src) {
        this(src, 0.0F);
    }

    public Vec4f(Vec3f src, float w) {
        this.x = src.x();
        this.y = src.y();
        this.z = src.z();
        this.w = w;
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

    
    public Vec4f set(float x, float y, float z, float w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
        return this;
    }

    @Override
    public Vec4f set(int index, float value) {
        switch (index) {
            case 0 -> this.setX(value);
            case 1 -> this.setY(value);
            case 2 -> this.setZ(value);
            case 3 -> this.setW(value);
        }
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
    public Vec4f set(float value) {
        this.setX(value);
        this.setY(value);
        this.setZ(value);
        this.setW(value);
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
    public Vec4f div(Vec4f other) {
        this.setX(this.x() / other.x());
        this.setY(this.y() / other.y());
        this.setZ(this.z() / other.z());
        this.setW(this.w() / other.w());
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
    public Vec4f create() {
        return new Vec4f();
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

    public Vec4f mul(Quaternionf quaternionf) {
        return this.mul(this.x(), this.y(), this.z(), quaternionf);
    }

    public Vec4f mul(float x, float y, float z, Quaternionf quaternionf) {
        float xx = quaternionf.x * quaternionf.x, yy = quaternionf.y * quaternionf.y, zz = quaternionf.z * quaternionf.z, ww = quaternionf.w * quaternionf.w;
        float xy = quaternionf.x * quaternionf.y, xz = quaternionf.x * quaternionf.z, yz = quaternionf.y * quaternionf.z, xw = quaternionf.x * quaternionf.w;
        float zw = quaternionf.z * quaternionf.w, yw = quaternionf.y * quaternionf.w, k = 1 / (xx + yy + zz + ww);
        this.setX(Math.fma((xx - yy - zz + ww) * k, x, Math.fma(2 * (xy - zw) * k, y, (2 * (xz + yw) * k) * z)));
        this.setY(Math.fma(2 * (xy + zw) * k, x, Math.fma((yy - xx - zz + ww) * k, y, (2 * (yz - xw) * k) * z)));
        this.setZ(Math.fma(2 * (xz - yw) * k, x, Math.fma(2 * (yz + xw) * k, y, ((zz - xx - yy + ww) * k) * z)));
        return this;
    }

    public Vec4f mul(Matrix4fc mat) {
        if ((mat.properties() & Matrix4fc.PROPERTY_AFFINE) != 0) {
            return this.mulAffine(mat);
        }
        return this.mulGeneric(mat);
    }

    public Vec4f mulTranspose(Matrix4fc mat) {
        if ((mat.properties() & Matrix4fc.PROPERTY_AFFINE) != 0) {
            return this.mulAffineTranspose(mat);
        }
        return this.mulGenericTranspose(mat);
    }

    private Vec4f mulAffine(Matrix4fc mat) {
        float x = this.x, y = this.y, z = this.z, w = this.w;
        this.x = Math.fma(mat.m00(), x, Math.fma(mat.m10(), y, Math.fma(mat.m20(), z, mat.m30() * w)));
        this.y = Math.fma(mat.m01(), x, Math.fma(mat.m11(), y, Math.fma(mat.m21(), z, mat.m31() * w)));
        this.z = Math.fma(mat.m02(), x, Math.fma(mat.m12(), y, Math.fma(mat.m22(), z, mat.m32() * w)));
        this.w = w;
        return this;
    }

    private Vec4f mulGeneric(Matrix4fc mat) {
        float x = this.x, y = this.y, z = this.z, w = this.w;
        this.x = Math.fma(mat.m00(), x, Math.fma(mat.m10(), y, Math.fma(mat.m20(), z, mat.m30() * w)));
        this.y = Math.fma(mat.m01(), x, Math.fma(mat.m11(), y, Math.fma(mat.m21(), z, mat.m31() * w)));
        this.z = Math.fma(mat.m02(), x, Math.fma(mat.m12(), y, Math.fma(mat.m22(), z, mat.m32() * w)));
        this.w = Math.fma(mat.m03(), x, Math.fma(mat.m13(), y, Math.fma(mat.m23(), z, mat.m33() * w)));
        return this;
    }

    private Vec4f mulAffineTranspose(Matrix4fc mat) {
        float x = this.x, y = this.y, z = this.z, w = this.w;
        this.x = Math.fma(mat.m00(), x, Math.fma(mat.m01(), y, mat.m02() * z));
        this.y = Math.fma(mat.m10(), x, Math.fma(mat.m11(), y, mat.m12() * z));
        this.z = Math.fma(mat.m20(), x, Math.fma(mat.m21(), y, mat.m22() * z));
        this.w = Math.fma(mat.m30(), x, Math.fma(mat.m31(), y, mat.m32() * z + w));
        return this;
    }
   
    private Vec4f mulGenericTranspose(Matrix4fc mat) {
        float x = this.x, y = this.y, z = this.z, w = this.w;
        this.x = Math.fma(mat.m00(), x, Math.fma(mat.m01(), y, Math.fma(mat.m02(), z, mat.m03() * w)));
        this.y = Math.fma(mat.m10(), x, Math.fma(mat.m11(), y, Math.fma(mat.m12(), z, mat.m13() * w)));
        this.z = Math.fma(mat.m20(), x, Math.fma(mat.m21(), y, Math.fma(mat.m22(), z, mat.m23() * w)));
        this.w = Math.fma(mat.m30(), x, Math.fma(mat.m31(), y, Math.fma(mat.m32(), z, mat.m33() * w)));
        return this;
    }
    
    public Vector4f joml() {
        return new Vector4f(this.x(), this.y(), this.z(), this.w());
    }

}
