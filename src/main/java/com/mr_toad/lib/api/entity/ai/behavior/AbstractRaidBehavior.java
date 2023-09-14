package com.mr_toad.lib.api.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ParametersAreNonnullByDefault
public abstract class AbstractRaidBehavior <T extends Mob> extends Behavior<T> {

    public final List<Raider> raiders;
    public boolean isActive;

    public AbstractRaidBehavior(Map<MemoryModuleType<?>, MemoryStatus> map, Level lvl, T mob) {
        super(map);

        raiders = lvl.getEntitiesOfClass(Raider.class, mob.getBoundingBox());

        for (Raider raider : raiders) {
            isActive = Objects.requireNonNull(raider.getCurrentRaid()).isActive();
        }

    }

    public List<Raider> getRaiders() {
        return raiders;
    }

    @Override
    protected abstract boolean checkExtraStartConditions(ServerLevel serverLevel, T mob);

    @Override
    protected abstract boolean canStillUse(ServerLevel serverLevel, T mob, long l0);

    @Override
    public abstract void start(ServerLevel serverLevel, T mob, long l0);

    @Override
    public abstract void stop(ServerLevel serverLevel, T mob, long l0);

    @Override
    public abstract void tick(ServerLevel serverLevel, T mob, long l0);


}





