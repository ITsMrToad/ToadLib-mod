package com.mr_toad.lib.event;

import com.mr_toad.lib.event.custom.ChunkTickEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public final class ToadLibEventFactory {

    public static void onChunkTickStart(ServerLevel serverLevel, LevelChunk chunk) {
        MinecraftForge.EVENT_BUS.post(new ChunkTickEvent(TickEvent.Phase.START, serverLevel, chunk));
    }

    public static void onChunkTickEnd(ServerLevel serverLevel, LevelChunk chunk) {
        MinecraftForge.EVENT_BUS.post(new ChunkTickEvent(TickEvent.Phase.END, serverLevel, chunk));
    }

}
