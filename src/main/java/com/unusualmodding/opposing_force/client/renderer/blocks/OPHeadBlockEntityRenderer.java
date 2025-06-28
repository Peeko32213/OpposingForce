package com.unusualmodding.opposing_force.client.renderer.blocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.blocks.OPHeadBlock;
import com.unusualmodding.opposing_force.blocks.OPWallHeadBlock;
import com.unusualmodding.opposing_force.blocks.entity.OPSkullBlockEntity;
import com.unusualmodding.opposing_force.client.models.mob_heads.DicerHeadModel;
import com.unusualmodding.opposing_force.client.models.mob_heads.OPMobModelBase;
import com.unusualmodding.opposing_force.registry.OPEntityModelLayers;
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

import static com.unusualmodding.opposing_force.OpposingForce.MOD_ID;


@OnlyIn(Dist.CLIENT)

public class OPHeadBlockEntityRenderer implements BlockEntityRenderer<OPSkullBlockEntity> {
    public final Map<OPHeadBlock.Type, OPMobModelBase> modelByType;
    public static final Map<OPHeadBlock.Type, ResourceLocation> SKIN_BY_TYPE = Util.make(Maps.newHashMap(), (p_261388_) -> {
        p_261388_.put(OPHeadBlock.Types.DICER, new ResourceLocation(MOD_ID, "textures/entity/ghoul/ghoul.png"));
    });

    public static Map<OPHeadBlock.Type, OPMobModelBase> createMobHeadRenderers(EntityModelSet root) {
        ImmutableMap.Builder<OPHeadBlock.Type, OPMobModelBase> builder = ImmutableMap.builder();
        builder.put(OPHeadBlock.Types.DICER, new DicerHeadModel(root.bakeLayer(OPEntityModelLayers.DICER_HEAD)));
        return builder.build();
    }

    public OPHeadBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.modelByType = createMobHeadRenderers(context.getModelSet());
    }

    public void render(OPSkullBlockEntity blockEntity, float v, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        float f = blockEntity.getAnimation(v);
        BlockState blockstate = blockEntity.getBlockState();
        boolean flag = blockstate.getBlock() instanceof OPWallHeadBlock;
        Direction direction = flag ? blockstate.getValue(OPWallHeadBlock.FACING) : null;
        int i = flag ? RotationSegment.convertToSegment(direction.getOpposite()) : blockstate.getValue(OPHeadBlock.ROTATION);
        float f1 = RotationSegment.convertToDegrees(i);
        OPHeadBlock.Type type = blockstate.getBlock() instanceof OPWallHeadBlock ? ((OPWallHeadBlock)blockstate.getBlock()).getType() : ((OPHeadBlock)blockstate.getBlock()).getType();
        OPMobModelBase modelBase = this.modelByType.get(type);
        RenderType rendertype = getRenderType(type);
        renderMobHead(direction, f1, f, poseStack, bufferSource, i1, modelBase, rendertype, null, type, false);
    }

    public static void renderMobHead(@Nullable Direction direction, float v, float v1, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, OPMobModelBase modelBase, RenderType renderType, @Nullable ItemDisplayContext context, OPHeadBlock.Type type, boolean isLayer) {
        poseStack.pushPose();

        if (direction == null) {
            poseStack.translate(0.5F, 0.0F, 0.5F);
        } else {
              poseStack.translate(0.5F - (float)direction.getStepX() * 0.25F, 0.25F, 0.5F - (float)direction.getStepZ() * 0.25F);
        }
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        if (context != null) {
            if (type == OPHeadBlock.Types.DICER) {
                if (context == ItemDisplayContext.GUI) {
                    poseStack.scale(0.9F, 0.9F, 0.9F);
                }
            }
        }

        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(renderType);
        modelBase.setupAnim(v1, v, 0.0F);
        modelBase.renderToBuffer(poseStack, vertexconsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    public static RenderType getRenderType(OPHeadBlock.Type type) {
        return RenderType.entityCutoutNoCullZOffset(SKIN_BY_TYPE.get(type));
    }
}
