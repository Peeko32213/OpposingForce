package com.unusualmodding.opposing_force.registry;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.unusualmodding.opposing_force.OpposingForce.MOD_ID;

@OnlyIn(Dist.CLIENT)
public interface  OPEntityModelLayers {

    ModelLayerLocation DICER_HEAD = main("dicer_head");


    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(new ResourceLocation(MOD_ID, id), name);
    }

    private static ModelLayerLocation main(String id) {
        return register(id, "main");
    }

}
