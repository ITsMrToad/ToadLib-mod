package com.mr_toad.lib.mtjava.math.geo;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.mtjava.math.vec.Vec3d;
import com.mr_toad.lib.mtjava.math.vec.Vec3f;
import com.mr_toad.lib.mtjava.math.vec.Vec3i;
import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import com.mr_toad.lib.mtjava.math.vec.base.IntVec;
import com.mr_toad.lib.mtjava.util.func.TriFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import org.joml.Quaterniond;
import org.joml.Quaternionf;

import org.jetbrains.annotations.NotNull;

public enum Axis implements StringRepresentable {

    X("x", (x, y, z) -> x, (x, y, z) -> x),
    Y("y", (x, y, z) -> y, (x, y, z) -> y),
    Z("z", (x, y, z) -> z, (x, y, z) -> z);

    public static final Codec<Axis> CODEC = StringRepresentable.fromEnum(Axis::values);

    private final String name;
    private final TriFunction<Float, Float, Float, Float> choosef;
    private final TriFunction<Double, Double, Double, Double> choosed;

    Axis(String name, TriFunction<Float, Float, Float, Float> choosef, TriFunction<Double, Double, Double, Double> choosed) {
        this.name = name;
        this.choosef = choosef;
        this.choosed = choosed;
    }

    public static Axis byVanilla(Direction.Axis axis) {
        return switch (axis) {
            case X -> X;
            case Y -> Y;
            case Z -> Z;
        };
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public float get(float x, float y, float z) {
        return this.choosef.apply(x, y, z);
    }

    public double get(double x, double y, double z) {
        return this.choosed.apply(x, y, z);
    }

    public Vec3f directionf() {
        return switch (this) {
            case X -> new Vec3f(1.0F, 0.0F, 0.0F);
            case Y -> new Vec3f(0.0F, 1.0F, 0.0F);
            case Z -> new Vec3f(0.0F, 0.0F, 1.0F);
        };
    }

    public Vec3d directiond() {
        return switch (this) {
            case X -> new Vec3d(1.0D, 0.0D, 0.0D);
            case Y -> new Vec3d(0.0D, 1.0D, 0.0D);
            case Z -> new Vec3d(0.0D, 0.0D, 1.0D);
        };
    }

    public Quaternionf counterclockwiseDeg(float v) {
        return this.clockwiseDeg(-v);
    }

    public Quaterniond counterclockwiseDeg(double v) {
        return this.clockwiseDeg(-v);
    }

    public Quaternionf clockwiseDeg(float v) {
        return this.clockwise(v * Mth.DEG_TO_RAD);
    }

    public Quaterniond clockwiseDeg(double v) {
        return this.clockwise(v * Mth.DEG_TO_RAD);
    }

    public Quaternionf counterclockwise(float v) {
        return this.clockwise(-v);
    }

    public Quaterniond counterclockwise(double v) {
        return this.clockwise(-v);
    }

    public Quaternionf clockwise(float v) {
        Quaternionf quaternionf = new Quaternionf();
        return switch (this) {
            case X -> quaternionf.rotateX(v);
            case Y -> quaternionf.rotateY(v);
            case Z -> quaternionf.rotateZ(v);
        };
    }

    public Quaterniond clockwise(double v) {
        Quaterniond quaterniond = new Quaterniond();
        return switch (this) {
            case X -> quaterniond.rotateX(v);
            case Y -> quaterniond.rotateY(v);
            case Z -> quaterniond.rotateZ(v);
        };
    }

    public int get(SectionPos pos) {
        return this.getMoj3I(pos);
    }

    public int get(ChunkPos pos) {
        return this.get(pos.getWorldPosition());
    }

    public int get(BlockPos pos) {
        return this.getMoj3I(pos);
    }

    public int get(net.minecraft.core.Vec3i pos) {
        return this.getMoj3I(pos);
    }

    public double get(Position position) {
        return switch (this) {
            case X -> position.x();
            case Y -> position.y();
            case Z -> position.z();
        };
    }

    public<V extends FloatVec<V>> float get(V vec) {
        if (vec.length() == 0.0F) {
            return 0.0F;
        }

        return switch (this) {
            case X -> vec.get(0);
            case Y -> {
                if (vec.size() < 2) {
                    yield 0.0F;
                } else {
                    yield vec.get(1);
                }
            }
            case Z -> {
                if (vec.size() < 3) {
                    yield 0.0F;
                } else {
                    yield vec.get(2);
                }
            }
        };
    }

    public<V extends DoubleVec<V>> double get(V vec) {
        if (vec.length() == 0.0D) {
            return 0.0D;
        }

        return switch (this) {
            case X -> vec.get(0);
            case Y -> {
                if (vec.size() < 2) {
                    yield 0.0D;
                } else {
                    yield vec.get(1);
                }
            }
            case Z -> {
                if (vec.size() < 3) {
                    yield 0.0D;
                } else {
                    yield vec.get(2);
                }
            }
        };
    }

    public<V extends IntVec<V>> int get(V vec) {
        if (vec.length() == 0) {
            return 0;
        }

        return switch (this) {
            case X -> vec.get(0);
            case Y -> {
                if (vec.size() < 2) {
                    yield 0;
                } else {
                    yield vec.get(1);
                }
            }
            case Z -> {
                if (vec.size() < 3) {
                    yield 0;
                } else {
                    yield vec.get(2);
                }
            }
        };
    }

    public Direction.Plane getPlane() {
        return switch (this) {
            case X, Z -> Direction.Plane.HORIZONTAL;
            case Y -> Direction.Plane.VERTICAL;
        };
    }

    public Direction.Axis vanilla() {
        return switch (this) {
            case X -> Direction.Axis.X;
            case Y -> Direction.Axis.Y;
            case Z -> Direction.Axis.Z;
        };
    }

    public Vec3f set(Vec3f target, float t) {
        switch (this) {
            case X -> target.setX(t);
            case Y -> target.setY(t);
            case Z -> target.setZ(t);
        }
        return target;
    }

    public Vec3d set(Vec3d target, double t) {
        switch (this) {
            case X -> target.setX(t);
            case Y -> target.setY(t);
            case Z -> target.setZ(t);
        }
        return target;
    }

    public Vec3i set(Vec3i target, int t) {
        switch (this) {
            case X -> target.setX(t);
            case Y -> target.setY(t);
            case Z -> target.setZ(t);
        }
        return target;
    }

    private <P extends net.minecraft.core.Vec3i> int getMoj3I(P pos) {
        return switch (this) {
            case X -> pos.getX();
            case Y -> pos.getY();
            case Z -> pos.getZ();
        };
    }

    public boolean isVertical() {
        return this == Y;
    }

    public boolean isHorizontal() {
        return this == X || this == Z;
    }

}
