package com.unusualmodding.opposingforce.client.event;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.client.model.DefaultModel;
import com.unusualmodding.opposingforce.client.model.TerrorDefaultModel;
import com.unusualmodding.opposingforce.client.render.PlainGeoRenderer;
import com.unusualmodding.opposingforce.client.render.SmallElectricBallRenderer;
import com.unusualmodding.opposingforce.client.render.TomahawkRenderer;
import com.unusualmodding.opposingforce.client.render.layer.OPGlowingEyeLayer;
import com.unusualmodding.opposingforce.common.entity.custom.monster.*;
import com.unusualmodding.opposingforce.common.item.OPItemProperties;
import com.unusualmodding.opposingforce.core.registry.OPBlocks;
import com.unusualmodding.opposingforce.core.registry.OPEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = OpposingForce.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

    public static void init(FMLClientSetupEvent event) {
    }
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {

        EntityRenderers.register(OPEntities.PALE_SPIDER.get(), (ctx) -> {
            PlainGeoRenderer<PaleSpiderEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("pale_spider"));
            render.addRenderLayer(new OPGlowingEyeLayer<>("pale_spider", render));
            return render;
        });

        EntityRenderers.register(OPEntities.UMBER_SPIDER.get(), (ctx) -> {
            PlainGeoRenderer<UmberSpiderEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("umber_spider"));
            render.addRenderLayer(new OPGlowingEyeLayer<>("umber_spider", render));
            return render;
        });

        EntityRenderers.register(OPEntities.RAMBLE.get(), (ctx) -> {
            PlainGeoRenderer<RambleEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("ramble"));
            return render;
        });

        EntityRenderers.register(OPEntities.DICER.get(), (ctx) -> {
            PlainGeoRenderer<DicerEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("dicer"));
            render.addRenderLayer(new OPGlowingEyeLayer<>("dicer", render));
            return render;
        });

        EntityRenderers.register(OPEntities.TREMBLER.get(), (ctx) -> {
            PlainGeoRenderer<TremblerEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("trembler"));
            return render;
        });

        EntityRenderers.register(OPEntities.VOLT.get(), (ctx) -> {
            PlainGeoRenderer<VoltEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("volt"));
            return render;
        });

        EntityRenderers.register(OPEntities.TERROR.get(), (ctx) -> {
            PlainGeoRenderer<TerrorEntity> render = new PlainGeoRenderer<>(ctx, () -> new TerrorDefaultModel<>("terror"));
            render.addRenderLayer(new OPGlowingEyeLayer<>("terror", render));
            return render;
        });

        EntityRenderers.register(OPEntities.WIZZ.get(), (ctx) -> {
            PlainGeoRenderer<WizzEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("wizz"));
            return render;
        });

        EntityRenderers.register(OPEntities.HOPPER.get(), (ctx) -> {
            PlainGeoRenderer<HopperEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("hopper"));
            return render;
        });

        EntityRenderers.register(OPEntities.FROWZY.get(), (ctx) -> {
            PlainGeoRenderer<FrowzyEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("frowzy"));
            return render;
        });

        EntityRenderers.register(OPEntities.FIRE_SLIME.get(), (ctx) -> {
            PlainGeoRenderer<FireSlimeEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("fireslime"));
            return render;
        });

        EntityRenderers.register(OPEntities.GUZZLER.get(), (ctx) -> {
            PlainGeoRenderer<GuzzlerEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("guzzler"));
            return render;
        });

        EntityRenderers.register(OPEntities.SLUG_EGG.get(), (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });

        EntityRenderers.register(OPEntities.SLUG.get(), (ctx) -> {
            PlainGeoRenderer<SlugEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("slug"));
            return render;
        });

        EntityRenderers.register(OPEntities.FETID.get(), (ctx) -> {
            PlainGeoRenderer<FetidEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("fetid"));
            return render;
        });

        EntityRenderers.register(OPEntities.SPINDLE.get(), (ctx) -> {
            PlainGeoRenderer<SpindleEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("spindle"));
            return render;
        });

        ItemBlockRenderTypes.setRenderLayer(OPBlocks.BLUE_TRUMPET.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.CAVE_PATTY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.CHICKEN_OF_THE_CAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.POWDER_GNOME.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.PRINCESS_JELLY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.CREAM_CAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.COPPER_ENOKI.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.RAINCAP.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(OPBlocks.BLACKCAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.CAP_OF_EYE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.GREEN_FUNK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.LIME_NUB.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.POP_CAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.PURPLE_KNOB.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.QUEEN_IN_PURPLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.SLATESHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.SLIPPERY_TOP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.WHITECAP.get(), RenderType.cutout());
        event.enqueueWork(OPItemProperties::addItemProperties);

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(OPEntities.SMALL_ELECTRICITY_BALL.get(), SmallElectricBallRenderer::new);
        event.registerEntityRenderer(OPEntities.TOMAHAWK.get(), TomahawkRenderer::new);

    }

}