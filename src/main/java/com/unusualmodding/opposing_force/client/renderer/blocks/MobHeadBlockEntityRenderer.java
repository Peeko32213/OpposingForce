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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class MobHeadBlockEntityRenderer implements BlockEntityRenderer<MobHeadBlockEntity> {

    public final Map<MobHeadBlock.Type, MobHeadModelBase> modelByType;

    public static final Map<MobHeadBlock.Type, ResourceLocation> SKIN_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        map.put(MobHeadBlock.Types.DICER, OpposingForce.modPrefix("textures/entity/dicer/dicer.png"));
        map.put(MobHeadBlock.Types.FROWZY, OpposingForce.modPrefix("textures/entity/frowzy.png"));
        map.put(MobHeadBlock.Types.RAMBLER_ANGRY, OpposingForce.modPrefix("textures/entity/rambler/skulls/angry.png"));
        map.put(MobHeadBlock.Types.RAMBLER_CLASSIC, OpposingForce.modPrefix("textures/entity/rambler/skulls/classic.png"));
        map.put(MobHeadBlock.Types.RAMBLER_CRUNDLY, OpposingForce.modPrefix("textures/entity/rambler/skulls/crundly.png"));
        map.put(MobHeadBlock.Types.RAMBLER_DWARVEN, OpposingForce.modPrefix("textures/entity/rambler/skulls/dwarven.png"));
        map.put(MobHeadBlock.Types.RAMBLER_EVIL, OpposingForce.modPrefix("textures/entity/rambler/skulls/evil.png"));
        map.put(MobHeadBlock.Types.RAMBLER_GRINNING, OpposingForce.modPrefix("textures/entity/rambler/skulls/grinning.png"));
        map.put(MobHeadBlock.Types.RAMBLER_IMPRISONED, OpposingForce.modPrefix("textures/entity/rambler/skulls/imprisoned.png"));
        map.put(MobHeadBlock.Types.RAMBLER_INDOMITABLE, OpposingForce.modPrefix("textures/entity/rambler/skulls/indomitable.png"));
        map.put(MobHeadBlock.Types.RAMBLER_LEERING, OpposingForce.modPrefix("textures/entity/rambler/skulls/leering.png"));
        map.put(MobHeadBlock.Types.RAMBLER_MAGMATIC, OpposingForce.modPrefix("textures/entity/rambler/skulls/magmatic.png"));
        map.put(MobHeadBlock.Types.RAMBLER_MUSICAL, OpposingForce.modPrefix("textures/entity/rambler/skulls/musical.png"));
        map.put(MobHeadBlock.Types.RAMBLER_NOSY, OpposingForce.modPrefix("textures/entity/rambler/skulls/nosy.png"));
        map.put(MobHeadBlock.Types.RAMBLER_SKELETAL, OpposingForce.modPrefix("textures/entity/rambler/skulls/skeletal.png"));
        map.put(MobHeadBlock.Types.RAMBLER_SMILING, OpposingForce.modPrefix("textures/entity/rambler/skulls/smiling.png"));
        map.put(MobHeadBlock.Types.RAMBLER_STRANGE, OpposingForce.modPrefix("textures/entity/rambler/skulls/strange.png"));
        map.put(MobHeadBlock.Types.RAMBLER_VALIANT, OpposingForce.modPrefix("textures/entity/rambler/skulls/valiant.png"));
        map.put(MobHeadBlock.Types.TART, OpposingForce.modPrefix("textures/entity/tart.png"));
        map.put(MobHeadBlock.Types.WHIZZ, OpposingForce.modPrefix("textures/entity/whizz/whizz.png"));
    });

    public static Map<MobHeadBlock.Type, MobHeadModelBase> createMobHeadRenderers(EntityModelSet root) {
        ImmutableMap.Builder<MobHeadBlock.Type, MobHeadModelBase> builder = ImmutableMap.builder();
        builder.put(MobHeadBlock.Types.DICER, new DicerHeadModel(root.bakeLayer(OPModelLayers.DICER_HEAD)));
        builder.put(MobHeadBlock.Types.FROWZY, new FrowzyHeadModel(root.bakeLayer(OPModelLayers.FROWZY_HEAD)));
        builder.put(MobHeadBlock.Types.RAMBLER_ANGRY, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_CLASSIC, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_CRUNDLY, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_DWARVEN, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_EVIL, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_GRINNING, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_IMPRISONED, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_INDOMITABLE, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_LEERING, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_MAGMATIC, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_MUSICAL, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_NOSY, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_SKELETAL, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_SMILING, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_STRANGE, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.RAMBLER_VALIANT, new RamblerSkullModel(root.bakeLayer(OPModelLayers.RAMBLER_SKULL)));
        builder.put(MobHeadBlock.Types.TART, new TartHeadModel(root.bakeLayer(OPModelLayers.TART_HEAD)));
        builder.put(MobHeadBlock.Types.WHIZZ, new WhizzHeadModel(root.bakeLayer(OPModelLayers.WHIZZ_HEAD)));
        return builder.build();
    }

    public MobHeadBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.modelByType = createMobHeadRenderers(context.getModelSet());
    }

    @Override
    public void render(MobHeadBlockEntity blockEntity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int i1, int i2) {
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
                    poseStack.scale(0.8F, 0.8F, 0.8F);
                    poseStack.translate(0.0F, -0.1F, 0.0F);
                }
            }
            if (type == MobHeadBlock.Types.WHIZZ) {
                if (context == ItemDisplayContext.GUI) {
                    poseStack.scale(0.75F, 0.75F, 0.75F);
                    poseStack.translate(0.2F, -0.5F, 0.0F);
                }
            }
            else {
                if (context == ItemDisplayContext.GUI) {
                    poseStack.scale(1, 1, 1);
                }
            }
        }

        if (isLayer) {
            if (type == MobHeadBlock.Types.WHIZZ) {
                poseStack.translate(0.0F, -0.1F, 0.0F);
            }
            poseStack.scale(1, 1,1);
        }

        modelBase.setupAnim(v1, v, 0);
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(renderType);
        modelBase.renderToBuffer(poseStack, vertexconsumer, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        if (modelBase instanceof DicerHeadModel dicerHeadModel) {
            dicerHeadModel.renderVisorToBuffer(poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }

        poseStack.popPose();
    }

    public static RenderType getRenderType(MobHeadBlock.Type type) {
        return RenderType.entityCutoutNoCullZOffset(SKIN_BY_TYPE.get(type));
    }
}