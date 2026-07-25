package com.mr_toad.lib.api.entity.type;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.api.entity.type.records.Add2HorseTypesRecord;
import net.minecraft.resources.Identifier;

import org.jetbrains.annotations.NotNull;

public class Add2HorseTypes {

    public static synchronized Add2HorseTypesRecord register(int id, Identifier name, Identifier texturePath) {
        Add2HorseTypesRecord type = new Add2HorseTypesRecord(id, name, texturePath);
        TypeSets.HORSE_TYPES.add(type);
        return type;
    }

    public static synchronized Add2HorseTypesRecord register(int id, Identifier name) {
        return register(id, name, Identifier.fromNamespaceAndPath(name.getNamespace(), "textures/entity/horse/" + name.getPath() + ".png"));
    }

    public static ImmutableList<@NotNull Add2HorseTypesRecord> values() {
        return ImmutableList.copyOf(TypeSets.HORSE_TYPES);
    }

}
