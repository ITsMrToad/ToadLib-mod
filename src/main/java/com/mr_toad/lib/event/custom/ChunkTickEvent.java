package com.mr_toad.lib.event.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.function.BooleanSupplier;

public class ChunkTickEvent extends TickEvent {

    private final ServerLevel serverLevel;
    private final LevelChunk chunk;
    private final BooleanSupplier haveTime;

    public ChunkTickEvent(Phase phase, ServerLevel serverLevel, LevelChunk chunk, BooleanSupplier haveTime) {
        super(Type.SERVER, LogicalSide.SERVER, phase);

        this.serverLevel = serverLevel;
        this.chunk = chunk;
        this.haveTime = haveTime;
    }

    public ServerLevel getServerLevel() {
        return this.serverLevel;
    }

    public LevelChunk getChunk() {
        return this.chunk;
    }

    public boolean haveTime() {
        return this.haveTime.getAsBoolean();
    }

}
