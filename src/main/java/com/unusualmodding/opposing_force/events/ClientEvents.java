package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.armor.*;
import com.unusualmodding.opposing_force.client.models.entity.*;
import com.unusualmodding.opposing_force.client.models.mob_heads.*;
import com.unusualmodding.opposing_force.client.particles.*;
import com.unusualmodding.opposing_force.client.renderer.*;
import com.unusualmodding.opposing_force.client.renderer.blocks.*;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(OPItemProperties::addItemProperties);
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegisterParticleProvidersEvent event){
        event.registerSpecial(OPParticles.ELECTRIC_ORB.get(), new ElectricChargeParticle.ElectricOrbFactory());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(OPEntities.DICER.get(), DicerRenderer::new);
        event.registerEntityRenderer(OPEntities.DICER_LASER.get(), DicerLaserRenderer::new);
        event.registerEntityRenderer(OPEntities.ELECTRIC_CHARGE.get(), ElectricBallRenderer::new);
        event.registerEntityRenderer(OPEntities.EMERALDFISH.get(), EmeraldfishRenderer::new);
        event.registerEntityRenderer(OPEntities.FIRE_SLIME.get(), FireSlimeRenderer::new);
        event.registerEntityRenderer(OPEntities.FROWZY.get(), FrowzyRenderer::new);
        event.registerEntityRenderer(OPEntities.GUZZLER.get(), GuzzlerRenderer::new);
        event.registerEntityRenderer(OPEntities.HAUNTED_TOOL.get(), HauntedToolRenderer::new);
        event.registerEntityRenderer(OPEntities.PALE_SPIDER.get(), PaleSpiderRenderer::new);
        event.registerEntityRenderer(OPEntities.RAMBLE.get(), RambleRenderer::new);
        event.registerEntityRenderer(OPEntities.SLUG.get(), SlugRenderer::new);
        event.registerEntityRenderer(OPEntities.SLUG_EGGS.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(OPEntities.TERROR.get(), TerrorRenderer::new);
        event.registerEntityRenderer(OPEntities.TOMAHAWK.get(), TomahawkRenderer::new);
        event.registerEntityRenderer(OPEntities.TREMBLER.get(), TremblerRenderer::new);
        event.registerEntityRenderer(OPEntities.UMBER_SPIDER.get(), UmberSpiderRenderer::new);
        event.registerEntityRenderer(OPEntities.VOLT.get(), VoltRenderer::new);
        event.registerEntityRenderer(OPEntities.WHIZZ.get(), WhizzRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OPModelLayers.DICER, DicerModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.DICER_HEAD, DicerHeadModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.EMERALDFISH, EmeraldfishModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.FIRE_SLIME, FireSlimeModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.FROWZY, FrowzyModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.FROWZY_HEAD, FrowzyHeadModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.GUZZLER, GuzzlerModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.PALE_SPIDER, PaleSpiderModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.RAMBLE, RambleModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.RAMBLE_SKULL, RambleSkullModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.SLUG, SlugModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.TERROR, TerrorModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.TREMBLER, TremblerModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.UMBER_SPIDER, UmberSpiderModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.VOLT, () -> VoltModel.createBodyLayer(new CubeDeformation(0.0F)));
        event.registerLayerDefinition(OPModelLayers.VOLT_CHARGED, () -> VoltModel.createBodyLayer(new CubeDeformation(0.5F)));
        event.registerLayerDefinition(OPModelLayers.WHIZZ, WhizzModel::createBodyLayer);

        event.registerLayerDefinition(OPModelLayers.DEEPWOVEN_ARMOR, DeepwovenArmorModel::createArmorLayer);
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(OPBlockEntityTypes.MOB_HEAD.get(), MobHeadBlockEntityRenderer::new);
    }
}