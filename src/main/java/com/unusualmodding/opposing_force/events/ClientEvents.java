package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.armor.*;
import com.unusualmodding.opposing_force.client.models.entity.*;
import com.unusualmodding.opposing_force.client.models.entity.skyvern.*;
import com.unusualmodding.opposing_force.client.models.mob_heads.*;
import com.unusualmodding.opposing_force.client.particles.*;
import com.unusualmodding.opposing_force.client.particles.lightning.LightningParticle;
import com.unusualmodding.opposing_force.client.renderer.*;
import com.unusualmodding.opposing_force.client.renderer.blocks.*;
import com.unusualmodding.opposing_force.items.BlasterItem;
import com.unusualmodding.opposing_force.items.LaserBladeItem;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(OPItemProperties::registerItemProperties);
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(OPParticles.LASER_BOLT_DUST.get(), LaserBoltDustParticle.Factory::new);
        event.registerSpriteSet(OPParticles.MOON_SHOES.get(), MoonShoesParticle.Factory::new);
        event.registerSpriteSet(OPParticles.LASER_SWEEP.get(), LaserSweepParticle.Factory::new);
        event.registerSpriteSet(OPParticles.DYED_SWEEP.get(), LaserSweepParticle.DyedFactory::new);
        event.registerSpecial(OPParticles.LIGHTNING.get(), new LightningParticle.Factory());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(OPEntities.DICER.get(), DicerRenderer::new);
        event.registerEntityRenderer(OPEntities.DICER_LASER.get(), DicerLaserRenderer::new);
        event.registerEntityRenderer(OPEntities.ELECTRIC_CHARGE.get(), ElectricChargeRenderer::new);
        event.registerEntityRenderer(OPEntities.LASER_BOLT.get(), LaserBoltRenderer::new);
        event.registerEntityRenderer(OPEntities.LASER_BLADE.get(), ThrownLaserBladeRenderer::new);
        event.registerEntityRenderer(OPEntities.EMERALDFISH.get(), EmeraldfishRenderer::new);
        event.registerEntityRenderer(OPEntities.FIRE_BOMB.get(), FireBombRenderer::new);
        event.registerEntityRenderer(OPEntities.FIRE_SLIME.get(), FireSlimeRenderer::new);
        event.registerEntityRenderer(OPEntities.FROWZY.get(), FrowzyRenderer::new);
        event.registerEntityRenderer(OPEntities.GUZZLER.get(), GuzzlerRenderer::new);
        event.registerEntityRenderer(OPEntities.HANGING_SPIDER.get(), HangingSpiderRenderer::new);
        event.registerEntityRenderer(OPEntities.LADYBUG.get(), LadybugRenderer::new);
//        event.registerEntityRenderer(OPEntities.NYMPH.get(), NymphRenderer::new);
        event.registerEntityRenderer(OPEntities.KINETIC_BOMB.get(), KineticBombRenderer::new);
        event.registerEntityRenderer(OPEntities.LIGHTNING_BOMB.get(), LightningBombRenderer::new);
        event.registerEntityRenderer(OPEntities.RAMBLER.get(), RamblerRenderer::new);
        event.registerEntityRenderer(OPEntities.SKYVERN.get(), SkyvernHeadRenderer::new);
        event.registerEntityRenderer(OPEntities.SKYVERN_SEGMENT.get(), SkyvernSegmentRenderer::new);
        event.registerEntityRenderer(OPEntities.STRATO_ARROW.get(), StratoArrowRenderer::new);
        event.registerEntityRenderer(OPEntities.SLUG.get(), SlugRenderer::new);
        event.registerEntityRenderer(OPEntities.TART.get(), TartRenderer::new);
        event.registerEntityRenderer(OPEntities.TERROR.get(), TerrorRenderer::new);
        event.registerEntityRenderer(OPEntities.TOMAHAWK.get(), TomahawkRenderer::new);
        event.registerEntityRenderer(OPEntities.TREMBLER.get(), TremblerRenderer::new);
        event.registerEntityRenderer(OPEntities.UMBER_DAGGER.get(), UmberDaggerRenderer::new);
        event.registerEntityRenderer(OPEntities.UMBER_SPIDER.get(), UmberSpiderRenderer::new);
        event.registerEntityRenderer(OPEntities.VOLT.get(), VoltRenderer::new);
        event.registerEntityRenderer(OPEntities.WHIZZ.get(), WhizzRenderer::new);
        event.registerEntityRenderer(OPEntities.WHIZZ_BOMB.get(), WhizzBombRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OPModelLayers.DICER, DicerModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.DICER_HEAD, DicerHeadModel::createHeadLayer);
        event.registerLayerDefinition(OPModelLayers.EMERALDFISH, EmeraldfishModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.FIRE_SLIME, FireSlimeModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.FROWZY, FrowzyModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.FROWZY_HEAD, FrowzyHeadModel::createHeadLayer);
        event.registerLayerDefinition(OPModelLayers.GUZZLER, GuzzlerModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.HANGING_SPIDER, HangingSpiderModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.LADYBUG, LadybugModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.NYMPH, NymphModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.RAMBLER, RamblerModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.RAMBLER_SKULL, RamblerSkullModel::createSkullLayer);
        event.registerLayerDefinition(OPModelLayers.SKYVERN, SkyvernModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.SKYVERN_BODY, SkyvernBodyModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.SKYVERN_TAIL, SkyvernTailModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.SKYVERN_HEAD, SkyvernHeadModel::createHeadLayer);
        event.registerLayerDefinition(OPModelLayers.SLUG, SlugModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.TART, TartModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.TART_HEAD, TartHeadModel::createHeadLayer);
        event.registerLayerDefinition(OPModelLayers.TERROR, TerrorModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.TREMBLER, TremblerModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.UMBER_SPIDER, UmberSpiderModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.VOLT, () -> VoltModel.createBodyLayer(new CubeDeformation(0.0F)));
        event.registerLayerDefinition(OPModelLayers.VOLT_CHARGED, () -> VoltModel.createBodyLayer(new CubeDeformation(1.0F)));
        event.registerLayerDefinition(OPModelLayers.WHIZZ, WhizzModel::createBodyLayer);
        event.registerLayerDefinition(OPModelLayers.WHIZZ_HEAD, WhizzHeadModel::createHeadLayer);
        event.registerLayerDefinition(OPModelLayers.LASER_BOLT, LaserBoltModel::createProjectileLayer);

        // Armor
        event.registerLayerDefinition(OPModelLayers.BONE_ARMOR, BoneArmorModel::createArmorLayer);
        event.registerLayerDefinition(OPModelLayers.DEEPWOVEN_ARMOR, DeepwovenArmorModel::createArmorLayer);
        event.registerLayerDefinition(OPModelLayers.EMERALD_ARMOR, EmeraldArmorModel::createArmorLayer);
        event.registerLayerDefinition(OPModelLayers.MOON_SHOES, MoonShoesModel::createArmorLayer);
        event.registerLayerDefinition(OPModelLayers.RECON_KNIGHT_ARMOR, ReconKnightArmorModel::createArmorLayer);
        event.registerLayerDefinition(OPModelLayers.SLUG_BARON_ARMOR, SlugBaronArmorModel::createArmorLayer);
        event.registerLayerDefinition(OPModelLayers.STONE_ARMOR, StoneArmorModel::createArmorLayer);
        event.registerLayerDefinition(OPModelLayers.WOODEN_ARMOR, WoodenArmorModel::createArmorLayer);
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(OPBlockEntityTypes.MOB_HEAD.get(), MobHeadBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null) {
                        return FoliageColor.getDefaultColor();
                    }
                    return BiomeColors.getAverageFoliageColor(world, pos);
                },
                OPBlocks.INFESTED_OAK_LEAVES.get()
        );
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
                    if (tintIndex == 0) {
                        if (stack.getItem() instanceof LaserBladeItem item) {
                            return item.getColor(stack);
                        }
                        if (stack.getItem() instanceof BlasterItem item) {
                            return item.getColor(stack);
                        }
                        return -1;
                    }
                    return 0xFFFFFF;
                },
                OPItems.LASER_BLADE.get()
        );
        event.register((stack, tintIndex) -> {
                    BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                    return event.getBlockColors().getColor(blockstate, null, null, tintIndex);
                },
                OPBlocks.INFESTED_OAK_LEAVES.get()
        );
    }
}