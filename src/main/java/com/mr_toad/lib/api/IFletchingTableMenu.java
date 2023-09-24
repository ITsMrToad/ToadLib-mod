package com.mr_toad.lib.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public interface IFletchingTableMenu {

    boolean isValidBlock(BlockState state);
    boolean mayPickup(Player player, boolean b01);
    void onTake(Player player, ItemStack stack);
    void createResult();
    boolean shouldQuickMoveToAdditionalSlot(ItemStack stack);

}
