package com.mr_toad.lib.api.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

public record SpawnerHolder(CustomSpawner spawner, ResourceKey<@NotNull Level> dim) {}
