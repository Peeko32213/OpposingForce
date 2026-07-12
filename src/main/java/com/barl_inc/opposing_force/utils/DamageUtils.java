package com.barl_inc.opposing_force.utils;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.PartEntity;

public class DamageUtils {

    public static DamageSource genericDamage(Level level) {
        return level.damageSources().generic();
    }

    public static void safelyDealDamage(DamageSource damageSource, Entity target, float dmg) {
        safelyDealDamage(damageSource, target, dmg, true);
    }

    public static void safelyDealDamage(DamageSource damageSource, Entity target, float dmg, boolean ignoreMiscEntities) {
        if (!ignoreMiscEntities && !(target instanceof LivingEntity) && !(target instanceof PartEntity<?>) && !(target instanceof EndCrystal)) return;
        if (target.hurt(damageSource, dmg)) {
            if (damageSource.getEntity() instanceof LivingEntity attacker) attacker.setLastHurtMob(target);
        }
    }
}