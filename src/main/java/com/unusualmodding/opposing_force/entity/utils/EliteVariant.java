package com.unusualmodding.opposing_force.entity.utils;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public interface EliteVariant {

    boolean isElite();

    void setElite(boolean elite);

    default int getEliteSpawnChance() {
        return 5;
    }

    default void setEliteStats(Mob entity) {
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getAttributeBaseValue(Attributes.MAX_HEALTH) * 1.5F);
        entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(entity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * 1.25F);
        entity.setHealth(entity.getMaxHealth());
    }
}
