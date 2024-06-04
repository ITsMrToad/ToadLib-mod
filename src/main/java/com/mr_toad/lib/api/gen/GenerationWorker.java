package com.mr_toad.lib.api.gen;

import com.google.common.collect.Sets;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import java.util.Set;
import java.util.function.Predicate;

public class GenerationWorker {

    private static int waterTemperature;

    private static final Set<ResourceKey<Biome>> CUSTOM_END_MUSIC_BIOMES = Sets.newHashSet();
    private static final Set<ResourceKey<Biome>> CUSTOM_NETHER_MUSIC_BIOMES = Sets.newHashSet();

    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler, BiomeSource original) {
        return original.getNoiseBiome(x, y, z, sampler);
    }

    public static Predicate<BlockState> isFluid(int minLevel, TagKey<Fluid> allowedFluids) {
        return (state) -> !state.getFluidState().isEmpty() && state.getFluidState().getOwnHeight() >= minLevel && state.getFluidState().is(allowedFluids);
    }

    public static synchronized void addToEndCustomMusic(ResourceKey<Biome> biomeName) {
        CUSTOM_END_MUSIC_BIOMES.add(biomeName);
    }

    public static synchronized void addToNetherCustomMusic(ResourceKey<Biome> biomeName) {
        CUSTOM_NETHER_MUSIC_BIOMES.add(biomeName);
    }

    public static boolean canPlayCustomEndMusic(ResourceKey<Biome> biomeName) {
        return CUSTOM_END_MUSIC_BIOMES.contains(biomeName);
    }

    public static boolean canPlayCustomNetherMusic(ResourceKey<Biome> biomeName) {
        return CUSTOM_NETHER_MUSIC_BIOMES.contains(biomeName);
    }

    public static int getVarietyWaterTemperature() {
        return waterTemperature;
    }

    public static int setVarietyWaterTemperature(int temperature) {
        return waterTemperature = temperature;
    }


}
