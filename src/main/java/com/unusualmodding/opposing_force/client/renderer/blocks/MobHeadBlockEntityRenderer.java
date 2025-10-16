package com.unusualmodding.opposing_force.client.renderer.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.MobHeadBlock;
import com.unusualmodding.opposing_force.blocks.WallMobHeadBlock;
import com.unusualmodding.opposing_force.blocks.entity.MobHeadBlockEntity;
import com.unusualmodding.opposing_force.client.models.mob_heads.*;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.Util;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class MobHeadBlockEntityRenderer implements BlockEntityRenderer<MobHeadBlockEntity> {

    public final Map<MobHeadBlock.Type, MobHeadModelBase> modelByType;

    public static final Map<MobHeadBlock.Type, ResourceLocation> SKIN_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        map.put(MobHeadBlock.Types.DICER, new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/dicer.png"));
        map.put(MobHeadBlock.Types.FROWZY, new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/frowzy.png"));
        map.put(MobHeadBlock.Types.RAMBLE, new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/ramble.png"));
    });

    public static Map<MobHeadBlock.Type, MobHeadModelBase> createMobHeadRenderers(EntityModelSet root) {
        ImmutableMap.Builder<MobHeadBlock.Type, MobHeadModelBase> builder = ImmutableMap.builder();
        builder.put(MobHeadBlock.Types.DICER, new DicerHeadModel(root.bakeLayer(OPModelLayers.DICER_HEAD)));
        builder.put(MobHeadBlock.Types.FROWZY, new FrowzyHeadModel(root.bakeLayer(OPModelLayers.FROWZY_HEAD)));
        builder.put(MobHeadBlock.Types.RAMBLE, new RambleSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        return builder.build();
    }

    public MobHeadBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.modelByType = createMobHeadRenderers(context.getModelSet());
    }

    @Override
    public void render(MobHeadBlockEntity blockEntity, float v, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        float f = blockEntity.getAnimation(v);
        BlockState blockstate = blockEntity.getBlockState();
        boolean flag = blockstate.getBlock() instanceof WallMobHeadBlock;
        Direction direction = flag ? blockstate.getValue(WallMobHeadBlock.FACING) : null;
        int i = flag ? RotationSegment.convertToSegment(direction.getOpposite()) : blockstate.getValue(MobHeadBlock.ROTATION);
        float f1 = RotationSegment.convertToDegrees(i);
        MobHeadBlock.Type type = blockstate.getBlock() instanceof WallMobHeadBlock ? ((WallMobHeadBlock) blockstate.getBlock()).getType() : ((MobHeadBlock) blockstate.getBlock()).getType();
        MobHeadModelBase modelBase = this.modelByType.get(type);
        RenderType rendertype = getRenderType(type);
        renderMobHead(direction, f1, f, poseStack, bufferSource, i1, modelBase, rendertype, null, type, false);
    }

    public static void renderMobHead(@Nullable Direction direction, float v, float v1, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, MobHeadModelBase modelBase, RenderType renderType, @Nullable ItemDisplayContext context, MobHeadBlock.Type type, boolean isLayer) {
        poseStack.pushPose();

        if (direction == null) {
            poseStack.translate(0.5F, 0, 0.5F);
        } else {
            poseStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25F, 0.5F - (float) direction.getStepZ() * 0.25F);
        }

        poseStack.scale(-1, -1, 1);

        if (context != null) {
            if (type == MobHeadBlock.Types.DICER) {
                if (context == ItemDisplayContext.GUI) {
                    poseStack.scale(0.9F, 0.9F, 0.9F);
                }
            }
            if (type == MobHeadBlock.Types.FROWZY) {
                if (context == ItemDisplayContext.GUI) {
                    poseStack.scale(1, 1, 1);
                }
            }
            if (type == MobHeadBlock.Types.RAMBLE) {
                if (context == ItemDisplayContext.GUI) {
                    poseStack.scale(1, 1, 1);
                }
            }
        }

        if (isLayer) {
            if (type == MobHeadBlock.Types.DICER) {
                poseStack.scale(1, 1,1);
            }
            if (type == MobHeadBlock.Types.FROWZY) {
                poseStack.scale(1, 1,1);
            }
            if (type == MobHeadBlock.Types.RAMBLE) {
                poseStack.scale(1, 1,1);
            }
        }

        modelBase.setupAnim(v1, v, 0);
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(renderType);
        modelBase.renderToBuffer(poseStack, vertexconsumer, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        if (modelBase instanceof DicerHeadModel dicerHeadModel) {
            dicerHeadModel.renderVisorToBuffer(poseStack, vertexconsumer, multiBufferSource, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }

        poseStack.popPose();
    }

    public static RenderType getRenderType(MobHeadBlock.Type type) {
        return RenderType.entityCutoutNoCullZOffset(SKIN_BY_TYPE.get(type));
    }
}