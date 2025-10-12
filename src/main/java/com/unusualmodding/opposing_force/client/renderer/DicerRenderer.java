package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.DicerModel;
import com.unusualmodding.opposing_force.client.renderer.layers.DicerVisorLayer;
import com.unusualmodding.opposing_force.entity.Dicer;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class DicerRenderer extends MobRenderer<Dicer, DicerModel> {

    private static final ResourceLocation DICER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/dicer.png");

    public DicerRenderer(EntityRendererProvider.Context context) {
        super(context, new DicerModel(context.bakeLayer(OPModelLayers.DICER)), 0.5F);
        this.addLayer(new DicerVisorLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Dicer entity) {
        return DICER;
    }

    @Override
    protected @Nullable RenderType getRenderType(Dicer entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(DICER);
    }
}
