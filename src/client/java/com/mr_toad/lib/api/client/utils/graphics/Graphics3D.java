package com.mr_toad.lib.api.client.utils.graphics;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.function.Consumer;

public class Graphics3D {

    public static void popPush(PoseStack stack, Consumer<PoseStack> action) {
        stack.pushPose();
        action.accept(stack);
        stack.popPose();
    }

    public static void safePopPush(PoseStack stack, Consumer<PoseStack> action) {
        stack.pushPose();
        try {
            action.accept(stack);
        } finally {
            stack.popPose();
        }
    }
}
