package com.unusualmodding.opposing_force.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @ModifyReturnValue(method = "getMobLooting", at = @At("RETURN"))
    private static int increaseLootingLevel(int original, LivingEntity entity) {
        int looting = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.LOOTING.get());
            if (!modifiers.isEmpty()) {
                looting += (int) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
            }
        }

        if (looting > 0) {
            return original + looting;
        } else {
            return original;
        }
    }
}
