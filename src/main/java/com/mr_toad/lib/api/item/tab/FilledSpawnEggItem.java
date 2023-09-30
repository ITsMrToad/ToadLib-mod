package com.mr_toad.lib.api.item.tab;

import com.mr_toad.lib.api.item.enums.tab.ICreativeTabFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class FilledSpawnEggItem extends ForgeSpawnEggItem implements ICreativeTabFiller {

    public FilledSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void fill(CreativeModeTab.Output output) {
        output.accept(this);
    }
}
