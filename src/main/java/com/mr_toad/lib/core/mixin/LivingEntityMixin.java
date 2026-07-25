package com.mr_toad.lib.core.mixin;

import com.mr_toad.lib.api.entity.immune.EffectImmuneStorage;
import com.mr_toad.lib.event.ToadEventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {

    protected LivingEntityMixin(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
    public void checkImmune(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (EffectImmuneStorage.isInImmuneMap((LivingEntity) (Object) this, effect)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "dropAllDeathLoot", at = @At("TAIL"))
    protected void eventDrop(ServerLevel serverLevel, DamageSource damageSource, CallbackInfo ci) {
        ToadEventFactory.onCustomDeathLoot((LivingEntity) (Object) this, serverLevel, damageSource);
    }
}


