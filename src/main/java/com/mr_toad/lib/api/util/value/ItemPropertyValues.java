package com.mr_toad.lib.api.util.value;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemPropertyValues {

    public static Item.Properties tool() {
        return new Item.Properties().stacksTo(1);
    }

    public static Item.Properties food(FoodProperties food) {
        return new Item.Properties().food(food);
    }

    public static Item.Properties fireResistantItem() {
        return new Item.Properties().fireResistant();
    }

    public static Item.Properties fireResistantTool() {
        return tool().fireResistant();
    }

    public static Item.Properties fireResistantFood(FoodProperties food) {
        return food(food).fireResistant();
    }

    public static Item.Properties itemWRarity(Rarity rarity) {
        return new Item.Properties().rarity(rarity);
    }

    public static Item.Properties foodWRarity(Rarity rarity, FoodProperties food) {
        return food(food).rarity(rarity);
    }

    public static Item.Properties toolWRarity(Rarity rarity) {
        return tool().rarity(rarity);
    }

    public static Item.Properties itemWDurability(int durability) {
        return new Item.Properties().durability(durability);
    }

    public static Item.Properties toolWDurability(int durability) {
        return tool().durability(durability);
    }

    public static Item.Properties foodWDurability(int durability, FoodProperties food) {
        return food(food).durability(durability);
    }

    public static Item.Properties toolWDurabilityWRarity(int durability, Rarity rarity) {
        return toolWRarity(rarity).durability(durability);
    }

    public static Item.Properties foodWDurabilityWRarity(int durability, Rarity rarity, FoodProperties food) {
        return foodWRarity(rarity, food).durability(durability);
    }

    public static Item.Properties itemWDurabilityWRarity(int durability, Rarity rarity) {
        return itemWRarity(rarity).durability(durability);
    }

}
