package com.unusualmodding.opposingforce.client.model;

import com.unusualmodding.opposingforce.OpposingForce;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class DefaultModel<T extends GeoAnimatable> extends GeoModel<T> {
    private final String model;
    private final String texture;
    private final String anim;

    public DefaultModel(String name){
        this(name, name, name);
    }

    public DefaultModel(String name, String texture){
        this(name, texture, name);
    }

    public DefaultModel(String model, String texture, String anim){
        this.model = model;
        this.texture = texture;
        this.anim = anim;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return new ResourceLocation(OpposingForce.MODID, "geo/entity/" + model + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return new ResourceLocation(OpposingForce.MODID, "textures/entity/" + texture + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(OpposingForce.MODID, "animations/" + anim + ".animation.json");
    }
}
