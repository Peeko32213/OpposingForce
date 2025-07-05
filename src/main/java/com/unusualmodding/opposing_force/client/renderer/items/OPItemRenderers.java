package com.unusualmodding.opposing_force.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.MobHeadBlock;
import com.unusualmodding.opposing_force.blocks.WallMobHeadBlock;
import com.unusualmodding.opposing_force.client.models.mob_heads.MobHeadModelBase;
import com.unusualmodding.opposing_force.client.renderer.blocks.MobHeadBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
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

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPItemRenderers extends BlockEntityWithoutLevelRenderer {

    public static OPItemRenderers instance;
    private Map<MobHeadBlock.Type, MobHeadModelBase> headModelBaseMap;

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
        this.headModelBaseMap = MobHeadBlockEntityRenderer.createMobHeadRenderers(this.entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource bufferSource, int p_108834_, int p_108835_) {
        Item item = itemStack.getItem();

        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof MobHeadBlock || block instanceof WallMobHeadBlock) {
                MobHeadBlock.Type type = block instanceof MobHeadBlock ? ((MobHeadBlock)block).getType() : ((WallMobHeadBlock) block).getType();
                MobHeadModelBase modelBase = this.headModelBaseMap.get(type);
                RenderType rendertype = MobHeadBlockEntityRenderer.getRenderType(type);

                MobHeadBlockEntityRenderer.renderMobHead(null, 180.0F, 0.0F, poseStack, bufferSource, p_108834_, modelBase, rendertype, itemDisplayContext, type, false);
            }
        }
    }
}
