package com.peeko32213.hole.core.events;


import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.*;
import com.peeko32213.hole.core.registry.HoleEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Hole.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HoleEntities.PALE_SPIDER.get(), EntityPaleSpider.createAttributes().build());
        event.put(HoleEntities.UMBER_SPIDER.get(), EntityUmberSpider.createAttributes().build());
        event.put(HoleEntities.RAMBLE.get(), EntityRamble.createAttributes().build());
        event.put(HoleEntities.DICER.get(), EntityDicer.createAttributes().build());
        event.put(HoleEntities.TREMBLER.get(), EntityTrembler.createAttributes().build());

    }

}
