package com.mr_toad.lib.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class DifficultyPredicates {

    public static final Predicate<Difficulty> HARD_PREDICATE = (difficulty) -> difficulty == Difficulty.HARD;

    public static boolean isHard(Level lvl) {
        return HARD_PREDICATE.test(lvl.getDifficulty());
    }

    public static boolean isHarderThanNormal(Level lvl, BlockPos pos) {
        return lvl.getCurrentDifficultyAt(pos).isHarderThan((float)Difficulty.NORMAL.ordinal());
    }

    public static boolean isPeaceful(Level level) {
        return level.getDifficulty() == Difficulty.PEACEFUL;
    }
}
