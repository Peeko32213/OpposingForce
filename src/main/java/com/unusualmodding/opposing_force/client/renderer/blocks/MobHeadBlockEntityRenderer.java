package com.unusualmodding.opposing_force.client.renderer.blocks;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.MobHeadBlock;
import com.unusualmodding.opposing_force.client.models.mob_heads.DicerHeadModel;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.model.PiglinHeadModel;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.ModLoader;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class MobHeadBlockEntityRenderer extends SkullBlockRenderer implements BlockEntityRenderer<SkullBlockEntity> {

    private final Map<SkullBlock.Type, SkullModelBase> modelByType;

    public MobHeadBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.modelByType = createSkullRenderers(context.getModelSet());
        SKIN_BY_TYPE.put(MobHeadBlock.Types.DICER, new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/dicer"));
    }

    @Override
    public void render(SkullBlockEntity skullBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_112538_, int p_112539_) {
        float f = skullBlockEntity.getAnimation(partialTicks);
        BlockState blockstate = skullBlockEntity.getBlockState();
        boolean flag = blockstate.getBlock() instanceof WallSkullBlock;
        Direction direction = flag ? blockstate.getValue(WallSkullBlock.FACING) : null;
        int i = flag ? RotationSegment.convertToSegment(direction.getOpposite()) : blockstate.getValue(SkullBlock.ROTATION);
        float f1 = RotationSegment.convertToDegrees(i);
        SkullBlock.Type skullType = ((AbstractSkullBlock) blockstate.getBlock()).getType();
        SkullModelBase skullmodelbase = this.modelByType.get(skullType);
        RenderType rendertype = getRenderType(skullType, skullBlockEntity.getOwnerProfile());
        renderSkull(direction, f1, f, poseStack, multiBufferSource, p_112538_, skullmodelbase, rendertype);
    }

    public static void renderSkull(@Nullable Direction direction, float p_173665_, float animationProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_173669_, SkullModelBase skullModelBase, RenderType renderType) {
        poseStack.pushPose();
        if (direction == null) {
            poseStack.translate(0.5F, 0.0F, 0.5F);
        } else {
            poseStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25F, 0.5F - (float) direction.getStepZ() * 0.25F);
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(renderType);
        skullModelBase.setupAnim(animationProgress, p_173665_, 0.0F);

        if (skullModelBase instanceof DicerHeadModel dicerHeadModel) {
            dicerHeadModel.renderToBuffer(poseStack, vertexconsumer, multiBufferSource, p_173669_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        poseStack.popPose();
    }

    @SuppressWarnings("UnstableApiUsage")
    public static Map<SkullBlock.Type, SkullModelBase> createSkullRenderers(EntityModelSet entityModelSet) {
        ImmutableMap.Builder<SkullBlock.Type, SkullModelBase> builder = ImmutableMap.builder();

        builder.put(SkullBlock.Types.SKELETON, new SkullModel(entityModelSet.bakeLayer(ModelLayers.SKELETON_SKULL)));
        builder.put(SkullBlock.Types.WITHER_SKELETON, new SkullModel(entityModelSet.bakeLayer(ModelLayers.WITHER_SKELETON_SKULL)));
        builder.put(SkullBlock.Types.PLAYER, new SkullModel(entityModelSet.bakeLayer(ModelLayers.PLAYER_HEAD)));
        builder.put(SkullBlock.Types.ZOMBIE, new SkullModel(entityModelSet.bakeLayer(ModelLayers.ZOMBIE_HEAD)));
        builder.put(SkullBlock.Types.CREEPER, new SkullModel(entityModelSet.bakeLayer(ModelLayers.CREEPER_HEAD)));
        builder.put(SkullBlock.Types.DRAGON, new DragonHeadModel(entityModelSet.bakeLayer(ModelLayers.DRAGON_SKULL)));
        builder.put(SkullBlock.Types.PIGLIN, new PiglinHeadModel(entityModelSet.bakeLayer(ModelLayers.PIGLIN_HEAD)));

        builder.put(MobHeadBlock.Types.DICER, new DicerHeadModel(entityModelSet.bakeLayer(OPModelLayers.DICER_HEAD), entityModelSet.bakeLayer(OPModelLayers.DICER_VISOR)));

        ModLoader.get().postEvent(new EntityRenderersEvent.CreateSkullModels(builder, entityModelSet));
        return builder.build();
    }
}