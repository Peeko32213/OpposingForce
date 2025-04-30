package com.unusualmodding.opposingforce.client.model;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.entity.custom.monster.WhizzEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WhizzModel extends GeoModel<WhizzEntity> {
    @Override
    public ResourceLocation getModelResource(WhizzEntity object) {
        return new ResourceLocation(OpposingForce.MODID, "geo/entity/whizz.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WhizzEntity object) {
        return new ResourceLocation(OpposingForce.MODID, "textures/entity/whizz.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WhizzEntity object) {
        return new ResourceLocation(OpposingForce.MODID, "animations/whizz.animation.json");
    }
}