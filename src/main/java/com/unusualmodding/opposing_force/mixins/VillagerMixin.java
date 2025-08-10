package com.unusualmodding.opposing_force.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(Villager.class)
public class VillagerMixin {

    @ModifyExpressionValue(method = "updateSpecialPrices", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;getPlayerReputation(Lnet/minecraft/world/entity/player/Player;)I"))
    private int increaseReputation(int original, Player player) {
        float reputation = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.VILLAGER_REPUTATION.get());
            if (!modifiers.isEmpty()) {
                reputation += (float) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
            }
        }

        if (reputation > 0) {
            original += (int) reputation;
        }
        return original;
    }
}
