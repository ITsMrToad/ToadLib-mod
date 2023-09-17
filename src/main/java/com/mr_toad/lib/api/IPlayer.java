   EndWaterBehaviors getBehavior();

    default boolean getEndWaterBehaviors(T entity, DamageSource endWaterSource) {
        switch (getBehavior()) {
            case HURT -> waterHurt(entity, endWaterSource);
            case REGEN -> waterHeal(entity);
        }

        return false;
    }

    default void waterAddEffect(T entity, DamageSource endWaterSource, MobEffect effect, int lvl, int ticks) {
        if (!entity.isInvulnerableTo(endWaterSource) && getBehavior() == EndWaterBehaviors.ADD_EFFECT) entity.addEffect(new MobEffectInstance(effect, lvl, ticks));
    }

    default void waterHurt(T entity, DamageSource endWaterSource) {
        if (!entity.isInvulnerableTo(endWaterSource)) entity.hurt(endWaterSource, 1.0F);
    }

    default void waterHeal(T entity) {
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0F);
        }
    }
