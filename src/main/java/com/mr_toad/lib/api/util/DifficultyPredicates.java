package com.mr_toad.lib.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

///@deprecated use {@link }
@Deprecated(since = "1.4.5")
public class DifficultyPredicates {

    public static final Predicate<Difficulty> HARD_PREDICATE = (difficulty) -> difficulty == Difficulty.HARD;

    public static boolean isHard(Level world) {
        return HARD_PREDICATE.test(world.getDifficulty());
    }

    public static boolean isHarderThanNormal(ServerLevel world, BlockPos pos) {
        return world.getCurrentDifficultyAt(pos).isHarderThan((float)Difficulty.NORMAL.ordinal());
    }

    public static boolean isPeaceful(Level level) {
        return level.getDifficulty() == Difficulty.PEACEFUL;
    }

}
