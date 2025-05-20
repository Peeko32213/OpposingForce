package com.unusualmodding.opposing_force.enchantments;

import com.unusualmodding.opposing_force.registry.OPEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

public class OPEnchantmentLogic {
    public static void kickbackLogic (LivingEntity entity, ItemStack stack) {
        int kickback = EnchantmentHelper.getItemEnchantmentLevel(OPEnchantments.KICKBACK.get(), stack);
        if (kickback > 0) {
            Vec3 lookVec = entity.getLookAngle().multiply(-1,-1,-1);
            entity.setDeltaMovement(new Vec3(0.5 + (kickback * 0.5), 0.5 + (kickback * 0.25), 0.5 + (kickback * 0.5)).multiply(lookVec));
        }
    }
}
