package com.mr_toad.lib.api.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

public record ActionLevelRunner<T extends Level>(T lvl, Consumer<T> action) {

    public static<T extends Level> void lTry(T lvl, Consumer<T> action) {
        new ActionLevelRunner<>(lvl, action).levelAction();
    }

    public static void serverTry(ServerLevel serverLevel, Consumer<ServerLevel> action) {
        new ActionLevelRunner<>(serverLevel, action).serverLevelAction();
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientTry(ClientLevel clientLevel, Consumer<ClientLevel> action) {
        new ActionLevelRunner<>(clientLevel, action).levelAction();
    }

    private void serverLevelAction() {
        if (!level.isClientSide()) {
            this.action().accept(level); 
        }   
    }

    private void levelAction() {
        this.action().accept(level);
    }

}
