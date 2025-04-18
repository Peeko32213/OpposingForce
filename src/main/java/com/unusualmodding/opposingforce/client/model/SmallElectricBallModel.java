package com.unusualmodding.opposingforce.client.model;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.entity.projectile.SmallElectricBall;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SmallElectricBallModel extends GeoModel<SmallElectricBall>
{
    @Override
    public ResourceLocation getModelResource(SmallElectricBall object)
    {
        return new ResourceLocation(OpposingForce.MODID, "geo/entity/small_electric_ball.json");
    }

    @Override
    public ResourceLocation getTextureResource(SmallElectricBall object)
    {
        return new ResourceLocation(OpposingForce.MODID, "textures/entity/small_electric_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SmallElectricBall object)
    {
        return new ResourceLocation(OpposingForce.MODID, "animations/small_electric_ball.animation.json");
    }
}
