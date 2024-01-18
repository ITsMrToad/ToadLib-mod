package com.mr_toad.lib.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

public final class ToadLibEventFactory {

    public static <T extends Entity> void registerNoRMBNLAnd(EntityType<T> entityType, SpawnPlacements.SpawnPredicate<T> predicate, SpawnPlacementRegisterEvent event) {
        event.register(entityType, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate, SpawnPlacementRegisterEvent.Operation.AND);
    }

    public static <T extends Entity> void registerOnGroundMBNLAnd(EntityType<T> entityType, SpawnPlacements.SpawnPredicate<T> predicate, SpawnPlacementRegisterEvent event) {
        event.register(entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate, SpawnPlacementRegisterEvent.Operation.AND);
    }

    public static <T extends Entity> void registerNoRMBNLOR(EntityType<T> entityType, SpawnPlacements.SpawnPredicate<T> predicate, SpawnPlacementRegisterEvent event) {
        event.register(entityType, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate, SpawnPlacementRegisterEvent.Operation.OR);
    }

    public static <T extends Entity> void registerOnGroundMBNLOr(EntityType<T> entityType, SpawnPlacements.SpawnPredicate<T> predicate, SpawnPlacementRegisterEvent event) {
        event.register(entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate, SpawnPlacementRegisterEvent.Operation.OR);
    }


    public static void onChunkTick(ServerLevel serverLevel, LevelChunk chunk, BooleanSupplier haveTime) {
        MinecraftForge.EVENT_BUS.post(new ChunkTickEvent(TickEvent.Phase.START, serverLevel, chunk, haveTime));
    }

}
