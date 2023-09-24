package com.mr_toad.lib.api.item.tab;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

public class FilledBlockItem extends BlockItem implements ICreativeTabFiller{

    public FilledBlockItem(Block block, Properties p) {
        super(block, p);
    }

    @Override
    public void fill(CreativeModeTab.Output output) {
        output.accept(this);
    }



}
