package com.mr_toad.lib.data.tag;

import com.mr_toad.lib.api.util.ToadTagUtils;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public final class ToadlyTags {

    public static final class ToadlyItemTags {
        public static TagKey<Item> WIRIOS = itemTag("wirios");
        public static TagKey<Item> ORES = itemTag("ores");
        public static TagKey<Item> ENDER_FOOD = itemTag("ender_food");

        private static TagKey<Item> itemTag(String name) {
            return ToadTagUtils.createItem(ToadLib.MODID, name);
        }
    }

    public static final class ToadlyBiomeTags {

        public static TagKey<Biome> IS_OUTER_END = biomeTag("is_outer_end");
        public static TagKey<Biome> IS_PLAINSLIKE = biomeTag("is_plainslike");

        private static TagKey<Biome> biomeTag(String name) {
            return ToadTagUtils.createBiome(ToadLib.MODID, name);
        }
    }

    public static final class ToadlyBlockTags {

        public static final TagKey<Block> ORES = blockTag();
        private static TagKey<Block> blockTag() {
            return ToadTagUtils.createBlock(ToadLib.MODID, "ores");
        }
    }
    
    public static final class ToadlyEntityTypeTags {

        public static final TagKey<EntityType<?>> ZOMBIES = entityTag("zombies");

        public static final TagKey<EntityType<?>> NETHER_MOBS = entityTag("nether_mobs");
        public static final TagKey<EntityType<?>> END_MOBS = entityTag("end_mobs");

        private static TagKey<EntityType<?>> entityTag(String name) {
            return ToadTagUtils.createEntityType(ToadLib.MODID, name);
        }
    }
