package com.mr_toad.lib.api.item.enums.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

public class FilledBlock extends Block implements ICreativeTabFiller {

    public FilledBlock(Properties p) {
        super(p);
    }

    @Override
    public void fill(CreativeModeTab.Output output) {
        output.accept(this);
    }
}
