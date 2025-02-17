package com.mr_toad.lib.mtjava.nio;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.mr_toad.lib.api.client.utils.graphics.gl.ToadlyMemoryTracker;
import com.mr_toad.lib.mtjava.math.vec.base.DoubleVec;
import com.mr_toad.lib.mtjava.math.vec.base.FloatVec;
import com.mr_toad.lib.mtjava.math.vec.base.IntVec;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MTNIO {

    public static<V extends FloatVec<V>> void putFloatVec(ByteBuffer buffer, V vec) {
        vec.values().forEach(buffer::putFloat);
    }

    public static<V extends IntVec<V>> void putIntVec(ByteBuffer buffer, V vec) {
        vec.values().forEach(buffer::putInt);
    }

    public static<V extends DoubleVec<V>> void putDoubleVec(ByteBuffer buffer, V vec) {
        vec.values().forEach(buffer::putDouble);
    }

    public static ByteBuffer fillBufferFrom(ResourceLocation rl) throws IOException {
        try (InputStream stream = Minecraft.getInstance().getResourceManager().open(rl)) {
            return fillBufferFrom(stream);
        }
    }

    public static ByteBuffer fillBufferFrom(InputStream stream) throws IOException {
        ByteSource byteSource = ByteSource.wrap(ByteStreams.toByteArray(stream));
        byte[] data = byteSource.read();
        ByteBuffer buffer = ToadlyMemoryTracker.byteAlloc(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
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
}
