package com.unusualmodding.opposingforce.client.model;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.entity.TerrorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TerrorModel extends GeoModel<TerrorEntity>
{
    @Override
    public ResourceLocation getModelResource(TerrorEntity object)
    {
        return new ResourceLocation(OpposingForce.MODID, "geo/entity/terror.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TerrorEntity object)
    {
        return new ResourceLocation(OpposingForce.MODID, "textures/entity/terror.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TerrorEntity object)
    {
        return new ResourceLocation(OpposingForce.MODID, "animations/terror.animation.json");
    }

    @Override
    public void setCustomAnimations(TerrorEntity animatable, long instanceId, AnimationState<TerrorEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone backBody = this.getAnimationProcessor().getBone("BackBody");
        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("Body");
        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
        root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));
        tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));

    }

}
