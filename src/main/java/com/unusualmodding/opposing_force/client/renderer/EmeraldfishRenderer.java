package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.EmeraldfishModel;
import com.unusualmodding.opposing_force.entity.Emeraldfish;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EmeraldfishRenderer extends MobRenderer<Emeraldfish, EmeraldfishModel<Emeraldfish>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/emeraldfish.png");


    public EmeraldfishRenderer(EntityRendererProvider.Context context) {
        super(context, new EmeraldfishModel(context.bakeLayer(OPModelLayers.EMERALDFISH_LAYER)), 0.3F);
    }

    protected float getFlipDegrees(Emeraldfish pLivingEntity) {
        return 180.0F;
    }

    public ResourceLocation getTextureLocation(Emeraldfish pEntity) {
        return TEXTURE;
    }
}
