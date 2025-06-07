package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.PaleSpiderModel;
import com.unusualmodding.opposing_force.client.renderer.layer.PaleSpiderEyesLayer;
import com.unusualmodding.opposing_force.entity.PaleSpider;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class PaleSpiderRenderer extends MobRenderer<PaleSpider, PaleSpiderModel<PaleSpider>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/pale_spider.png");

    public PaleSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new PaleSpiderModel<>(context.bakeLayer(OPModelLayers.PALE_SPIDER_LAYER)), 0.5F);
        this.addLayer(new PaleSpiderEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(PaleSpider entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(PaleSpider entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }

    @Override
    protected void setupRotations(PaleSpider entity, PoseStack matrixStack, float f, float g, float h) {
        super.setupRotations(entity, matrixStack, f, g, h);
        matrixStack.scale(0.75F, 0.75F, 0.75F);

        if (entity.isUpsideDown() && !entity.onGround()) {
            matrixStack.translate(0.0, 0.6, 0);
            matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }
}
