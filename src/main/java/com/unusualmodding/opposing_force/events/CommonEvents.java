package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.*;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(OPEntities.PALE_SPIDER.get(), PaleSpiderEntity.createAttributes().build());
        event.put(OPEntities.UMBER_SPIDER.get(), UmberSpiderEntity.createAttributes().build());
        event.put(OPEntities.RAMBLE.get(), RambleEntity.createAttributes().build());
        event.put(OPEntities.TREMBLER.get(), TremblerEntity.createAttributes().build());
        event.put(OPEntities.DICER.get(), DicerEntity.createAttributes().build());
        event.put(OPEntities.TERROR.get(), TerrorEntity.createAttributes().build());
        event.put(OPEntities.VOLT.get(), VoltEntity.createAttributes().build());
        event.put(OPEntities.WHIZZ.get(), WhizzEntity.createAttributes().build());
        event.put(OPEntities.FROWZY.get(), FrowzyEntity.createAttributes().build());
        event.put(OPEntities.GUZZLER.get(), GuzzlerEntity.createAttributes().build());
        event.put(OPEntities.FIRE_SLIME.get(), FireSlimeEntity.createAttributes().build());
        event.put(OPEntities.SLUG.get(), SlugEntity.createAttributes().build());
        event.put(OPEntities.EMERALDFISH.get(), EmeraldfishEntity.createAttributes().build());
    }
}
