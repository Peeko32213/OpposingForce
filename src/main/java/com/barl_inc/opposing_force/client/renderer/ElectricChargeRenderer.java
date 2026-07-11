package com.barl_inc.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.barl_inc.opposing_force.entity.projectile.ElectricCharge;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricChargeRenderer extends EntityRenderer<ElectricCharge> {

    public ElectricChargeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ElectricCharge entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ElectricCharge charge) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
