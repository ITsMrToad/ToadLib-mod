package com.mr_toad.lib.api.entity.immune;

import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.event.custom.RegisterImmuneEvent;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import org.jetbrains.annotations.ApiStatus;

public class EffectImmuneStorage {

    //For registry use "RegisterImmuneEvent"
    private static final Object2ObjectMap<EntityType<?>, MobEffect> TYPE_IMMUNE = new Object2ObjectOpenHashMap<>();

    @ApiStatus.Internal
    public static void init() {
        RegisterImmuneEvent.IMMUNE_REGISTRY.invoker().apply(TYPE_IMMUNE, TYPE_IMMUNE::put, TYPE_IMMUNE::remove);
        if (!TYPE_IMMUNE.isEmpty()) {
            ToadLib.LOGGER.info("Immune storage started.");
            ToadLib.LOGGER.info("Added immunities: '{}'", TYPE_IMMUNE.size());
        }
    }

    @ApiStatus.Internal
    public static boolean isInImmuneMap(LivingEntity type, MobEffectInstance effect) {
        MobEffect e = TYPE_IMMUNE.get(type.getType());
        if (e == null) {
            return false;
        } else {
            return e == effect.getEffect().value();
        }
    }
}
