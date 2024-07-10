package com.peeko32213.hole.core.events;


import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.common.entity.EntityRamble;
import com.peeko32213.hole.common.entity.EntityUmberSpider;
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

    }

}
