package com.mr_toad.lib.api.client.utils.graphics.gl;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mr_toad.lib.api.client.utils.graphics.GraphicsException;
import com.mr_toad.lib.api.client.utils.graphics.ToadMemoryUtils;
import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL44;
import org.lwjgl.system.MemoryStack;

import java.util.Objects;
import java.util.function.IntSupplier;

public class GLU {

    public static final int STORAGE_FLAGS = GL30.GL_MAP_WRITE_BIT | GL44.GL_MAP_PERSISTENT_BIT;
    public static final int FLUSH_MAP_FLAGS = GL30.GL_MAP_WRITE_BIT | GL44.GL_MAP_PERSISTENT_BIT | GL30.GL_MAP_FLUSH_EXPLICIT_BIT;

    private static final Long2ObjectMap<GLProfiler> PROFILERS = new Long2ObjectOpenHashMap<>();

    public static GLProfiler getProfiler(String s, int target) {
        long key = Objects.hash(s, target);
        GLProfiler profiler = PROFILERS.get(key);
        if (profiler != null) {
            return profiler;
        }
        GLProfiler p = new GLProfiler(s, target);
        PROFILERS.put(key, p);
        return p;
    }

    public static void destroyProfilers() {
        if (PROFILERS.isEmpty()) {
            return;
        }

        int totalCount = 0;
        for (GLProfiler a : PROFILERS.values()) {
            totalCount += a.getQueries().length;
        }

        if (totalCount == 0) {
            return;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            long ptr = stack.nmalloc(totalCount * 4);
            long offset = 0L;
            for (GLProfiler value : PROFILERS.values()) {
                int[] arr = value.getQueries();
                ToadMemoryUtils.copyIntArray(ptr, offset, arr);
                offset += arr.length * 4L;
            }
            GL15.nglDeleteQueries(totalCount, ptr);
        }
    }

    public static void unbindBuffer(int target) {
        GlStateManager._glBindBuffer(target, 0);
    }

    public static boolean checkErrors(boolean shutdown) {
        int error = GlStateManager._getError();
        if (error != 0) {
            ToadLib.LOGGER.error("OpenGL got error: {}", error);
            if (shutdown) {
                ToadLib.LOGGER.warn("Exiting...");
                throw new GraphicsException("'" + error + "'", GraphicsException.In.GL);
            } else {
                ToadLib.LOGGER.warn("This error may cause crash or optimization problems!");
            }
            return false;
        } else {
            return true;
        }
    }

    public static void deleteBuffers(IntArrayList list) {
        GL15.glDeleteBuffers(list.elements());
    }

    ///@deprecated use {@link #deleteBuffers(it.unimi.dsi.fastutil.ints.IntArrayList)}
    @Deprecated(since = "1.5.0")
    public static void delBuffers(IntSupplier... buffers) {
        for (IntSupplier buffer : buffers) {
            GlStateManager._glDeleteBuffers(buffer.getAsInt());
        }
    }

    public static long getQueryResult(int id) {
        return GL33.glGetQueryObjectui64(id, GL15.GL_QUERY_RESULT);
    }
}
