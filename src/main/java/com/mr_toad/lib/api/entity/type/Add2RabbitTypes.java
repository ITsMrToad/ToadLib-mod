package com.mr_toad.lib.api.entity.type;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.api.entity.type.records.Add2RabbitTypesRecord;
import net.minecraft.resources.Identifier;

import org.jetbrains.annotations.NotNull;

public class Add2RabbitTypes {

    public static synchronized Add2RabbitTypesRecord register(int id, Identifier name, Identifier texturePath) {
        Add2RabbitTypesRecord type = new Add2RabbitTypesRecord(id, name, texturePath);
        TypeSets.RABBIT_TYPES.add(type);
        return type;
    }

    public static synchronized Add2RabbitTypesRecord register(int id, Identifier name) {
        return register(id, name, Identifier.fromNamespaceAndPath(name.getNamespace(), "textures/entity/rabbit/" + name.getPath() + ".png"));
    }

    public static ImmutableList<@NotNull Add2RabbitTypesRecord> values() {
        return ImmutableList.copyOf(TypeSets.RABBIT_TYPES);
    }
}
