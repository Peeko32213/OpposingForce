package com.unusualmodding.opposing_force.client.renderer.layers;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.UmberSpiderModel;
import com.unusualmodding.opposing_force.entity.UmberSpider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class UmberSpiderEyesLayer extends EyesLayer<UmberSpider, UmberSpiderModel> {

    private static final RenderType EYES = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider/umber_spider_eyes.png"));

    public UmberSpiderEyesLayer(RenderLayerParent<UmberSpider, UmberSpiderModel> parentModel) {
        super(parentModel);
    }

    @Override
    public @NotNull RenderType renderType() {
        return EYES;
    }
}