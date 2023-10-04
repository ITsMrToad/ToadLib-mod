package com.mr_toad.lib.api.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ToadItemUtils {

    public static ItemStack dropIfFullInventory(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player && !((Player)entity).getAbilities().instabuild) {
            ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
            if (!player.getInventory().add(itemstack)) {
                player.drop(itemstack, false);
            }
        }

        return stack;
    }

    public static int getColor(ItemStack stack, boolean b0) {
        if (!b0) return 0x77533F;
        CompoundTag compoundtag = stack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : 10511680;
    }

}
