package com.mr_toad.lib.api.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ToadOuterUtils {

    public static String itemName(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    public static String itemName(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    public static ResourceLocation resourceBlock(String path, String modid) {
        return new ResourceLocation(modid, "block/" + path);
    }

    public static ResourceLocation resourceItem(String path, String modid) {
        return new ResourceLocation(modid, "item/" + path);
    }

    public static RegistryObject<SoundEvent> registerSounds(String name, String modid, DeferredRegister<SoundEvent> soundEventDeferredRegister) {
        return soundEventDeferredRegister.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(modid, name)));
    }


}
