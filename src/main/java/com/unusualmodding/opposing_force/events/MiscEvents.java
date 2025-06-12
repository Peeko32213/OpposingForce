package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class MiscEvents {

//    @SubscribeEvent
//    public static void deepwovenHatEquippedEvent(LivingEquipmentChangeEvent event) {
//        if (event.getTo().is(OPItems.DEEPWOVEN_HELMET.get()) || event.getFrom().is(OPItems.DEEPWOVEN_HELMET.get())) {
//            if (event.getEntity() instanceof Player player) {
//                player.refreshDisplayName();
//            }
//        }
//    }

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
}
