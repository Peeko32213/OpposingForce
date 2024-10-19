package com.peeko32213.hole.client.event;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.client.model.DefaultModel;
import com.peeko32213.hole.client.model.SmallElectricBallModel;
import com.peeko32213.hole.client.model.TerrorDefaultModel;
import com.peeko32213.hole.client.render.SmallElectricBallRenderer;
import com.peeko32213.hole.client.render.TerrorRenderer;
import com.peeko32213.hole.client.render.layer.HoleGlowingEyeLayer;
import com.peeko32213.hole.client.render.PlainGeoRenderer;
import com.peeko32213.hole.common.entity.*;
import com.peeko32213.hole.core.registry.HoleBlocks;
import com.peeko32213.hole.core.registry.HoleEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
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

        EntityRenderers.register(HoleEntities.TERROR.get(), (ctx) -> {
            PlainGeoRenderer<EntityTerror> render = new PlainGeoRenderer<>(ctx, () -> new TerrorDefaultModel<>("terror"));
            render.addRenderLayer(new HoleGlowingEyeLayer<>("terror", render));
            return render;
        });

        EntityRenderers.register(HoleEntities.WIZZ.get(), (ctx) -> {
            PlainGeoRenderer<EntityWizz> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("wizz"));
            return render;
        });

        EntityRenderers.register(HoleEntities.HOPPER.get(), (ctx) -> {
            PlainGeoRenderer<EntityHopper> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("hopper"));
            return render;
        });

        EntityRenderers.register(HoleEntities.FROWZY.get(), (ctx) -> {
            PlainGeoRenderer<EntityFrowzy> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("frowzy"));
            return render;
        });



        ItemBlockRenderTypes.setRenderLayer(HoleBlocks.BLUE_TRUMPET.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HoleBlocks.CAVE_PATTY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HoleBlocks.CHICKEN_OF_THE_CAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HoleBlocks.POWDER_GNOME.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HoleBlocks.PRINCESS_JELLY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HoleBlocks.CREAM_CAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HoleBlocks.COPPER_ENOKI.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HoleBlocks.RAINCAP.get(), RenderType.cutout());

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HoleEntities.SMALL_ELECTRICITY_BALL.get(), SmallElectricBallRenderer::new);

    }

}