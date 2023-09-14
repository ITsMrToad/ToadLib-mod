package com.mr_toad.lib.data.tag;

import com.mr_toad.lib.core.ToadLib;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ToadlyBlockTagProvider extends BlockTagsProvider {

    public ToadlyBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, ToadLib.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider lookup) {
        this.tag(ToadlyTags.ToadlyBlockTags.ORES).add(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.ANCIENT_DEBRIS);
    }


}
