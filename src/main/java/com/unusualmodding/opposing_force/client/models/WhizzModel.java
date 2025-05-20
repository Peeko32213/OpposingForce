package com.unusualmodding.opposing_force.client.models;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.WhizzEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WhizzModel extends GeoModel<WhizzEntity> {
    @Override
    public ResourceLocation getModelResource(WhizzEntity object) {
        return new ResourceLocation(OpposingForce.MOD_ID, "geo/entity/whizz.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WhizzEntity object) {
        return new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/whizz.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WhizzEntity object) {
        return new ResourceLocation(OpposingForce.MOD_ID, "animations/whizz.animation.json");
    }
}