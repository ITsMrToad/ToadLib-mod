package com.mr_toad.lib.api.entity.type;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.api.entity.type.records.Add2RabbitTypesRecord;
import net.minecraft.resources.ResourceLocation;

public class Add2RabbitTypes {

    public static synchronized Add2RabbitTypesRecord register(int id, ResourceLocation name, ResourceLocation texturePath) {
        Add2RabbitTypesRecord type = new Add2RabbitTypesRecord(id, name, texturePath);
        TypeSets.RABBIT_TYPES.add(type);
        return type;
    }



    public static synchronized Add2RabbitTypesRecord register(int id, ResourceLocation name) {
        return register(id, name, new ResourceLocation(name.getNamespace(), "textures/entity/rabbit/" + name.getPath() + ".png"));
    }

    public static ImmutableList<Add2RabbitTypesRecord> values() {
        return ImmutableList.copyOf(TypeSets.RABBIT_TYPES);
    }
}
