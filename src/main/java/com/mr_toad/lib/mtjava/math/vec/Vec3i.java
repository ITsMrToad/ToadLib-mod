package com.mr_toad.lib.mtjava.math.vec;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.base.IntVec;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import org.joml.Vector3i;

import java.util.List;

public class Vec3i implements IntVec<Vec3i> {

    public static final Vec3i ZERO = new Vec3i();

    private int x;
    private int y;
    private int z;

    public Vec3i() {
        this(0);
    }

    public Vec3i(List<Integer> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
    }

    public Vec3i(IntVec<?> v) {
        this.x = v.get(0);
        this.y = v.get(1);
        this.z = v.get(2);
    }

    public Vec3i(BlockPos blockPos) {
        this(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public Vec3i(int[] a) {
        this(a[0], a[1], a[2]);
    }

    public Vec3i(int v) {
        this(v, v, v);
    }

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @CanIgnoreReturnValue
    public Vec3i set(int x, int y, int z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        return this;
    }

    @Override
    public Vec3i set(Vec3i other) {
        this.setX(other.x());
        this.setY(other.y());
        this.setZ(other.z());
        return this;
    }

    @Override
    public Vec3i add(Vec3i other) {
        this.setX(this.x() + other.x());
        this.setY(this.y() + other.y());
        this.setZ(this.z() + other.z());
        return this;
    }

    @Override
    public Vec3i sub(Vec3i other) {
        this.setX(this.x() - other.x());
        this.setY(this.y() - other.y());
        this.setZ(this.z() - other.z());
        return this;
    }

    @Override
    public Vec3i mul(Vec3i other) {
        this.setX(this.x() * other.x());
        this.setY(this.y() * other.y());
        this.setZ(this.z() * other.z());
        return this;
    }

    @Override
    public Vec3i scale(int scalar) {
        this.setX(this.x() * scalar);
        this.setY(this.y() * scalar);
        this.setZ(this.z() * scalar);
        return this;
    }

    @Override
    public Vec3i min(Vec3i other) {
        this.setX(Math.min(this.x(), other.x()));
        this.setY(Math.min(this.y(), other.y()));
        this.setZ(Math.min(this.z(), other.z()));
        return this;
    }

    @Override
    public Vec3i max(Vec3i other) {
        this.setX(Math.max(this.x(), other.x()));
        this.setY(Math.max(this.y(), other.y()));
        this.setZ(Math.max(this.z(), other.z()));
        return this;
    }

    @Override
    public Vec3i abs() {
        this.setX(Mth.abs(this.x()));
        this.setY(Mth.abs(this.y()));
        this.setZ(Mth.abs(this.z()));
        return this;
    }

    @Override
    public Vec3i cross(Vec3i other) {
        return new Vec3i(this.y() * other.z() - this.z() * other.y(), this.z() * other.x() - this.x() * other.z(), this.x() * other.y() - this.y() * other.x());
    }

    @Override
    public IntList values() {
        return IntList.of(this.x(), this.y(), this.z());
    }

    @Override
    public int length() {
        return MtMath.length(this.x, this.y, this.z);
    }

    @Override
    public int lengthSqr() {
        return MtMath.lengthSquared(this.x, this.y, this.z);
    }

    @Override
    public int dist(Vec3i other) {
        int x = this.x() - other.x();
        int y = this.y() - other.y();
        int z = this.z() - other.z();
        return MtMath.length(x, y, z);
    }

    @Override
    public int distSqr(Vec3i other) {
        int x = this.x() - other.x();
        int y = this.y() - other.y();
        int z = this.z() - other.z();
        return MtMath.lengthSquared(x, y, z);
    }

    @Override
    public int dot(Vec3i other) {
        return this.x() * other.x() + this.y() * other.y() + this.z() * other.z();
    }

    @Override
    public Vec3i zero() {
        return ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Vec3i other) {
            return this.x() == other.x() && this.y() == other.y() && this.z() == other.z();
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

        return result;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Vector3i joml() {
        return new Vector3i(this.x(), this.y(), this.z());
    }

    public net.minecraft.core.Vec3i minecraft() {
        return new net.minecraft.core.Vec3i(this.x(), this.y(), this.z());
    }

    public BlockPos blockPos() {
        return new BlockPos(this.minecraft());
    }
}
