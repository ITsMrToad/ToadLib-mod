package com.mr_toad.lib.core.mixin;

import com.mr_toad.lib.event.ToadEventFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements WorldGenLevel {

    protected ServerLevelMixin(WritableLevelData data, ResourceKey<Level> key, RegistryAccess access, Holder<DimensionType> dimensionType, Supplier<ProfilerFiller> profilerFiller, boolean aB01, boolean aB02, long s, int i0) {
        super(data, key, access, dimensionType, profilerFiller, aB01, aB02, s, i0);
    }

    @Inject(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;getPos()Lnet/minecraft/world/level/ChunkPos;", shift = At.Shift.BEFORE))
    public void eventChunkStart(LevelChunk chunk, int i0, CallbackInfo ci) {
        ToadEventFactory.onChunkTickStart(this.getLevel(), chunk);
    }

    @Inject(method = "tickChunk", at = @At("TAIL"))
    public void eventChunkEnd(LevelChunk chunk, int i0, CallbackInfo ci) {
        ToadEventFactory.onChunkTickEnd(this.getLevel(), chunk);
    }
}
