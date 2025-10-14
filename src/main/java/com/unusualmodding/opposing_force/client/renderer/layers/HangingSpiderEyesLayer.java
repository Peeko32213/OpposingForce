package com.unusualmodding.opposing_force.client.renderer.layers;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.HangingSpiderModel;
import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HangingSpiderEyesLayer extends EyesLayer<HangingSpider, HangingSpiderModel> {

    private static final RenderType EYES = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/hanging_spider/hanging_spider_eyes.png"));

    public HangingSpiderEyesLayer(RenderLayerParent<HangingSpider, HangingSpiderModel> parentModel) {
        super(parentModel);
    }

    public RenderType renderType() {
        return EYES;
    }
}