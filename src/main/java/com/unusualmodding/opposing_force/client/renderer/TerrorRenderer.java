package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.TerrorModel;
import com.unusualmodding.opposing_force.client.renderer.layer.TerrorGlowLayer;
import com.unusualmodding.opposing_force.entity.Terror;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TerrorRenderer extends MobRenderer<Terror, TerrorModel<Terror>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/terror/terror.png");

    public TerrorRenderer(EntityRendererProvider.Context context) {
        super(context, new TerrorModel<>(context.bakeLayer(OPModelLayers.TERROR)), 0.5F);
        this.addLayer(new TerrorGlowLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Terror entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Terror entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
