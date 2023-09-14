package com.mr_toad.lib.api.outer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ToadlyDataProvider {

    public abstract static class AbstractToadlyBlockStateProvider extends BlockStateProvider {

        public AbstractToadlyBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
            super(output, modid, exFileHelper);
        }

        public void chLogBlock(RotatedPillarBlock block, String name) {
            axisBlock(block, blockTexture(block), new ResourceLocation("block/" + name + "_log_top"));
        }

    }

    public abstract static class AbstractToadlyRecipeProvider  {

        public static ShapedRecipeBuilder shaped(ItemLike like) {
            return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, like);
        }

        public static SingleItemRecipeBuilder cutting(Ingredient ingredient, ItemLike like) {
            return SingleItemRecipeBuilder.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, like);
        }

        public static SimpleCookingRecipeBuilder campfireCoking(Ingredient ingredient, ItemLike like, float f0, int i0) {
            return SimpleCookingRecipeBuilder.campfireCooking(ingredient, RecipeCategory.FOOD, like, f0, i0);
        }

        public static SimpleCookingRecipeBuilder cooking(Ingredient ingredient, ItemLike like, float f0, int i0, RecipeSerializer<? extends AbstractCookingRecipe> recipeSerializer) {
            return SimpleCookingRecipeBuilder.generic(ingredient, RecipeCategory.FOOD, like, f0, i0, recipeSerializer);
        }

    }

    public abstract static class AbstractToadlyAdvancementProvider {
        public static Advancement.Builder getAdvancement(String modid, Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return Advancement.Builder.advancement().parent(parent).display(display,
                    Component.translatable(modid + "." + name),
                    Component.translatable(modid + "." + name + ".desc"), null, frame, showToast, announceToChat, hidden);
        }
    }


}
