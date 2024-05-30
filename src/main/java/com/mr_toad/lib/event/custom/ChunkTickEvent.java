package com.mr_toad.lib.event.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;

public class ChunkTickEvent extends TickEvent {

    private final ServerLevel serverLevel;
    private final LevelChunk chunk;

    public ChunkTickEvent(Phase phase, ServerLevel serverLevel, LevelChunk chunk) {
        super(Type.SERVER, LogicalSide.SERVER, phase);

        this.serverLevel = serverLevel;
        this.chunk = chunk;
    }

    public ServerLevel getServerLevel() {
        return this.serverLevel;
    }

    public LevelChunk getChunk() {
        return this.chunk;
    }
}
