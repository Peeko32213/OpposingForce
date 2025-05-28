package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.WhizzModel;
import com.unusualmodding.opposing_force.entity.WhizzEntity;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class WhizzRenderer extends MobRenderer<WhizzEntity, WhizzModel<WhizzEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/whizz.png");

    public WhizzRenderer(EntityRendererProvider.Context context) {
        super(context, new WhizzModel<>(context.bakeLayer(OPModelLayers.WHIZZ_LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(WhizzEntity entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(WhizzEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}