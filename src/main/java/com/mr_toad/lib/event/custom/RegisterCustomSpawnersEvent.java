package com.mr_toad.lib.api.event.custom;

import com.google.common.collect.Lists;
import com.mr_toad.lib.api.util.CustomSpawnerHolder;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public final class RegisterCustomSpawnersEvent extends Event {

    private final List<CustomSpawnerHolder> list = Lists.newArrayList();

    public RegisterCustomSpawnersEvent() {}

    public<S extends CustomSpawnerHolder> void register(S spawner) {
        this.list.add(spawner);
    }

    public List<CustomSpawnerHolder> getSpawners() {
        return this.list;
    }
}
