package com.mr_toad.lib.api.util;

import com.mr_toad.lib.mtjava.floats.OptionalFloat;
import com.mr_toad.lib.mtjava.strings.OptionalString;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ToadOtherUtils {

    public static OptionalString itemName(Item item) {
        ResourceLocation rl = ForgeRegistries.ITEMS.getKey(item);
        if (rl == null) {
            return OptionalString.empty();
        }

        return OptionalString.of(rl.getPath());
    }

    public static OptionalString blockName(Block block) {
        ResourceLocation rl = ForgeRegistries.BLOCKS.getKey(block);
        if (rl == null) {
            return OptionalString.empty();
        }

        return OptionalString.of(rl.getPath());
    }

    public static ResourceLocation resourceBlock(String path, String modid) {
        return new ResourceLocation(modid, "block/" + path);
    }

    public static ResourceLocation resourceItem(String path, String modid) {
        return new ResourceLocation(modid, "item/" + path);
    }

    public static RegistryObject<SoundEvent> registerSounds(String name, String modid, DeferredRegister<SoundEvent> soundEventDeferredRegister, OptionalFloat range) {
        return soundEventDeferredRegister.register(name, () -> {
            if (range.isPresent()) {
                return SoundEvent.createFixedRangeEvent(new ResourceLocation(modid, name), range.getAsFloat());
            } else {
                return SoundEvent.createVariableRangeEvent(new ResourceLocation(modid, name));
            }
        });
    }
}

