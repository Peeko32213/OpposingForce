package com.peeko32213.hole.client.render;

import com.peeko32213.hole.client.model.TerrorModel;
import com.peeko32213.hole.common.entity.EntityTerror;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TerrorRenderer extends GeoEntityRenderer<EntityTerror> {

    public TerrorRenderer(EntityRendererProvider.Context context) {
        super(context, new TerrorModel());
    }
}
