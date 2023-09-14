package com.mr_toad.lib.data.tag;

import com.mr_toad.lib.core.ToadLib;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ToadlyBiomeTagProvider extends BiomeTagsProvider {

    public ToadlyBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, providerCompletableFuture, ToadLib.MODID, existingFileHelper);
    }

    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ToadlyTags.ToadlyBiomeTags.IS_PLAINSLIKE).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW);
    }



}
