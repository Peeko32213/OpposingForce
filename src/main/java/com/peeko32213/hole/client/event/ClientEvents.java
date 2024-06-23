package com.peeko32213.hole.client.event;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.client.model.DefaultModel;
import com.peeko32213.hole.client.render.layer.HoleSpiderEyeLayer;
import com.peeko32213.hole.client.render.PlainGeoRenderer;
import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.common.entity.EntityUmberSpider;
import com.peeko32213.hole.core.registry.HoleEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Hole.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

    public static void init(FMLClientSetupEvent event) {
    }
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {

        EntityRenderers.register(HoleEntities.PALE_SPIDER.get(), (ctx) -> {
            PlainGeoRenderer<EntityPaleSpider> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("pale_spider"));
            render.addRenderLayer(new HoleSpiderEyeLayer<>("pale_spider", render));
            return render;
        });

        EntityRenderers.register(HoleEntities.UMBER_SPIDER.get(), (ctx) -> {
            PlainGeoRenderer<EntityUmberSpider> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("umber_spider"));
            render.addRenderLayer(new HoleSpiderEyeLayer<>("umber_spider", render));
            return render;
        });

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
    }

}