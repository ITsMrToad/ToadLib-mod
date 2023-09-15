package com.mr_toad.lib.api.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Collections;

public class ToadEntityUtils {

    public static void addParrotImitation(EntityType<?> entityType, SoundEvent soundEvent) {
        Parrot.MOB_SOUND_MAP.put(entityType, soundEvent);
    }

    public static void addParrotFood(Item... items) {
        Collections.addAll(Parrot.TAME_FOOD, items);
    }
}
