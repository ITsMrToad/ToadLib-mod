package com.mr_toad.lib.api.util;

import com.mr_toad.lib.mtjava.floats.OptionalFloat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ToadOtherUtils {

    ///@deprecated
    ///Use {@link #itemKey(net.minecraft.world.item.Item)}
    @Deprecated(since = "1.4.5")
    public static String itemName(Item item) {
        Identifier rl = BuiltInRegistries.ITEM.getKey(item);
        return rl.getPath();
    }

    ///@deprecated
    ///Duplicate of {@link #itemName(net.minecraft.world.item.Item)}, WTF
    ///Use {@link #blockKey(net.minecraft.world.level.block.Block)}
    @Deprecated(since = "1.4.5")
    public static String blockName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public static Identifier itemKey(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static Identifier blockKey(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public static Identifier resourceBlock(String path, String modid) {
        return Identifier.fromNamespaceAndPath(modid, "block/" + path);
    }

    public static Identifier resourceItem(String path, String modid) {
        return Identifier.fromNamespaceAndPath(modid, "item/" + path);
    }

    public static SoundEvent registerSounds(String name, String modid, OptionalFloat range) {
        SoundEvent entry = range.isPresent() ? SoundEvent.createFixedRangeEvent(Identifier.fromNamespaceAndPath(modid, name), range.getAsFloat()) : SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(modid, name));
        return Registry.register(BuiltInRegistries.SOUND_EVENT, name, entry);
    }
}
