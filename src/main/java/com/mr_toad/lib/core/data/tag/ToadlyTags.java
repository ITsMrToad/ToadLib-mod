package com.mr_toad.lib.core.data.tag;

import com.mr_toad.lib.api.util.ToadTagUtils;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;


public final class ToadlyTags {

    public static final class ToadlyItemTags {
        public static TagKey<@NotNull Item> WIRIOS = itemTag("wirios");
        public static TagKey<@NotNull Item> ORES = itemTag("ores");
        public static TagKey<@NotNull Item> ENDER_FOOD = itemTag("ender_food");

        private static TagKey<@NotNull Item> itemTag(String name) {
            return ToadTagUtils.ofItem(ToadLib.MODID, name);
        }
    }

    public static final class ToadlyBiomeTags {

        public static TagKey<@NotNull Biome> IS_OUTER_END = biomeTag("is_outer_end");
        public static TagKey<@NotNull Biome> IS_PLAINSLIKE = biomeTag("is_plainslike");

        private static TagKey<@NotNull Biome> biomeTag(String name) {
            return ToadTagUtils.ofBiome(ToadLib.MODID, name);
        }
    }

    public static final class ToadlyBlockTags {

        public static final TagKey<@NotNull Block> ORES = blockTag();
        private static TagKey<@NotNull Block> blockTag() {
            return ToadTagUtils.ofBlock(ToadLib.MODID, "ores");
        }
    }

    public static final class ToadlyEntityTypeTags {

        public static final TagKey<@NotNull EntityType<?>> ZOMBIES = entityTag("zombies");
        public static final TagKey<@NotNull EntityType<?>> NETHER_MOBS = entityTag("nether_mobs");
        public static final TagKey<@NotNull EntityType<?>> END_MOBS = entityTag("end_mobs");
        public static final TagKey<@NotNull EntityType<?>> NON_SWIMMABLE = entityTag("non_swimmable");
        public static final TagKey<@NotNull EntityType<?>> NON_NAMEABLE = entityTag("non_nameable");

        private static TagKey<@NotNull EntityType<?>> entityTag(String name) {
            return ToadTagUtils.ofEntityType(ToadLib.MODID, name);
        }
    }

}
