package com.mr_toad.lib.api.enums;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("deprecation")
public class ToadlyArmorMaterial implements ArmorMaterial {

    private final int[] slotProtections;
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

    private final int durabilityMultiplier;
    private final int enchantmentValue;
    private final float toughness;
    private final float knockbackResistance;

    private final Supplier<SoundEvent> sound;
    private final ResourceLocation name;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    public ToadlyArmorMaterial(ResourceLocation name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, Supplier<SoundEvent> sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_PER_SLOT[type.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.slotProtections[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound.get();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name.toString();
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }


}
