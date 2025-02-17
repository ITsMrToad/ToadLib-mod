package com.mr_toad.lib.mtjava.math.geo;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.Vec3f;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

import javax.annotation.CheckForNull;
import java.util.Arrays;
import java.util.Objects;

public class OBB {

    private static final Vec3f[] AABB_AXES = {new Vec3f(0.0f, 0.0f, 1.0f), new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(1.0f, 0.0f, 0.0f)};
    
    private final Vec3f center;
    private final Vec3f halfSizes;
    private final Vec3f[] axes;

    public OBB(OBB obb) {
        this(obb.center, obb.halfSizes, obb.axes);
    }
    
    public OBB(Vec3f center, Vec3f halfSizes, Vec3f[] axes) {
        if (axes.length <= 1) {
            throw new IllegalArgumentException("Axes for OBB must have exactly 2 or greater elements.");
        }

        this.center = new Vec3f(center);
        this.halfSizes = new Vec3f(halfSizes);
        this.axes = new Vec3f[3];
        for (int i = 0; i < 3; i++) {
            this.axes[i] = this.normalizeOrThrow(axes[i]);
        }
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
        ImmutableList.Builder<Vec3f> testAxes = ImmutableList.builder();

        testAxes.add(this.axes);
        testAxes.add(other.axes);

        Arrays.stream(this.axes).forEach(axisA -> Arrays.stream(other.axes).forEach(axisB -> {
            Vec3f cross = axisA.cross(axisB);
            Vec3f norm = this.normalizeIfNotZero(cross);
            if (norm != null) {
                testAxes.add(norm);
            }
        }));

        for (Vec3f axis : testAxes.build()) {
            Vec3f normAxis = this.normalizeIfNotZero(axis);
            if (normAxis == null) {
                continue;
            }
            if (!this.overlapOnAxis(other, normAxis)) {
                return false;
            }
        }
        return true;
    }

    public boolean intersects(AABB aabb) {
        ImmutableList.Builder<Vec3f> testAxes = ImmutableList.builder();

        testAxes.add(AABB_AXES);
        testAxes.add(this.axes);

        Arrays.stream(AABB_AXES).forEach(axisAABB -> Arrays.stream(this.axes).forEach(axisOBB -> {
            Vec3f cross = axisAABB.cross(axisOBB);
            Vec3f norm = this.normalizeIfNotZero(cross);
            if (norm != null) {
                testAxes.add(norm);
            }
        }));

        for (Vec3f axis : testAxes.build()) {
            Vec3f normAxis = this.normalizeIfNotZero(axis);
            if (normAxis == null) {
                continue;
            }
            if (!this.overlapOnAxis(aabb, normAxis)) {
                return false;
            }
        }
        return true;
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

        for (Vec3f vertex : this.getVertices(aabb)) {
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

    public Vec3f[] getVertices() {
        Vec3f[] vertices = new Vec3f[8];
        int i = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 2; k++) {
                for (int l = 0; l < 2; l++) {
                    Vec3f vertex = new Vec3f(this.center);
                    vertex.add(this.axes[0].scale(MtMath.sign(j) * this.halfSizes.x()));
                    vertex.add(this.axes[1].scale(MtMath.sign(k) * this.halfSizes.y()));
                    vertex.add(this.axes[2].scale(MtMath.sign(l) * this.halfSizes.z()));
                    vertices[i++] = vertex;
                }
            }
        }
        return vertices;
    }

    private Vec3f normalizeOrThrow(Vec3f vec) {
        float length = vec.length();
        if (length < MtMath.EPSILON) {
            throw new IllegalArgumentException("Axis vector is too small to normalize: " + vec);
        }
        return vec.scale(1.0f / length);
    }

    @CheckForNull
    private Vec3f normalizeIfNotZero(Vec3f axis) {
        float length = axis.length();
        if (length > MtMath.EPSILON) {
            return axis.scale(1.0f / length);
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof OBB other)) {
            return false;
        } else {
            return Objects.equals(this.center, other.center) && Objects.equals(this.halfSizes, other.halfSizes) && Arrays.equals(this.axes, other.axes);
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.center, this.halfSizes, Arrays.hashCode(this.axes));
    }
  
    @Override
    public String toString() {
        return "OBB[center=" + this.center + ", halfSizes=" + this.halfSizes + ", axes=" + Arrays.toString(this.axes) + "]";
    }
}
