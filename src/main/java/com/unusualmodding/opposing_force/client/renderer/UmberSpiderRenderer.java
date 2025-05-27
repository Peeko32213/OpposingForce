package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.UmberSpiderModel;
import com.unusualmodding.opposing_force.client.renderer.layer.UmberSpiderEyesLayer;
import com.unusualmodding.opposing_force.entity.UmberSpiderEntity;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class UmberSpiderRenderer extends MobRenderer<UmberSpiderEntity, UmberSpiderModel<UmberSpiderEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider.png");

    public UmberSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new UmberSpiderModel<>(context.bakeLayer(OPModelLayers.UMBER_SPIDER_LAYER)), 0.8F);
        this.addLayer(new UmberSpiderEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(UmberSpiderEntity entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(UmberSpiderEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
