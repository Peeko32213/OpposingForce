package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.TartModel;
import com.unusualmodding.opposing_force.entity.Tart;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TartRenderer extends MobRenderer<Tart, TartModel> {

    private static final ResourceLocation TART = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/tart.png");

    public TartRenderer(EntityRendererProvider.Context context) {
        super(context, new TartModel(context.bakeLayer(OPModelLayers.TART)), 0.2F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Tart entity) {
        return TART;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Tart entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TART);
    }
}