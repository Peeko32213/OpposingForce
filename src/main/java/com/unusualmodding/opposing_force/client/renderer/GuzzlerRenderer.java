package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.GuzzlerModel;
import com.unusualmodding.opposing_force.entity.GuzzlerEntity;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GuzzlerRenderer extends MobRenderer<GuzzlerEntity, GuzzlerModel<GuzzlerEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/guzzler.png");

    public GuzzlerRenderer(EntityRendererProvider.Context context) {
        super(context, new GuzzlerModel<>(context.bakeLayer(OPModelLayers.GUZZLER_LAYER)), 1.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(GuzzlerEntity entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(GuzzlerEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
