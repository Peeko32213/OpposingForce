package com.unusualmodding.opposing_force.client.models;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TerrorDefaultModel<T extends GeoAnimatable> extends GeoModel<T> {
    private final String model;
    private final String texture;
    private final String anim;

    public TerrorDefaultModel(String name){
        this(name, name, name);
    }

    public TerrorDefaultModel(String name, String texture){
        this(name, texture, name);
    }

    public TerrorDefaultModel(String model, String texture, String anim){
        this.model = model;
        this.texture = texture;
        this.anim = anim;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return new ResourceLocation(OpposingForce.MOD_ID, "geo/entity/" + model + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/" + texture + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(OpposingForce.MOD_ID, "animations/" + anim + ".animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone backBody = this.getAnimationProcessor().getBone("BackBody");
        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("Body");
        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));

        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));
        tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));

    }
}
