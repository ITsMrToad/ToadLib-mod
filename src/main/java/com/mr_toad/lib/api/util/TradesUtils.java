package com.mr_toad.lib.api.util;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;

//Utils for trades with Villager / Wandering traders
public class TradesUtils {

    public static final int NOVICE = 1;
    public static final int APPRENTICE = 2;
    public static final int JOURNEYMAN = 3;
    public static final int EXPERT = 4;
    public static final int MASTER = 5;

    public static void addVillagerTrades(VillagerTradesEvent event, int level, VillagerTrades.ItemListing... trades) {
        for (VillagerTrades.ItemListing trade : trades) event.getTrades().get(level).add(trade);
    }

    public static void addVillagerTrades(VillagerTradesEvent event, VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        if (event.getType() == profession) addVillagerTrades(event, level, trades);
    }

    public static void addWandererTrades(WandererTradesEvent event, VillagerTrades.ItemListing... trades) {
        for (VillagerTrades.ItemListing trade : trades) event.getGenericTrades().add(trade);
    }

    public static void addRareWandererTrades(WandererTradesEvent event, VillagerTrades.ItemListing... trades) {
        for (VillagerTrades.ItemListing trade : trades) event.getRareTrades().add(trade);
    }


    public static class NewTrade extends BasicItemListing {

        public NewTrade(ItemStack input, ItemStack input2, ItemStack output, int maxTrades, int xp, float priceMulti) {
            super(input, input2, output, maxTrades, xp, priceMulti);
        }

        public NewTrade(Item input, int inputCount, int emeraldCount, int maxTrades, int xp, float priceMulti) {
            this(new ItemStack(input, inputCount), ItemStack.EMPTY, new ItemStack(Items.EMERALD, emeraldCount), maxTrades, xp, priceMulti);
        }

        public NewTrade(Item input, int inputCount, int emeraldCount, int maxTrades, int xp) {
            this(input, inputCount, emeraldCount, maxTrades, xp, 0.15F);
        }

        public NewTrade(int emeraldCount, Item output, int outputCount, int maxTrades, int xp, float priceMulti) {
            this(new ItemStack(Items.EMERALD, emeraldCount), ItemStack.EMPTY, new ItemStack(output, outputCount), maxTrades, xp, priceMulti);
        }

        public NewTrade(int emeraldCount, Item output, int outputCount, int maxTrades, int xp) {
            this(emeraldCount, output, outputCount, maxTrades, xp, 0.15F);
        }

    }
}
