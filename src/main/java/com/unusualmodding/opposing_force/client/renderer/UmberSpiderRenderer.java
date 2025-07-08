package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.UmberSpiderModel;
import com.unusualmodding.opposing_force.client.renderer.layers.UmberSpiderEyesLayer;
import com.unusualmodding.opposing_force.entity.UmberSpider;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class UmberSpiderRenderer extends MobRenderer<UmberSpider, UmberSpiderModel<UmberSpider>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider/umber_spider.png");

    public UmberSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new UmberSpiderModel<>(context.bakeLayer(OPModelLayers.UMBER_SPIDER)), 0.8F);
        this.addLayer(new UmberSpiderEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(UmberSpider entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(UmberSpider entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
