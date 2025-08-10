package com.unusualmodding.opposing_force.entity.utils;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;

public class DamageUtil {

    public static DamageSource genericDamage(Level level) {
        return level.damageSources().generic();
    }

    public static boolean safelyDealDamage(DamageSource damageSource, Entity target, float dmg) {
        return safelyDealDamage(damageSource, target, dmg, true);
    }

    public static boolean safelyDealDamage(DamageSource damageSource, Entity target, float dmg, boolean ignoreMiscEntities) {
        if (!ignoreMiscEntities && !(target instanceof LivingEntity) && !(target instanceof PartEntity<?>) && !(target instanceof EndCrystal))
            return false;

        if (target.hurt(damageSource, dmg)) {
            if (damageSource.getEntity() instanceof LivingEntity attacker)
                attacker.setLastHurtMob(target);

            return true;
        }

        return false;
    }
}