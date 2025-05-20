package com.unusualmodding.opposing_force.client.models;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.UmberSpiderEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class UmberSpiderModel extends GeoModel<UmberSpiderEntity> {
    @Override
    public ResourceLocation getModelResource(UmberSpiderEntity object) {
        return new ResourceLocation(OpposingForce.MOD_ID, "geo/entity/umber_spider.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UmberSpiderEntity object) {
        return new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UmberSpiderEntity object) {
        return new ResourceLocation(OpposingForce.MOD_ID, "animations/umber_spider.animation.json");
    }

    @Override
    public void setCustomAnimations(UmberSpiderEntity animatable, long instanceId, AnimationState<UmberSpiderEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("Striker");

//        if (animatable.isClimbing()) {
//            root.setRotX(90.0F);
//        }
    }
}
