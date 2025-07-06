package com.unusualmodding.opposing_force.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.opposing_force.blocks.MobHeadBlock;
import com.unusualmodding.opposing_force.client.renderer.blocks.MobHeadBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class OPItemRenderers extends BlockEntityWithoutLevelRenderer {

    private final Map<SkullBlock.Type, SkullModelBase> skullModels;

    private static final OPItemRenderers instance = new OPItemRenderers(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

    public static OPItemRenderers getInstance() {
        return instance;
    }

    public OPItemRenderers(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
        this.skullModels = MobHeadBlockEntityRenderer.createSkullRenderers(entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof MobHeadBlock headBlock) {
                SkullBlock.Type type = headBlock.getType();
                SkullModelBase skullmodelbase = this.skullModels.get(type);
                RenderType rendertype = SkullBlockRenderer.getRenderType(type, null);

                MobHeadBlockEntityRenderer.renderSkull(null, 180.0F, 0.0F, poseStack, multiBufferSource, light, skullmodelbase, rendertype);
            }
        }
    }
}
