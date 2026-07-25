package com.mr_toad.lib.event.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;

import org.jetbrains.annotations.NotNull;

public class ChunkTickEvents {

    public static final Event<@NotNull ChunkTick> CHUNK_TICK = EventFactory.createArrayBacked(ChunkTick.class, callbacks -> (chunk, serverWorld, randomTickSpeed) -> {
        for (ChunkTick callback : callbacks) {
            callback.onChunkTick(chunk, serverWorld, randomTickSpeed);
        }
    });


    @FunctionalInterface
    public interface ChunkTick {
        void onChunkTick(LevelChunk chunk, ServerLevel serverWorld, int randomTickSpeed);
    }
}