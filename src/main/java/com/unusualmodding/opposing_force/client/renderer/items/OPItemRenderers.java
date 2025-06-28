package com.unusualmodding.opposing_force.client.renderer.items;

import com.unusualmodding.opposing_force.OpposingForce;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.blocks.OPHeadBlock;
import com.unusualmodding.opposing_force.blocks.OPWallHeadBlock;
import com.unusualmodding.opposing_force.client.models.mob_heads.OPMobModelBase;
import com.unusualmodding.opposing_force.client.renderer.blocks.OPHeadBlockEntityRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPItemRenderers extends BlockEntityWithoutLevelRenderer {
    public static OPItemRenderers instance;
    private Map<OPHeadBlock.Type, OPMobModelBase> headModelBaseMap;

    public OPItemRenderers(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @SubscribeEvent
    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        instance = new OPItemRenderers(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        event.registerReloadListener(instance);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        super.onResourceManagerReload(resourceManager);
        this.headModelBaseMap = OPHeadBlockEntityRenderer.createMobHeadRenderers(this.entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource bufferSource, int p_108834_, int p_108835_) {
        Item item = itemStack.getItem();

        if (item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
            if (block instanceof OPHeadBlock || block instanceof OPWallHeadBlock) {
                OPHeadBlock.Type type = block instanceof OPHeadBlock ? ((OPHeadBlock)block).getType() : ((OPWallHeadBlock)block).getType();
                OPMobModelBase modelBase = this.headModelBaseMap.get(type);
                RenderType rendertype = OPHeadBlockEntityRenderer.getRenderType(type);

                OPHeadBlockEntityRenderer.renderMobHead(null, 180.0F, 0.0F, poseStack, bufferSource, p_108834_, modelBase, rendertype, itemDisplayContext, type, false);
            }
        }
    }
}
