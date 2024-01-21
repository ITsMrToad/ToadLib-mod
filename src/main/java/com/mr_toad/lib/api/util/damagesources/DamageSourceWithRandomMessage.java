package com.mr_toad.lib.api.util;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;


@MethodsReturnNonnullByDefault
public class DamageSourceWithRandomMessage extends DamageSource {

    public final int messagesCount;

    public DamageSourceWithRandomMessage(Holder<DamageType> damageTypeHolder, @Nullable Entity entity1, @Nullable Entity entity2, @Nullable Vec3 from) {
        this(damageTypeHolder, entity1, entity2, from, 3);
    }
    
    public DamageSourceWithRandomMessage(Holder<DamageType> damageTypeHolder, @Nullable Entity entity1, @Nullable Entity entity2, @Nullable Vec3 from, int messagesCount) {
        super(damageTypeHolder, entity1, entity2, from);
        this.messagesCount = messagesCount;
    }

    public DamageSourceWithRandomMessage(Holder<DamageType> damageTypeHolder, @Nullable Entity entity1, @Nullable Entity entity2, int messagesCount) {
        super(damageTypeHolder, entity1, entity2);
        this.messagesCount = messagesCount;
    }

    public DamageSourceWithRandomMessage(Holder<DamageType> damageTypeHolder, Vec3 from, int messagesCount) {
        super(damageTypeHolder, from);
        this.messagesCount = messagesCount;
    }

    public DamageSourceWithRandomMessage(Holder<DamageType> damageTypeHolder, @Nullable Entity entity, int messagesCount) {
        super(damageTypeHolder, entity);
        this.messagesCount = messagesCount;
    }

    public DamageSourceWithRandomMessage(Holder<DamageType> damageTypeHolder, int messagesCount) {
        super(damageTypeHolder);
        this.messagesCount = messagesCount;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity attacked) {
        int type = attacked.getRandom().nextInt(this.getMessagesCount());
        LivingEntity livingentity = attacked.getKillCredit();
        String s = "toadlib.death.attack." + this.getMsgId() + "_" + type;
        String s1 = s + ".player";
        return livingentity != null ? Component.translatable(s1, attacked.getDisplayName(), livingentity.getDisplayName()) : Component.translatable(s, attacked.getDisplayName());
    }

    public int getMessagesCount() {
        return this.messagesCount;
    }
}
