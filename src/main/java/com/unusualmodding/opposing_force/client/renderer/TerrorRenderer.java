package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.client.models.TerrorModel;
import com.unusualmodding.opposing_force.entity.TerrorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TerrorRenderer extends GeoEntityRenderer<TerrorEntity> {

    public TerrorRenderer(EntityRendererProvider.Context context) {
        super(context, new TerrorModel());
    }
}
