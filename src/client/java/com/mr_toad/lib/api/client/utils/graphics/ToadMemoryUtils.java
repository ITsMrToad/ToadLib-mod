package com.mr_toad.lib.api.client.utils.graphics;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import org.lwjgl.system.MemoryUtil;
import sun.misc.Unsafe;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ToadMemoryUtils {

    public static final Unsafe UNSAFE = UnsafeAccess.UNSAFE;
    public static final MemoryUtil.MemoryAllocator ALLOCATOR = MemoryUtil.getAllocator(false);

    public static ByteBuffer fromStream(InputStream stream) throws IOException {
        try (ReadableByteChannel channel = Channels.newChannel(stream)) {
            int initialCapacity = Math.max(stream.available(), 8192);
            ByteBuffer buffer = MemoryUtil.memAlloc(initialCapacity);
            while (channel.read(buffer) != -1) {
                if (!buffer.hasRemaining()) {
                    int newCapacity = buffer.capacity() + (buffer.capacity() >> 1);
                    buffer = MemoryUtil.memRealloc(buffer, newCapacity);
                }
            }
            buffer.flip();
            return buffer;
        }
    }

    public static void copyIntArray(long address, long offset, int[] arr) {
        long bytes = arr.length * 4L;
        UNSAFE.copyMemory(arr, Unsafe.ARRAY_INT_BASE_OFFSET, null, address + offset, bytes);
    }

    public static void copyFloatArray(long address, long offset, float[] arr) {
        long bytes = arr.length * 4L;
        UNSAFE.copyMemory(arr, Unsafe.ARRAY_FLOAT_BASE_OFFSET, null, address + offset, bytes);
    }
}
