package com.mr_toad.lib.data.tag;

import com.mr_toad.lib.api.util.ToadTagUtils;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ToadlyTags {

    public static class ToadlyItemTags {

        public static TagKey<Item> WIRIOS = itemTag("wirios");
        public static TagKey<Item> ORES = itemTag("ores");
        public static TagKey<Item> ENDER_FOOD = itemTag("ender_food");

        private static TagKey<Item> itemTag(String name) {
            return ToadTagUtils.createItem(ToadLib.MODID, name);
        }
    }

    public static class ToadlyBiomeTags {

        public static TagKey<Biome> IS_STORMWOOD_FOREST = biomeTag("is_stormwood_forest");
        public static TagKey<Biome> IS_OUTER_END = biomeTag("is_outer_end");

        public static TagKey<Biome> IS_PLAINSLIKE = biomeTag("is_plainslike");

        private static TagKey<Biome> biomeTag(String name) {
            return ToadTagUtils.createBiome(ToadLib.MODID, name);
        }
    }

    public static class ToadlyBlockTags {

        public static TagKey<Block> ORES = blockTag();

        private static TagKey<Block> blockTag() {
            return ToadTagUtils.createBlock(ToadLib.MODID, "ores");
        }
    }

    public static class ToadlyEntityTypeTags {

        public static TagKey<EntityType<?>> ORES = entityTag();

        private static TagKey<EntityType<?>> entityTag() {
            return ToadTagUtils.createEntityType(ToadLib.MODID, "ores");
        }
    }

}
