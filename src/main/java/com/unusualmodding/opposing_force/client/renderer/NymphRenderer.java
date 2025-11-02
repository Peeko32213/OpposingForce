package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.NymphModel;
import com.unusualmodding.opposing_force.client.renderer.layers.NymphEyesLayer;
import com.unusualmodding.opposing_force.entity.Nymph;
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
public class NymphRenderer extends MobRenderer<Nymph, NymphModel> {

    private static final ResourceLocation NYMPH = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/nymph/nymph.png");

    public NymphRenderer(EntityRendererProvider.Context context) {
        super(context, new NymphModel(context.bakeLayer(OPModelLayers.NYMPH)), 0.5F);
        this.addLayer(new NymphEyesLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Nymph entity) {
        return NYMPH;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Nymph entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(NYMPH);
    }
}