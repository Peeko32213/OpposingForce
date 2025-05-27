package com.unusualmodding.opposing_force.client.renderer.layer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.UmberSpiderModel;
import com.unusualmodding.opposing_force.entity.UmberSpiderEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UmberSpiderEyesLayer<T extends UmberSpiderEntity, M extends UmberSpiderModel<T>> extends EyesLayer<T, M> {

    private static final RenderType SPIDER_EYES = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider_eyes.png"));

    public UmberSpiderEyesLayer(RenderLayerParent<T, M> parentModel) {
        super(parentModel);
    }

    public RenderType renderType() {
        return SPIDER_EYES;
    }
}