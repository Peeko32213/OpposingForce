package com.unusualmodding.opposing_force.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.MobHeadBlock;
import com.unusualmodding.opposing_force.blocks.WallMobHeadBlock;
import com.unusualmodding.opposing_force.client.models.item.BlasterModel;
import com.unusualmodding.opposing_force.client.models.mob_heads.MobHeadModelBase;
import com.unusualmodding.opposing_force.client.renderer.blocks.MobHeadBlockEntityRenderer;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPItemRenderers extends BlockEntityWithoutLevelRenderer {

//    private static final ResourceLocation BLASTER_TEXTURE = modPrefix("textures/item/blaster.png");
//    private final BlasterModel blasterModel;

    public static OPItemRenderers instance;
    private Map<MobHeadBlock.Type, MobHeadModelBase> headModelBaseMap;


    public OPItemRenderers(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
//        this.blasterModel = new BlasterModel(modelSet.bakeLayer(OPModelLayers.BLASTER));
    }

    @SubscribeEvent
    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        instance = new OPItemRenderers(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        event.registerReloadListener(instance);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        super.onResourceManagerReload(resourceManager);
        this.headModelBaseMap = MobHeadBlockEntityRenderer.createMobHeadRenderers(this.entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof MobHeadBlock || block instanceof WallMobHeadBlock) {
                MobHeadBlock.Type type = block instanceof MobHeadBlock ? ((MobHeadBlock)block).getType() : ((WallMobHeadBlock) block).getType();
                MobHeadModelBase modelBase = this.headModelBaseMap.get(type);
                RenderType rendertype = MobHeadBlockEntityRenderer.getRenderType(type);

                MobHeadBlockEntityRenderer.renderMobHead(null, 180.0F, 0.0F, poseStack, bufferSource, light, modelBase, rendertype, itemDisplayContext, type, false);
            }
        }
//        if (item == OPItems.BLASTER.get()) {
//            float shootProgress = 1;
//            poseStack.pushPose();
//            poseStack.translate(0.5F, 1.5F, 0.5F);
//            poseStack.mulPose(Axis.XP.rotationDegrees(-180));
//            poseStack.mulPose(Axis.YP.rotationDegrees(180));
//            poseStack.pushPose();
//            poseStack.scale(0.8F, 0.8F, 0.8F);
//
//            blasterModel.setupAnim(shootProgress, 1,  1);
//            blasterModel.renderToBuffer(poseStack, ItemRenderer.getFoilBuffer(bufferSource, RenderType.entityCutoutNoCull(BLASTER_TEXTURE), false, itemStack.hasFoil()), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
//            poseStack.popPose();
//            poseStack.popPose();
//        }
    }
}
