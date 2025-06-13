package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientForgeEvents {
    @SubscribeEvent
    public static void renderNameplate(RenderNameTagEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == OPItems.DEEPWOVEN_HELMET.get()) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
