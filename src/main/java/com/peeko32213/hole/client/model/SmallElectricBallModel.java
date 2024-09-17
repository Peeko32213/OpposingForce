package com.peeko32213.hole.client.model;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.EntityTerror;
import com.peeko32213.hole.common.entity.projectile.EntitySmallElectricBall;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SmallElectricBallModel extends GeoModel<EntitySmallElectricBall>
{
    @Override
    public ResourceLocation getModelResource(EntitySmallElectricBall object)
    {
        return new ResourceLocation(Hole.MODID, "geo/entity/small_electric_ball.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntitySmallElectricBall object)
    {
        return new ResourceLocation(Hole.MODID, "textures/entity/small_electric_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntitySmallElectricBall object)
    {
        return new ResourceLocation(Hole.MODID, "animations/small_electric_ball.json");
    }
}
