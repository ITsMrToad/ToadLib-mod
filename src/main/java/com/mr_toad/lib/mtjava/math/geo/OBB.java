package com.mr_toad.lib.mtjava.math.geo;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.mtjava.math.MtMath;
import com.mr_toad.lib.mtjava.math.vec.Vec3f;
import com.mr_toad.lib.mtjava.math.vec.Vec4f;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class OBB {

    public static final Vec3f[] AABB_AXES = {new Vec3f(0.0F, 0.0F, 1.0F), new Vec3f(0.0F, 1.0F, 0.0F), new Vec3f(1.0F, 0.0F, 0.0F)};

    private final Vec3f center;
    private final Vec3f halfSizes;
    private final Vec3f[] axes;

    public OBB(OBB obb) {
        this(obb.center, obb.halfSizes, obb.axes);
    }

    public OBB(Vec3f center, Vec3f size, Quaternionf quaternionf) {
        Vec3f[] axes = AABB_AXES;
        for (int i = 0; i < axes.length; i++) {
            axes[i] = axes[i].mul(quaternionf);
        }

        this.center = center;
        this.halfSizes = size.scale(0.5F);
        this.axes = axes;
    }

    public OBB(Vec3f center, Vec3f size, Vec3f rot) {
        this(center, size, rot, new Matrix4f());
    }

    public OBB(Vec3f center, Vec3f size, Vec3f rot, Matrix4f pose) {
        float rx = rot.x() * Mth.DEG_TO_RAD;
        float ry = rot.y() * Mth.DEG_TO_RAD;
        float rz = rot.z() * Mth.DEG_TO_RAD;

        pose.rotateXYZ(rx, ry, rz);

        Vec3f[] axes = AABB_AXES;

        for (int i = 0; i < axes.length; i++) {
            axes[i] = new Vec3f(new Vec4f(axes[i]).mul(pose));
        }

        this.center = center;
        this.halfSizes = new Vec3f(size).scale(0.5F);
        this.axes = axes;
    }

    public OBB(Vec3f center, Vec3f size) {
        this(center, size, AABB_AXES);
    }

    public OBB(Vec3f center, Vec3f size, Vec3f[] axes) {
        if (axes.length <= 1) {
            throw new IllegalArgumentException("Axes for OBB must have exactly 2 or greater elements.");
        }
        this.center = new Vec3f(center);
        this.halfSizes = new Vec3f(size).scale(0.5F);
        this.axes = new Vec3f[3];
        for (int i = 0; i < 3; i++) {
            this.axes[i] = this.normalizeOrThrow(axes[i]);
        }
    }

    public OBB moveCenter(float x, float y, float z) {
        return this.moveCenter(new Vec3f(x, y, z));
    }

    public OBB moveCenter(Vec3f delta) {
        this.center.add(delta);
        return this;
    }

    public OBB deflate(float x, float y, float z) {
        return this.inflate(-x, -y, -z);
    }
    
    public OBB deflate(Vec3f vec) {
        return this.inflate(vec.inverse());
    }

    public OBB inflate(float x, float y, float z) {
        return this.inflate(new Vec3f(x, y, z));
    }

    public OBB inflate(Vec3f vec) {
        this.halfSizes.add(vec);
        if (this.halfSizes.x() < 0 || this.halfSizes.y() < 0 || this.halfSizes.z() < 0) {
            throw new IllegalArgumentException("Half sizes must remain non-negative.");
        }
        return this;
    }

    public OBB scale(float scalar) {
        if (scalar < 0) {
            throw new IllegalArgumentException("Scale factor must be non-negative.");
        }
        this.halfSizes.scale(scalar);
        return this;
    }

    public boolean intersects(OBB other) {
        ImmutableList.Builder<@NotNull Vec3f> testAxes = ImmutableList.builder();

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
        ImmutableList.Builder<@NotNull Vec3f> testAxes = ImmutableList.builder();

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

    public boolean overlapOnAxis(OBB other, Vec3f axis) {
        float thisRadius = this.getProjectedRadius(axis);
        float otherRadius = other.getProjectedRadius(axis);
        float distance = Math.abs(this.center.dot(axis) - other.center.dot(axis));
        return distance <= thisRadius + otherRadius;
    }

    public boolean overlapOnAxis(AABB aabb, Vec3f axis) {
        float aabbRadius = this.getProjectedRadius(aabb, axis);
        float obbRadius = this.getProjectedRadius(axis);
        Vec3f aabbCenter = new Vec3f((float) aabb.getCenter().x(), (float) aabb.getCenter().y(), (float) aabb.getCenter().z());
        float distance = Mth.abs(this.center.dot(axis) - aabbCenter.dot(axis));
        return distance <= aabbRadius + obbRadius;
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
        return this.halfSizes.x() * Mth.abs(axis.dot(this.axes[0])) + this.halfSizes.y() * Mth.abs(axis.dot(this.axes[1])) + this.halfSizes.z() * Mth.abs(axis.dot(this.axes[2]));
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

    @Nullable
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
