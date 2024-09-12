package com.peeko32213.hole.client.model;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.EntityTerror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TerrorModel extends GeoModel<EntityTerror>
{
    @Override
    public ResourceLocation getModelResource(EntityTerror object)
    {
        return new ResourceLocation(Hole.MODID, "geo/entity/terror.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTerror object)
    {
        return new ResourceLocation(Hole.MODID, "textures/entity/terror.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTerror object)
    {
        return new ResourceLocation(Hole.MODID, "animations/terror.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityTerror animatable, long instanceId, AnimationState<EntityTerror> animationState) {
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
