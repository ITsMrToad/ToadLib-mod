package com.mr_toad.lib.mtjava.util;

import com.mr_toad.lib.mtjava.MtJava;
import com.mr_toad.lib.mtjava.math.vec.Vec3f;
import com.mr_toad.lib.mtjava.math.vec.Vec3i;
import com.mr_toad.lib.mtjava.math.vec.Vec4f;
import com.mr_toad.lib.mtjava.math.vec.Vec4i;
import net.minecraft.util.Mth;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.function.IntSupplier;

public class SimpleARGB implements IntSupplier, Comparable<SimpleARGB> {

    public static final SimpleARGB WHITE = new SimpleARGB(1.0F);
    public static final SimpleARGB BLACK = new SimpleARGB(0.0F);

    private int a;
    private int r;
    private int g;
    private int b;

    public SimpleARGB(int color) {
        this.a = (color >> 24) & 0xFF;
        this.r = (color >> 16) & 0xFF;
        this.g = (color >> 8) & 0xFF;
        this.b = color & 0xFF;
    }

    public SimpleARGB(Vec3f vec) {
        this(vec.x(), vec.y(), vec.z());
    }

    public SimpleARGB(Vec3i vec) {
        this(vec.x(), vec.y(), vec.z());
    }

    public SimpleARGB(Vec4f vec) {
        this(vec.x(), vec.y(), vec.z(), vec.w());
    }

    public SimpleARGB(Vec4i vec) {
        this(vec.x(), vec.y(), vec.z(), vec.w());
    }

    public SimpleARGB(SimpleARGB argb) {
        this.a = argb.alpha();
        this.r = argb.red();
        this.g = argb.green();
        this.b = argb.blue();
    }

    public SimpleARGB(float v) {
        this(v, v, v, v);
    }

    public SimpleARGB(float r, float g, float b) {
        this(1.0F, r, g, b);
        MtJava.validatePercents(r, g, b);
    }

    public SimpleARGB(int r, int g, int b) {
        this(255, r, g, b);
    }

    public SimpleARGB(float a, float r, float g, float b) {
        MtJava.validatePercents(a, r, g, b);
        this.a = Mth.floor(Mth.clamp((a * 255), 0, 255));
        this.r = Mth.floor(Mth.clamp((r * 255), 0, 255));
        this.g = Mth.floor(Mth.clamp((g * 255), 0, 255));
        this.b = Mth.floor(Mth.clamp((b * 255), 0, 255));
    }

    public SimpleARGB(int a, int r, int g, int b) {
        this.a = Mth.clamp(a, 0, 255);
        this.r = Mth.clamp(r, 0, 255);
        this.g = Mth.clamp(g, 0, 255);
        this.b = Mth.clamp(b, 0, 255);
    }

    @Override
    public int getAsInt() {
        return this.a << 24 | this.r << 16 | this.g << 8 | this.b;
    }

    public SimpleARGB set(SimpleARGB argb) {
        this.setA(argb.alpha());
        this.setR(argb.red());
        this.setG(argb.green());
        this.setB(argb.blue());
        return this;
    }

    public SimpleARGB set(float r, float g, float b) {
        this.setR(r);
        this.setG(g);
        this.setB(b);
        return this;
    }

    public SimpleARGB set(float a, float r, float g, float b) {
        this.setA(a);
        this.setR(r);
        this.setG(g);
        this.setB(b);
        return this;
    }

    public SimpleARGB set(int r, int g, int b) {
        this.setR(r);
        this.setG(g);
        this.setB(b);
        return this;
    }

    public SimpleARGB set(int a, int r, int g, int b) {
        this.setA(a);
        this.setR(r);
        this.setG(g);
        this.setB(b);
        return this;
    }

    public void lerp(float x, int a, int b) {
        this.setA(Mth.lerpInt(x, a >>> 24, b >>> 24));
        this.setR(Mth.lerpInt(x, a << 16, b << 16));
        this.setG(Mth.lerpInt(x, a << 8, a << 8));
        this.setB(Mth.lerpInt(x, a, b));
    }

    public void lerp(float x, int a1, int a2, int r1, int r2, int g1, int g2, int b1, int b2) {
        this.setA(Mth.lerpInt(x, a1, a2));
        this.setR(Mth.lerpInt(x, r1, r2));
        this.setG(Mth.lerpInt(x, g1, g2));
        this.setB(Mth.lerpInt(x, b1, b2));
    }

    public void setA(float a) {
        MtJava.validatePercents(a);
        this.a =Mth.floor(Mth.clamp(a * 255, 0, 255));
    }

    public void setR(float r) {
        MtJava.validatePercents(a);
        this.r = Mth.floor(Mth.clamp(r * 255, 0, 255));
    }

    public void setG(float g) {
        MtJava.validatePercents(a);
        this.g = Mth.floor(Mth.clamp(g * 255, 0, 255));
    }

    public void setB(float b) {
        MtJava.validatePercents(a);
        this.b = Mth.floor(Mth.clamp(b * 255, 0, 255));
    }

    public void setA(int a) {
        this.a = Mth.clamp(a, 0, 255);
    }

    public void setR(int r) {
        this.r = Mth.clamp(r, 0, 255);
    }

    public void setG(int g) {
        this.g = Mth.clamp(g, 0, 255);
    }

    public void setB(int b) {
        this.b = Mth.clamp(b, 0, 255);
    }

    public float alphaFactor() {
        return this.alpha() / 255.0F;
    }

    public float redFactor() {
        return this.red() / 255.0F;
    }

    public float greenFactor() {
        return this.green() / 255.0F;
    }

    public float blueFactor() {
        return this.blue() / 255.0F;
    }

    public int alpha() {
        return this.a;
    }

    public int red() {
        return this.r;
    }

    public int green() {
        return this.g;
    }

    public int blue() {
        return this.b;
    }

    public int[] getColor() {
        return new int[]{this.alpha(), this.red(), this.green(), this.blue()};
    }

    public float[] getPercent() {
        return new float[]{this.alphaFactor(), this.redFactor(), this.greenFactor(), this.blueFactor()};
    }

    @Override
    public int compareTo(@NotNull SimpleARGB other) {
        return Integer.compare(this.getAsInt(), other.getAsInt());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SimpleARGB argb) {
            return Arrays.equals(this.getColor(), argb.getColor());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.getColor());
    }

    @Override
    public String toString() {
        return "ARGB(" + String.join(", ", Arrays.stream(this.getColor()).mapToObj(String::valueOf).toList()) + ")";
    }
}
