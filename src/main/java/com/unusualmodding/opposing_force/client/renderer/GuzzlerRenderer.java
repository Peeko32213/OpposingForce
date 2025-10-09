package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.GuzzlerModel;
import com.unusualmodding.opposing_force.client.renderer.layers.GuzzlerGlowLayer;
import com.unusualmodding.opposing_force.entity.Guzzler;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GuzzlerRenderer extends MobRenderer<Guzzler, GuzzlerModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/guzzler/guzzler.png");

    public GuzzlerRenderer(EntityRendererProvider.Context context) {
        super(context, new GuzzlerModel(context.bakeLayer(OPModelLayers.GUZZLER)), 1.1F);
        this.addLayer(new GuzzlerGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Guzzler entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Guzzler entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
