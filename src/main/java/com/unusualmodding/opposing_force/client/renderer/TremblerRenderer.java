package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.TremblerModel;
import com.unusualmodding.opposing_force.entity.Trembler;
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
public class TremblerRenderer extends MobRenderer<Trembler, TremblerModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/trembler/trembler.png");
    private static final ResourceLocation ELITE_TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/trembler/turbo_trembler.png");

    public TremblerRenderer(EntityRendererProvider.Context context) {
        super(context, new TremblerModel(context.bakeLayer(OPModelLayers.TREMBLER)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Trembler entity) {
        return entity.isElite() ? ELITE_TEXTURE : TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Trembler entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
