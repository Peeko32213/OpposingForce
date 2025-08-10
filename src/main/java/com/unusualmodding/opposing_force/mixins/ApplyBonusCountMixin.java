package com.unusualmodding.opposing_force.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {

    @ModifyExpressionValue(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/item/ItemStack;)I"))
    private int increaseFortuneLevel(int i, ItemStack stack, LootContext context) {
        ItemStack itemstack = context.getParamOrNull(LootContextParams.TOOL);
        int fortune = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (itemstack != null) {
                Collection<AttributeModifier> modifiers = itemstack.getAttributeModifiers(slot).get(OPAttributes.FORTUNE.get());
                if (!modifiers.isEmpty()) {
                    fortune += (int) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                }
            }
        }

        if (fortune > 0) {
            i += fortune;
        }
        return i;
    }
}
