package com.mr_toad.lib.core.mixin.access;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Set;

@Mixin(Parrot.class)
public interface ParrotAccessor {

    @Accessor("TAME_FOOD")
    static Set<Item> getTameFood() {
        throw new AssertionError("Mixin not apply");
    }

    @Accessor("MOB_SOUND_MAP")
    static Map<EntityType<?>, SoundEvent> getMobSoundMap() {
        throw new AssertionError("Mixin not apply");
    }
}
