package com.unusualmodding.opposing_force.client.renderer.layer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class OPSkullLayers {

    public static final ModelLayerLocation DICER_HEAD_MODEL = createLocation("dicer_head_model", "main");

    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation("opposing_force", model), layer);
    }

}
