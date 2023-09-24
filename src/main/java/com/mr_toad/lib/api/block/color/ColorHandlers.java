package com.mr_toad.lib.api.block.color;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

@OnlyIn(Dist.CLIENT)
public class ColorHandlers {

    public static void registerItemColor(RegisterColorHandlersEvent.Item colorHandlersEvent, ItemColor color, Item... items) {
        colorHandlersEvent.register(color, items); //Colors block
    }

    public static void registerBlockColor(RegisterColorHandlersEvent.Block colorHandlersEvent, BlockColor color, Block... blocks) {
        colorHandlersEvent.register(color, blocks); //Colors item
    }

}
