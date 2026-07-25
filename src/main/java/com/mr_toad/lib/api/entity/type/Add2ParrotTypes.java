package com.mr_toad.lib.api.entity.type;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.api.entity.type.records.Add2ParrotTypesRecord;
import net.minecraft.resources.Identifier;

public class Add2ParrotTypes {

    public static synchronized Add2ParrotTypesRecord register(int id, Identifier name, Identifier texturePath) {
        Add2ParrotTypesRecord type = new Add2ParrotTypesRecord(id, name, texturePath);
        TypeSets.PARROT_TYPES.add(type);
        return type;
    }

    public static synchronized Add2ParrotTypesRecord register(int id, Identifier name) {
        return register(id, name, Identifier.fromNamespaceAndPath(name.getNamespace(), "textures/entity/parrot/" + name.getPath() + ".png"));
    }

    public static ImmutableList<Add2ParrotTypesRecord> values() {
        return ImmutableList.copyOf(TypeSets.PARROT_TYPES);
    }
}
