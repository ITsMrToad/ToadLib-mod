package com.mr_toad.lib.api.client.utils.graphics.gl;

import com.mr_toad.lib.api.client.utils.graphics.GraphicsException;
import org.lwjgl.system.MemoryUtil;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

public class ToadlyMemoryTracker {

    private static final MemoryUtil.MemoryAllocator ALLOCATOR = MemoryUtil.getAllocator(false);

    public static ByteBuffer byteAlloc(int size) {
        return alloc(size, (address) -> MemoryUtil.memByteBuffer(address, size));
    }

    public static ByteBuffer byteResize(ByteBuffer buffer, int size) {
        return resize(buffer, size, (address) -> MemoryUtil.memByteBuffer(address, size));
    }

    public static ShortBuffer shortAlloc(int size) {
        return alloc(size, (address) -> MemoryUtil.memShortBuffer(address, size));
    }

    public static ShortBuffer shortResize(ShortBuffer buffer, int size) {
        return resize(buffer, size, (address) -> MemoryUtil.memShortBuffer(address, size));
    }

    public static IntBuffer intAlloc(int size) {
        return alloc(size, (address) -> MemoryUtil.memIntBuffer(address, size));
    }

    public static IntBuffer intResize(IntBuffer buffer, int size) {
        return resize(buffer, size, (address) -> MemoryUtil.memIntBuffer(address, size));
    }

    public static FloatBuffer floatAlloc(int size) {
        return alloc(size, (address) -> MemoryUtil.memFloatBuffer(address, size));
    }

    public static FloatBuffer floatResize(FloatBuffer buffer, int size) {
        return resize(buffer, size, (address) -> MemoryUtil.memFloatBuffer(address, size));
    }

    public static ByteBuffer byteSlice(ByteBuffer buffer, int offset, int size) {
        return slice(offset, size, (o) -> MemoryUtil.memSlice(buffer, size, o));
    }

    public static ShortBuffer shortSlice(ShortBuffer buffer, int offset, int size) {
        return slice(offset, size, (o) -> MemoryUtil.memSlice(buffer, size, o));
    }

    public static IntBuffer intSlice(IntBuffer buffer, int offset, int size) {
        return slice(offset, size, (o) -> MemoryUtil.memSlice(buffer, size, o));
    }

    public static FloatBuffer floatSlice(FloatBuffer buffer, int offset, int size) {
        return slice(offset, size, (o) -> MemoryUtil.memSlice(buffer, size, o));
    }

    public static<B extends Buffer> void memFree(B buffer) {
        long address = MemoryUtil.memAddress(buffer);
        if (address == 0L) {
            throw new GraphicsException("Failed to free memory of '" + buffer + "'", GraphicsException.In.MEMORY, new OutOfMemoryError());
        } else {
            ALLOCATOR.free(address);
        }
    }

    private static<B extends Buffer> B slice(int offset, int size, IntFunction<B> slicer) {
        return slicer.apply(size - offset);
    }

    private static<B extends Buffer> B alloc(int size, LongFunction<B> allocator) {
        long address = ALLOCATOR.malloc(size);
        if (address == 0L) {
            throw new GraphicsException("Failed to allocate '" + size + "' bytes for buffer", GraphicsException.In.MEMORY, new OutOfMemoryError());
        } else {
            return allocator.apply(address);
        }
    }

    private static<B extends Buffer> B resize(B buffer, int newSize, LongFunction<B> resizer) {
        long address = ALLOCATOR.realloc(MemoryUtil.memAddress0(buffer), newSize);
        if (address == 0L) {
            throw new GraphicsException("Failed to re-allocate buffer from'" + buffer.capacity() + "' bytes to '" + newSize + "' bytes for buffer", GraphicsException.In.MEMORY, new OutOfMemoryError());
        } else {
            return resizer.apply(address);
        }
    }
}
