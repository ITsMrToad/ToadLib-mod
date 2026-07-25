package com.mr_toad.lib.client.mixin;

import com.mr_toad.lib.api.client.screen.ex.widget.CloseableWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Shadow @Final private List<GuiEventListener> children;

    @Inject(method = "onClose", at = @At("TAIL"))
    public void closeWidgets(CallbackInfo ci) {
        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i) instanceof CloseableWidget callback) {
                callback.onClose();
            }
        }
    }
}
