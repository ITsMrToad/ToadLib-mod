package com.mr_toad.lib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.Set;

public class BehaviorHelper {

    public static boolean isWithinDistance(Mob mob, LivingEntity target, double distance) {
        BlockPos blockPos = target.blockPosition();
        BlockPos blockPos1 = mob.blockPosition();
        return blockPos1.closerThan(blockPos, distance);
    }

    public static boolean tickConditionsInvolved(ServerLevel serverLevel, LivingEntity owner, long l0) {
        return BehaviorHelper.tickConditionsInvolved(serverLevel, owner, l0, 5.0F, 0.5F);
    }

    public static boolean tickConditionsInvolved(ServerLevel serverLevel, LivingEntity owner, long l0, float dist, float speed) {
        Optional<LivingEntity> optional = owner.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET);
        if (optional.isEmpty()) {
            return false;
        } else {
            LivingEntity entity = optional.get();
            if (!(owner.distanceToSqr(entity) > dist)) {
                BehaviorUtils.lockGazeAndWalkToEachOther(owner, entity, speed);
                if (entity instanceof Villager villager) {
                    if (owner instanceof Villager ov) {
                        ov.gossip(serverLevel, villager, l0);
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    public static<E extends LivingEntity & InventoryCarrier> void throwQuarterStack(E entity, Set<Item> items, Vec3 vec) {
        BehaviorHelper.throwCounted(entity, items, vec, 4);
    }

    public static<E extends LivingEntity & InventoryCarrier> void throwHalfStack(E entity, Set<Item> items, Vec3 vec) {
        BehaviorHelper.throwCounted(entity, items, vec, 2);
    }

    public static<E extends LivingEntity & InventoryCarrier> void throwCounted(E entity, Set<Item> items, Vec3 vec, int divider) {
        SimpleContainer simplecontainer = entity.getInventory();
        ItemStack itemstack = ItemStack.EMPTY;
        int i = 0;

        while (i < simplecontainer.getContainerSize()) {
            ItemStack itemStack1;
            Item item;
            int j;
            label28:{
                itemStack1 = simplecontainer.getItem(i);
                if (!itemStack1.isEmpty()) {
                    item = itemStack1.getItem();
                    if (items.contains(item)) {
                        if (itemStack1.getCount() > itemStack1.getMaxStackSize() / 2) {
                            j = itemStack1.getCount() / divider;
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
            BehaviorUtils.throwItem(entity, itemstack, vec);
        }

    }

}
