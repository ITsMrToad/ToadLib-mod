package com.mr_toad.lib.api.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ToadDataUtils {

    private String itemName(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    private String itemName(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    public ResourceLocation resourceBlock(String path, String modid) {
        return new ResourceLocation(modid, "block/" + path);
    }

    public ResourceLocation resourceItem(String path, String modid) {
        return new ResourceLocation(modid, "item/" + path);
    }

    public String advName(String name, String modid) {
        return modid + ":" + name;
    }

    public static RegistryObject<SoundEvent> registerSounds(String name, String modid, DeferredRegister<SoundEvent> soundEventDeferredRegister) {
        return soundEventDeferredRegister.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(modid, name)));
    }

    public void blockBasedModel(Item item, String suffix) {
        withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
    }


    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), "item/generated").texture("layer0", texture);
    }

    private String itemName(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(VillageUpgrade.MODID, "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(VillageUpgrade.MODID, "item/" + path);
    }
}
