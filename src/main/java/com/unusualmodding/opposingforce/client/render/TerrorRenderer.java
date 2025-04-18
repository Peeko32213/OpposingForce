package com.unusualmodding.opposingforce.client.render;

import com.unusualmodding.opposingforce.client.model.TerrorModel;
import com.unusualmodding.opposingforce.common.entity.TerrorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TerrorRenderer extends GeoEntityRenderer<TerrorEntity> {

    public TerrorRenderer(EntityRendererProvider.Context context) {
        super(context, new TerrorModel());
    }
}
