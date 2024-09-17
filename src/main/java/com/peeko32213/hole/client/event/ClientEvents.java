package com.peeko32213.hole.client.event;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.client.model.DefaultModel;
import com.peeko32213.hole.client.model.SmallElectricBallModel;
import com.peeko32213.hole.client.render.SmallElectricBallRenderer;
import com.peeko32213.hole.client.render.TerrorRenderer;
import com.peeko32213.hole.client.render.layer.HoleGlowingEyeLayer;
import com.peeko32213.hole.client.render.PlainGeoRenderer;
import com.peeko32213.hole.common.entity.*;
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
            render.addRenderLayer(new HoleGlowingEyeLayer<>("pale_spider", render));
            return render;
        });

        EntityRenderers.register(HoleEntities.UMBER_SPIDER.get(), (ctx) -> {
            PlainGeoRenderer<EntityUmberSpider> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("umber_spider"));
            render.addRenderLayer(new HoleGlowingEyeLayer<>("umber_spider", render));
            return render;
        });

        EntityRenderers.register(HoleEntities.RAMBLE.get(), (ctx) -> {
            PlainGeoRenderer<EntityRamble> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("ramble"));
            return render;
        });

        EntityRenderers.register(HoleEntities.DICER.get(), (ctx) -> {
            PlainGeoRenderer<EntityDicer> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("dicer"));
            render.addRenderLayer(new HoleGlowingEyeLayer<>("dicer", render));
            return render;
        });

        EntityRenderers.register(HoleEntities.TREMBLER.get(), (ctx) -> {
            PlainGeoRenderer<EntityTrembler> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("trembler"));
            return render;
        });

        EntityRenderers.register(HoleEntities.VOLT.get(), (ctx) -> {
            PlainGeoRenderer<EntityVolt> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("volt"));
            return render;
        });

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HoleEntities.TERROR.get(), TerrorRenderer::new);
        event.registerEntityRenderer(HoleEntities.SMALL_ELECTRICITY_BALL.get(), SmallElectricBallRenderer::new);

    }

}