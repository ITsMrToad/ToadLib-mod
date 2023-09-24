package com.mr_toad.lib.api.item.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class FilledItem extends Item implements ICreativeTabFiller {

    public FilledItem(Properties p) {
        super(p);
    }

    @Override
    public void fill(CreativeModeTab.Output output) {
        output.accept(this);
    }


}
