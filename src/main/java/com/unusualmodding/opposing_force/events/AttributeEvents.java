package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEffects;
import com.unusualmodding.opposing_force.registry.tags.OPDamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.unusualmodding.opposing_force.effects.*;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class AttributeEvents {

    @SubscribeEvent
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (event.getLookingEntity() != null) {
            double attributeValue = 0.0D;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = event.getEntity().getItemBySlot(slot);
                Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.STEALTH.get());
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
                ItemStack stack = target.getItemBySlot(slot);
                Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.ELECTRIC_RESISTANCE.get());
                if (!modifiers.isEmpty()) {
                    electricResistance += (float) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                }
            }

            if (electricResistance > 0.0F) {
                event.setAmount(event.getAmount() - event.getAmount() * electricResistance);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();

        float jumpPower = 0.0F;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.JUMP_POWER.get());
            if (!modifiers.isEmpty()) {
                jumpPower += (float) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
            }
        }

        float jumpPowerModifier = jumpPower / 10;
        if (jumpPower > 0.0F) {
            entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getDeltaMovement().y() + jumpPowerModifier, entity.getDeltaMovement().z());
        }
    }

    @SubscribeEvent
    public static void increaseXpGained(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.EXPERIENCE_GAIN.get());
            if (!modifiers.isEmpty()) {
                float experienceBoost = event.getAmount() * (float) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                int base = Mth.floor(experienceBoost);
                float bonus = Mth.frac(experienceBoost);
                if (bonus != 0.0F && Math.random() < bonus) {
                    ++base;
                }
                event.setAmount(event.getAmount() + base);
            }
        }
    }
}
