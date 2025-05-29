package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.DefaultModel;
import com.unusualmodding.opposing_force.client.models.entity.*;
import com.unusualmodding.opposing_force.client.particles.*;
import com.unusualmodding.opposing_force.client.renderer.PlainGeoRenderer;
import com.unusualmodding.opposing_force.client.renderer.*;
import com.unusualmodding.opposing_force.client.renderer.layer.OPGlowingEyeLayer;
import com.unusualmodding.opposing_force.entity.*;
import com.unusualmodding.opposing_force.items.OPItemProperties;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import com.unusualmodding.opposing_force.registry.OPParticles;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

    public static void init(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegisterParticleProvidersEvent event){
        event.registerSpecial(OPParticles.ELECTRIC_ORB.get(), new ElectricBallParticle.ElectricOrbFactory());
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {

        EntityRenderers.register(OPEntities.PALE_SPIDER.get(), (ctx) -> {
            PlainGeoRenderer<PaleSpiderEntity> render = new PlainGeoRenderer<>(ctx, () -> new DefaultModel<>("pale_spider"));
            render.addRenderLayer(new OPGlowingEyeLayer<>("pale_spider", render));
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

        EntityRenderers.register(OPEntities.BOUNCER.get(), (ctx) -> {
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

        EntityRenderers.register(OPEntities.SLUG_EGG.get(), (render) -> new ThrownItemRenderer<>(render, 0.75F, true));

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
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.QUEEN_IN_MAGENTA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.SLATESHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.SLIPPERY_TOP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(OPBlocks.WHITECAP.get(), RenderType.cutout());
        event.enqueueWork(OPItemProperties::addItemProperties);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(OPEntities.ELECTRICITY_BALL.get(), ElectricBallRenderer::new);
        event.registerEntityRenderer(OPEntities.TOMAHAWK.get(), TomahawkRenderer::new);
        event.registerEntityRenderer(OPEntities.EMERALDFISH.get(), EmeraldfishRenderer::new);
        event.registerEntityRenderer(OPEntities.SLUG.get(), SlugRenderer::new);
        event.registerEntityRenderer(OPEntities.TERROR.get(), TerrorRenderer::new);
        event.registerEntityRenderer(OPEntities.TREMBLER.get(), TremblerRenderer::new);
        event.registerEntityRenderer(OPEntities.UMBER_SPIDER.get(), UmberSpiderRenderer::new);
        event.registerEntityRenderer(OPEntities.VOLT.get(), VoltRenderer::new);
        event.registerEntityRenderer(OPEntities.WHIZZ.get(), WhizzRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OPModelLayers.EMERALDFISH_LAYER, EmeraldfishModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.SLUG_LAYER, SlugModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.TERROR_LAYER, TerrorModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.TREMBLER_LAYER, TremblerModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.UMBER_SPIDER_LAYER, UmberSpiderModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.VOLT_LAYER, VoltModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.WHIZZ_LAYER, WhizzModel::createBodyLayer);
    }
}