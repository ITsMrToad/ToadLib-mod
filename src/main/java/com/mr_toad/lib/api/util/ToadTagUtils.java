package com.mr_toad.lib.api.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

public class ToadTagUtils {

    public static TagKey<Biome> createBiome(String modId, String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(modId, name));
    }

    public static TagKey<Fluid> createFluid(String modid, String name) {
        return TagKey.create(Registries.FLUID, new ResourceLocation(modid, name));
    }

    public static TagKey<EntityType<?>> createEntityType(String modid, String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(modid, name));
    }

    public static TagKey<FlatLevelGeneratorPreset> createFlatLevelGeneratorPreset(String modid, String name) {
        return TagKey.create(Registries.FLAT_LEVEL_GENERATOR_PRESET, new ResourceLocation(modid, name));
    }

    public static TagKey<Item> createItem(String modid, String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(modid, name));
    }

    public static TagKey<Block> createBlock(String modid, String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(modid, name));
    }

    public static TagKey<CatVariant> createCatVariant(String modid, String name) {
        return TagKey.create(Registries.CAT_VARIANT, new ResourceLocation(modid, name));
    }

    public static TagKey<PoiType> createPOIType(String modid, String name) {
        return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, new ResourceLocation(modid, name));
    }

    public static TagKey<WorldPreset> createWorldPreset(String modid, String name) {
        return TagKey.create(Registries.WORLD_PRESET, new ResourceLocation(modid, name));
    }

    public static TagKey<DamageType> createDamageType(String modid, String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(modid, name));
    }

    public static TagKey<BannerPattern> createBannerPattern(String modid, String name) {
        return TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(modid, name));
    }

    public static TagKey<GameEvent> createGameEvent(String modid, String name) {
        return TagKey.create(Registries.GAME_EVENT, new ResourceLocation(modid, name));
    }

    public static TagKey<Instrument> createInstrument(String modid, String name) {
        return TagKey.create(Registries.INSTRUMENT, new ResourceLocation(modid, name));
    }

    public static TagKey<PaintingVariant> createPaintingVariant(String modid, String name) {
        return TagKey.create(Registries.PAINTING_VARIANT, new ResourceLocation(modid, name));
    }

    public static TagKey<Structure> createStructure(String modid, String name) {
        return TagKey.create(Registries.STRUCTURE, new ResourceLocation(modid, name));
    }
}
