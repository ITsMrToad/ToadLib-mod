package com.mr_toad.lib.api.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Set;

@ParametersAreNonnullByDefault
public abstract class AbstractTradeBehavior extends Behavior<Villager> {

    public Set<Item> trades = ImmutableSet.of();

    public AbstractTradeBehavior() {
        super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Villager v) {
        return BehaviorUtils.targetIsValid(v.getBrain(), MemoryModuleType.INTERACTION_TARGET, EntityType.VILLAGER);
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Villager v, long l0) {
        return this.checkExtraStartConditions(serverLevel, v);
    }

    @Override
    protected void start(ServerLevel s, Villager vi, long l0) {
        Villager villager = (Villager) vi.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        BehaviorUtils.lockGazeAndWalkToEachOther(vi, villager, 0.5F);

        this.trades = figureOutWhatIAmWillingToTrade(vi, villager);
    }

    @Override
    protected void stop(ServerLevel serverLevel, Villager villager, long l0) {
        villager.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
    }

    @Override
    protected abstract void tick(ServerLevel serverLevel, Villager villager, long l0);

    public abstract Set<Item> figureOutWhatIAmWillingToTrade(Villager villager, Villager villager02);

    
}
