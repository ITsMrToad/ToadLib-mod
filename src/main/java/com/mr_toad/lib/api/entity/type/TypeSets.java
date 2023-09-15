package com.mr_toad.lib.api.entity.type;

import com.mr_toad.lib.api.entity.type.records.Add2HorseTypesRecord;
import com.mr_toad.lib.api.entity.type.records.Add2ParrotTypesRecord;
import com.mr_toad.lib.api.entity.type.records.Add2RabbitTypesRecord;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;

import java.util.Set;

public class TypeSets {
    public static final Set<Add2HorseTypesRecord> HORSE_TYPES = new ObjectArraySet<>();
    public static final Set<Add2RabbitTypesRecord> RABBIT_TYPES = new ObjectArraySet<>();
    public static final Set<Add2ParrotTypesRecord> PARROT_TYPES = new ObjectArraySet<>();
}
