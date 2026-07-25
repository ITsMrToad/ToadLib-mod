package com.mr_toad.lib.api.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
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

import org.jetbrains.annotations.NotNull;

public class ToadTagUtils {

    public static TagKey<@NotNull Biome> ofBiome(String modid, String name) {
        return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull Fluid> ofFluid(String modid, String name) {
        return TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull EntityType<?>> ofEntityType(String modid, String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull FlatLevelGeneratorPreset> ofFlatLevelGeneratorPreset(String modid, String name) {
        return TagKey.create(Registries.FLAT_LEVEL_GENERATOR_PRESET, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull Item> ofItem(String modid, String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull Block> ofBlock(String modid, String name) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull CatVariant> ofCatVariant(String modid, String name) {
        return TagKey.create(Registries.CAT_VARIANT, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull PoiType> ofPOIType(String modid, String name) {
        return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull WorldPreset> ofWorldPreset(String modid, String name) {
        return TagKey.create(Registries.WORLD_PRESET, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull DamageType> ofDamageType(String modid, String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull BannerPattern> ofBannerPattern(String modid, String name) {
        return TagKey.create(Registries.BANNER_PATTERN, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull GameEvent> ofGameEvent(String modid, String name) {
        return TagKey.create(Registries.GAME_EVENT, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull Instrument> ofInstrument(String modid, String name) {
        return TagKey.create(Registries.INSTRUMENT, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull PaintingVariant> ofPaintingVariant(String modid, String name) {
        return TagKey.create(Registries.PAINTING_VARIANT, Identifier.fromNamespaceAndPath(modid, name));
    }

    public static TagKey<@NotNull Structure> ofStructure(String modid, String name) {
        return TagKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(modid, name));
    }
}
