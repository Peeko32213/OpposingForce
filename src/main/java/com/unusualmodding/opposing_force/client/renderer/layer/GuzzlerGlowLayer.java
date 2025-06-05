package com.unusualmodding.opposing_force.client.renderer.layer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.GuzzlerModel;
import com.unusualmodding.opposing_force.entity.Guzzler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuzzlerGlowLayer<T extends Guzzler, M extends GuzzlerModel<T>> extends EyesLayer<T, M> {

    private static final RenderType GLOW = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/guzzler_glow.png"));

    public GuzzlerGlowLayer(RenderLayerParent<T, M> parentModel) {
        super(parentModel);
    }

    public RenderType renderType() {
        return GLOW;
    }
}


