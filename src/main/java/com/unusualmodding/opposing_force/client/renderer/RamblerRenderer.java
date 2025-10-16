package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.RamblerModel;
import com.unusualmodding.opposing_force.entity.Rambler;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class RamblerRenderer extends MobRenderer<Rambler, RamblerModel> {

    private static final ResourceLocation RAMBLER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/rambler.png");

    public RamblerRenderer(EntityRendererProvider.Context context) {
        super(context, new RamblerModel(context.bakeLayer(OPModelLayers.RAMBLER)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(Rambler entity) {
        return RAMBLER;
    }

    @Override
    protected @Nullable RenderType getRenderType(Rambler entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(RAMBLER);
    }
}
