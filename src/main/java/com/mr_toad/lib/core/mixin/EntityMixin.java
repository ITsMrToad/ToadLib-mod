package com.mr_toad.lib.core.mixin;

import com.mr_toad.lib.core.data.tag.ToadlyTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IForgeEntity {

    @Shadow public abstract void setSwimming(boolean aB);
    @Shadow public abstract boolean isSprinting();
    @Shadow public abstract boolean isInWater();
    @Shadow public abstract boolean isInFluidType();
    @Shadow public abstract boolean isPassenger();

    @Shadow public abstract EntityType<?> getType();

    @Inject(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSwimming(Z)V", ordinal = 0))
    private void canSwim(CallbackInfo ci) {
        this.setSwimming(this.isSprinting() && !this.getType().is(ToadlyTags.ToadlyEntityTypeTags.NON_SWIMMABLE_ENTITIES) && (this.isInWater() || this.isInFluidType((fluidType, height) -> this.canSwimInFluidType(fluidType))) && !this.isPassenger());
    }

}
