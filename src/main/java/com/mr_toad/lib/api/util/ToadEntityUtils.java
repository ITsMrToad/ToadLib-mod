package com.mr_toad.lib.api.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

public class ToadEntityUtils {

    public static void addParrotImitation(EntityType<?> entityType, SoundEvent soundEvent) {
        Parrot.MOB_SOUND_MAP.put(entityType, soundEvent);
    }

    public static void addParrotFood(Item... items) {
        Collections.addAll(Parrot.TAME_FOOD, items);
    }

    public static boolean isHero(Player player) {
        return player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE);
    }

    public static void villagerCraftItem(Villager villager, Item result, Item ingredient, int maxCount) {
        SimpleContainer simplecontainer = villager.getInventory();

        if (simplecontainer.countItem(result) <= maxCount) {
            int i = simplecontainer.countItem(ingredient);
            int j = 1;
            int k = 1;
            int l = Math.min(j, i / j);

            if (l != 0) {
                int i1 = l * k;
                simplecontainer.removeItemType(ingredient, i1);
                ItemStack itemstack = simplecontainer.addItem(new ItemStack(result, l));

                if (!itemstack.isEmpty()) {
                    villager.spawnAtLocation(itemstack, 0.5F);
                }

            }
        }
    }
}
