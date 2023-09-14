package com.mr_toad.lib.api.entity.type;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.api.entity.type.records.Add2HorseTypesRecord;
import net.minecraft.resources.ResourceLocation;

public class Add2HorseTypes {

    public static synchronized Add2HorseTypesRecord register(int id, ResourceLocation name, ResourceLocation texturePath) {
        Add2HorseTypesRecord type = new Add2HorseTypesRecord(id, name, texturePath);
        TypeSets.HORSE_TYPES.add(type);
        return type;
    }

    public static synchronized Add2HorseTypesRecord register(int id, ResourceLocation name) {
        return register(id, name, new ResourceLocation(name.getNamespace(), "textures/entity/horse/" + name.getPath() + ".png"));
    }

    public static ImmutableList<Add2HorseTypesRecord> values() {
        return ImmutableList.copyOf(TypeSets.HORSE_TYPES);
    }

}
