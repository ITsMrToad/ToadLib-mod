package com.mr_toad.lib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.biome.EndBiomes;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.Set;

public class BehaviorHelper {

    private static final int INTERACT_DIST_SQR = 5;
    private static final float SPEED_MODIFIER = 0.5F;

    protected boolean isWithinDistance(Mob mob, LivingEntity target, double distance) {
        BlockPos blockPos = target.blockPosition();
        BlockPos blockPos1 = mob.blockPosition();
        return blockPos1.closerThan(blockPos, distance);
    }

    public boolean tickConditionsInvolved(ServerLevel serverLevel, Villager vi, long l0) {
        Villager villager = (Villager) vi.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        if (!(vi.distanceToSqr(villager) > INTERACT_DIST_SQR)) {
            BehaviorUtils.lockGazeAndWalkToEachOther(vi, villager, SPEED_MODIFIER);
            vi.gossip(serverLevel, villager, l0);
            return true;
        } else {
            return false;

        }

    }

    public void throwHalfStack(Villager villager, Set<Item> items, LivingEntity entity) {
        SimpleContainer simplecontainer = villager.getInventory();
        ItemStack itemstack = ItemStack.EMPTY;
        int i = 0;

        while (i < simplecontainer.getContainerSize()) {
            ItemStack itemStack1;
            Item item;
            int j;
            label28:
            {
                itemStack1 = simplecontainer.getItem(i);
                if (!itemStack1.isEmpty()) {
                    item = itemStack1.getItem();
                    if (items.contains(item)) {
                        if (itemStack1.getCount() > itemStack1.getMaxStackSize() / 2) {
                            j = itemStack1.getCount() / 2;
                            break label28;
                        }

                        if (itemStack1.getCount() > 24) {
                            j = itemStack1.getCount() - 24;
                            break label28;
                        }
                    }
                }

                ++i;
                continue;
            }

            itemStack1.shrink(j);
            itemstack = new ItemStack(item, j);
            break;
        }

        if (!itemstack.isEmpty()) {
            BehaviorUtils.throwItem(villager, itemstack, entity.position());
        }

    }

}
