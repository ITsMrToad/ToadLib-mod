package com.mr_toad.lib.mtjava.nio;

import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import com.mr_toad.lib.mtjava.math.vec.base.IntVec;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;

import java.nio.ByteBuffer;

public class MTNIO {

    public static void putId(ByteBuffer buffer, Identifier id) {
        putString(buffer, id.toString());
    }

    public static void putString(ByteBuffer buffer, String s) {
        buffer.put(s.getBytes());
    }

    public static<V extends FloatVec<V>> void putFloatVec(ByteBuffer buffer, V vec) {
        vec.values().forEach(buffer::putFloat);
    }

    public static<V extends IntVec<V>> void putIntVec(ByteBuffer buffer, V vec) {
        vec.values().forEach(buffer::putInt);
    }

    public static<V extends DoubleVec<V>> void putDoubleVec(ByteBuffer buffer, V vec) {
        vec.values().forEach(buffer::putDouble);
    }

    public static String readString(ByteBuffer buffer, String s) {
        return readString(buffer, s.length());
    }

    public static String readString(ByteBuffer buffer, int length) {
        return readString(buffer, 0, length);
    }

    public static String readString(ByteBuffer buffer, int start, int length) {
        byte[] bytes = new byte[length];
        buffer.position(start);
        buffer.get(bytes, 0, length);
        return new String(bytes);
    }

    public static DoubleList readDoubleList(FriendlyByteBuf buf) {
        return buf.readCollection(DoubleArrayList::new, FriendlyByteBuf::readDouble);
    }

    public static FloatList readFloatList(FriendlyByteBuf buf) {
        return buf.readCollection(FloatArrayList::new, FriendlyByteBuf::readFloat);
    }

    public static IntList readIntList(FriendlyByteBuf buf) {
        return buf.readCollection(IntArrayList::new, FriendlyByteBuf::readInt);
    }
}
