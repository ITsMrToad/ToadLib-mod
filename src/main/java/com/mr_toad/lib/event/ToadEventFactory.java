package com.mr_toad.lib.event;

import com.mr_toad.lib.event.custom.ChunkTickEvents;
import com.mr_toad.lib.event.custom.CustomDeathLootEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public final class ToadEventFactory {

    public static void onCustomDeathLoot(LivingEntity entity, ServerLevel world, DamageSource source) {
        CustomDeathLootEvent.DROP_LOOT.invoker().drop(entity, world, source);
    }

    public static void onChunkTick(LevelChunk chunk, ServerLevel serverWorld, int randomTickSpeed) {
        ChunkTickEvents.CHUNK_TICK.invoker().onChunkTick(chunk, serverWorld, randomTickSpeed);
    }
}
