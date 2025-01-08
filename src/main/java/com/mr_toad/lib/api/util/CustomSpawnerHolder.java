package com.mr_toad.lib.api.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;

public record CustomSpawnerHolder(CustomSpawner spawner, ResourceKey<Level> dim) {}
