package com.mr_toad.lib.core.mixin;

import com.google.common.collect.Lists;
import com.mr_toad.lib.api.util.SpawnerHolder;
import com.mr_toad.lib.event.ToadEventFactory;
import com.mr_toad.lib.event.custom.RegisterCustomSpawnersEvent;
import com.mr_toad.lib.event.custom.RegisterSavedDataEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin extends Level {

    @Mutable @Shadow @Final private List<CustomSpawner> customSpawners;

    protected ServerWorldMixin(WritableLevelData writableLevelData, ResourceKey<@NotNull Level> resourceKey, RegistryAccess registryAccess, Holder<@NotNull DimensionType> holder, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, bl, bl2, l, i);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void toadlibInit(MinecraftServer server, Executor executor, LevelStorageSource.LevelStorageAccess levelStorage, ServerLevelData levelData, ResourceKey<Level> dimension, LevelStem levelStem, boolean isDebug, long biomeZoomSeed, List<CustomSpawner> customSpawners, boolean tickTime, CallbackInfo ci) {
        RegisterSavedDataEvent.REGISTER_SAVED_DATA.invoker().apply((ServerLevel) (Object) this);
        List<SpawnerHolder> spawnerHolders = Lists.newArrayList();
        RegisterCustomSpawnersEvent.SPAWNER_REGISTRY.invoker().apply(spawnerHolders);
        if (!spawnerHolders.isEmpty()) {
            List<CustomSpawner> spnws = Lists.newArrayList(customSpawners);
            spawnerHolders.stream().filter(holder -> holder.dim() == dimension).map(SpawnerHolder::spawner).forEach(spnws::add);
            this.customSpawners = spnws;
        }
    }

    @Inject(method = "tickChunk", at = @At("TAIL"))
    public void startTickChunkEvent(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ToadEventFactory.onChunkTick(chunk, (ServerLevel) (Object) this, randomTickSpeed);
    }
}