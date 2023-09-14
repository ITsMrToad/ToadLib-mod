package com.mr_toad.lib.data.tag;

import com.mr_toad.lib.core.ToadLib;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ToadlyItemTagProvider extends ItemTagsProvider {


    public ToadlyItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> future12, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, future12, ToadLib.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider lookup) {
        this.tag(ToadlyTags.ToadlyItemTags.ORES).add(Items.COAL_ORE, Items.DEEPSLATE_COAL_ORE, Items.COPPER_ORE, Items.DEEPSLATE_COPPER_ORE, Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE, Items.GOLD_ORE, Items.DEEPSLATE_GOLD_ORE, Items.NETHER_GOLD_ORE, Items.REDSTONE_ORE, Items.DEEPSLATE_REDSTONE_ORE, Items.LAPIS_ORE, Items.DEEPSLATE_LAPIS_ORE, Items.EMERALD_ORE, Items.DEEPSLATE_EMERALD_ORE, Items.DIAMOND_ORE, Items.DEEPSLATE_DIAMOND_ORE, Items.ANCIENT_DEBRIS);
    }




}
