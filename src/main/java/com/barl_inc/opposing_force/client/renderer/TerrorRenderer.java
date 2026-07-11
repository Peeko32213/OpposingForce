package com.barl_inc.opposing_force.client.renderer;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.entity.TerrorModel;
import com.barl_inc.opposing_force.client.renderer.layers.TerrorGlowLayer;
import com.barl_inc.opposing_force.entity.Terror;
import com.barl_inc.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class TerrorRenderer extends MobRenderer<Terror, TerrorModel> {

    private static final ResourceLocation TERROR = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/terror/terror.png");
    private static final ResourceLocation ANTEDILUVIAN_TERROR = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/terror/antediluvian_terror.png");

    public TerrorRenderer(EntityRendererProvider.Context context) {
        super(context, new TerrorModel(context.bakeLayer(OPModelLayers.TERROR)), 0.5F);
        this.addLayer(new TerrorGlowLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Terror entity) {
        return entity.isElite() ? ANTEDILUVIAN_TERROR : TERROR;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Terror entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
