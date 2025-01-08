package com.mr_toad.lib.mtjava.math.geo;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.vec.Vec3f;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Beta //I'm not sure if intersects methods will work properly.
public class OBB {

    private final Vec3f center;
    private final Vec3f halfSizes;
    private final Vec3f[] axes;

    public OBB(Vec3f center, Vec3f halfSizes, Vec3f[] axes) {
        this.center = center;
        this.halfSizes = halfSizes;
        this.axes = axes;
    }

    @CanIgnoreReturnValue
    public OBB moveCenter(float x, float y, float z) {
        return this.moveCenter(new Vec3f(x, y, z));
    }

    @CanIgnoreReturnValue
    public OBB moveCenter(Vec3f center) {
        this.center.add(center);
        return this;
    }

    @CanIgnoreReturnValue
    public OBB deflate(float x, float y, float z) {
        return this.inflate(-x, -y, -z);
    }

    @CanIgnoreReturnValue
    public OBB deflate(Vec3f vec) {
        return this.inflate(vec.inverse());
    }

    @CanIgnoreReturnValue
    public OBB inflate(float x, float y, float z) {
        return this.inflate(new Vec3f(x, y, z));
    }

    @CanIgnoreReturnValue
    public OBB inflate(Vec3f vec) {
        this.halfSizes.add(vec);
        return this;
    }

    @CanIgnoreReturnValue
    public OBB scale(float scalar) {
        this.halfSizes.scale(scalar);
        return this;
    }

    public boolean intersects(OBB other) {
        return Stream.concat(Arrays.stream(this.axes), Arrays.stream(other.axes)).allMatch(axis -> this.overlapOnAxis(other, axis)) && Arrays.stream(this.axes).flatMap(axisThis -> Arrays.stream(other.axes).map(axisThis::cross)).allMatch(axis -> this.overlapOnAxis(other, axis));
    }

    public boolean intersects(AABB aabb) {
        Vec3f[] aabbAxes = {new Vec3f(1.0f, 0.0f, 0.0f), new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(0.0f, 0.0f, 1.0f)};
        return Stream.concat(Arrays.stream(aabbAxes), Arrays.stream(this.axes)).allMatch(axis -> this.overlapOnAxis(aabb, axis)) && Arrays.stream(aabbAxes).flatMap(axisAABB -> Arrays.stream(this.axes).map(axisAABB::cross)).allMatch(axis -> this.overlapOnAxis(aabb, axis));
    }

    public boolean overlapOnAxis(AABB aabb, Vec3f axis) {
        float aabbRadius = this.getProjectedRadius(aabb, axis);
        float obbRadius = this.getProjectedRadius(axis);
        float distance = Mth.abs(this.center.dot(axis) - new Vec3f((float) aabb.getCenter().x(), (float) aabb.getCenter().y(), (float) aabb.getCenter().z()).dot(axis));
        return distance <= aabbRadius + obbRadius;
    }

    public boolean overlapOnAxis(OBB other, Vec3f axis) {
        float thisRadius = this.getProjectedRadius(axis);
        float otherRadius = other.getProjectedRadius(axis);
        float distance = Mth.abs(this.center.dot(axis) - other.center.dot(axis));
        return distance <= thisRadius + otherRadius;
    }

    public float getProjectedRadius(AABB aabb, Vec3f axis) {
        float maxDot = Float.NEGATIVE_INFINITY;
        float minDot = Float.POSITIVE_INFINITY;

        for (Vec3f vertex : getVertices(aabb)) {
            float dot = vertex.dot(axis);
            maxDot = Math.max(maxDot, dot);
            minDot = Math.min(minDot, dot);
        }

        return (maxDot - minDot) / 2.0F;
    }

    public float getProjectedRadius(Vec3f axis) {
        return this.halfSizes.x() * Math.abs(axis.dot(this.axes[0])) + this.halfSizes.y() * Mth.abs(axis.dot(this.axes[1])) + this.halfSizes.z() * Mth.abs(axis.dot(this.axes[2]));
    }

    public static Vec3f[] getVertices(AABB aabb) {
        float minX = (float) aabb.minX;
        float minY = (float) aabb.minY;
        float minZ = (float) aabb.minZ;
        float maxX = (float) aabb.maxX;
        float maxY = (float) aabb.maxY;
        float maxZ = (float) aabb.maxZ;
        return new Vec3f[]{
                new Vec3f(minX, minY, minZ),
                new Vec3f(minX, minY, maxZ),
                new Vec3f(minX, maxY, minZ),
                new Vec3f(minX, maxY, maxZ),
                new Vec3f(maxX, minY, minZ),
                new Vec3f(maxX, minY, maxZ),
                new Vec3f(maxX, maxY, minZ),
                new Vec3f(maxX, maxY, maxZ)
        };
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.center, this.halfSizes, Arrays.hashCode(this.axes));
    }
}
