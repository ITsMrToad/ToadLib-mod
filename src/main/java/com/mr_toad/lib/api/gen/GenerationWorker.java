package com.mr_toad.lib.api.gen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class GenerationWorker {

    private static int waterTemperature;

    private static final Set<ResourceKey<Biome>> CUSTOM_END_MUSIC_BIOMES = new HashSet<>();
    private static final Set<ResourceKey<Biome>> CUSTOM_NETHER_MUSIC_BIOMES = new HashSet<>();

    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler, BiomeSource original) {
        return original.getNoiseBiome(x, y, z, sampler);
    }

    public static Predicate<BlockState> isFluid(int minLevel, TagKey<Fluid> allowedFluids) {
        return (state) -> !state.getFluidState().isEmpty() && state.getFluidState().getOwnHeight() >= minLevel && state.getFluidState().is(allowedFluids);
    }

    public static synchronized void markEndBiomeCustomMusic(ResourceKey<Biome> biomeName) {
        CUSTOM_END_MUSIC_BIOMES.add(biomeName);
    }

    public static boolean shouldPlayCustomEndMusic(ResourceKey<Biome> biomeName) {
        return CUSTOM_END_MUSIC_BIOMES.contains(biomeName);
    }

    public static synchronized void markNetherBiomeCustomMusic(ResourceKey<Biome> biomeName) {
        CUSTOM_NETHER_MUSIC_BIOMES.add(biomeName);
    }

    public static boolean shouldPlayCustomNetherMusic(ResourceKey<Biome> biomeName) {
        return CUSTOM_NETHER_MUSIC_BIOMES.contains(biomeName);
    }

    public static int getVarietyWaterTemperature() {
        return waterTemperature;
    }

    public static int setVarietyWaterTemperature(int temperature) {
        return waterTemperature = temperature;
    }

    public static BlockPos getClosestPositionToPos(List<BlockPos> positions, BlockPos pos) {
        double distance = -1.0D;
        BlockPos blockPos = null;

        for (BlockPos listOfPositions : positions) {
            double newDistance = Vec3.atLowerCornerOf(pos).distanceToSqr(Vec3.atLowerCornerOf((listOfPositions)));
            if (distance == -1.0D || newDistance < distance) {
                distance = newDistance;
                blockPos = listOfPositions;
            }
        }

        return blockPos;
    }
}
