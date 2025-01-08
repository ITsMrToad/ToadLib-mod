package com.mr_toad.lib.api.client.utils.graphics.gl;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mojang.blaze3d.pipeline.RenderCall;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mr_toad.lib.api.client.utils.graphics.GraphicsException;
import com.mr_toad.lib.core.ToadLib;
import org.lwjgl.opengl.GL11;

import java.util.function.IntSupplier;

public class GLU {

    public static void deleteBuffers(IntSupplier... buffers) {
        callRender(() -> _delBuffers(buffers));
    }

    public static void unbindBuffer(int target) {
        RenderSystem.glBindBuffer(target, () -> 0);
    }

    public static void pointSize(float size) {
        GL11.glPointSize(size);
    }

    public static void callRender(RenderCall call) {
        if (RenderSystem.isOnRenderThread()) {
            call.execute();
        } else {
            RenderSystem.recordRenderCall(call);
        }
    }

    @CanIgnoreReturnValue
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

    private static void _delBuffers(IntSupplier... buffers) {
        for (IntSupplier buffer : buffers) {
            RenderSystem.glDeleteBuffers(buffer.getAsInt());
        }
    }
}
