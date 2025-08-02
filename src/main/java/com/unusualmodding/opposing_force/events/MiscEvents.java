package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.unusualmodding.opposing_force.effects.*;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class MiscEvents {

    @SubscribeEvent
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (event.getLookingEntity() != null) {
            double attributeValue = 0.0D;
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack stack = event.getEntity().getItemBySlot(equipmentSlot);
                Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(equipmentSlot).get(OPAttributes.STEALTH.get());
                if (!modifiers.isEmpty()) {
                    attributeValue += modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                }
            }
            if (attributeValue > 0.0D) {
                event.modifyVisibility(Math.max(1.0D - attributeValue, 0.0D));
            }
        }
    }

    @SubscribeEvent
    public static void onMobHurt(final LivingHurtEvent event) {
        for (MobEffectInstance activeEffect : event.getEntity().getActiveEffects()) {
            if (activeEffect.getEffect() == OPEffects.SLUG_INFESTATION.get()) {
                SlugInfestation.onMobHurt(event.getEntity(), activeEffect.getAmplifier());
            }
        }

        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();

        if (source.is(OPDamageTypes.ELECTRIFIED)) {
            float electricResistance = 0.0F;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack stack = target.getItemBySlot(slot);
                    Collection<AttributeModifier> electricResistance1 = stack.getAttributeModifiers(slot).get(OPAttributes.ELECTRIC_RESISTANCE.get());
                    if (!electricResistance1.isEmpty()) {
                        electricResistance += (float) electricResistance1.stream().mapToDouble(AttributeModifier::getAmount).sum();
                    }
                }
            }

            if (electricResistance > 0.0F) {
                event.setAmount(event.getAmount() - event.getAmount() * electricResistance);
            }
        }
    }
}
