package com.unusualmodding.opposing_force.client.renderer.layer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.TerrorModel;
import com.unusualmodding.opposing_force.entity.TerrorEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TerrorEyesLayer<T extends TerrorEntity, M extends TerrorModel<T>> extends EyesLayer<T, M> {

    private static final RenderType EYES = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/terror_eyes.png"));

    public TerrorEyesLayer(RenderLayerParent<T, M> parentModel) {
        super(parentModel);
    }

    public RenderType renderType() {
        return EYES;
    }
}


