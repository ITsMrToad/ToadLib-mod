package com.mr_toad.lib.core.mixin;

import com.mr_toad.lib.core.data.ToadlyTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements IForgeEntity {

    @Shadow public abstract EntityType<?> getType();

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        if (!this.getType().is(ToadlyTags.ToadlyEntityTypeTags.NON_SWIMMABLE)) {
            return false;
        }
        return IForgeEntity.super.canSwimInFluidType(type);
    }
}
