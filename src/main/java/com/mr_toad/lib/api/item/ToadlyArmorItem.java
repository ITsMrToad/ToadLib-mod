package com.mr_toad.lib.api.item;

import com.google.common.collect.Maps;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public interface ToadlyArmorItem {

    HashMap<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP = Maps.newHashMap();

    default void put(ArmorMaterial material, MobEffectInstance effectInstance) {
        if (material != null && effectInstance != null) {
            MATERIAL_TO_EFFECT_MAP.put(material, effectInstance);
        } else ToadLib.LOGGER.error("Material & Effect cannot be null");
    }

    default void evaluateArmorEffects(Player player) {
        if (!MATERIAL_TO_EFFECT_MAP.isEmpty()) {
            for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
                ArmorMaterial mapArmorMaterial = entry.getKey();
                MobEffectInstance mapStatusEffect = entry.getValue();

                if (this.hasCorrectArmorOn(mapArmorMaterial, player)) {
                    this.addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }
        }
    }
    default boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armorStack: player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
        ArmorItem chestplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && chestplate.getMaterial() == material && leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    default void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

        if(this.hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(mapStatusEffect.getEffect(), mapStatusEffect.getDuration(), mapStatusEffect.getAmplifier()));
        }
    }
}
