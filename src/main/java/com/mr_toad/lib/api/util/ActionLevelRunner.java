package com.mr_toad.lib.api.util;

import com.mr_toad.lib.core.ToadLib;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.IOException;
import java.util.function.Consumer;

public record ActionLevelRunner<T extends Level>(T lvl, Consumer<T> action) {

    private static final Marker MARKER = MarkerFactory.getMarker("LevelRunnerError");

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
        try(T level = this.lvl()) {
            if (!level.isClientSide()) {
                this.action().accept(level);
            }
        } catch (IOException e) {
            ToadLib.LOGGER.error(MARKER, "Cannot run server level action: {}", this.action(), e);
        }
    }

    private void levelAction() {
        try(T level = this.lvl()) {
            this.action().accept(level);
        } catch (IOException e) {
            ToadLib.LOGGER.error(MARKER, "Cannot run level action: {}", this.action(), e);
        }
    }

}
