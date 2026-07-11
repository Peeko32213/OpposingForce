package com.barl_inc.opposing_force.client.renderer;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.entity.VoltModel;
import com.barl_inc.opposing_force.client.renderer.layers.VoltChargedLayer;
import com.barl_inc.opposing_force.client.renderer.layers.VoltGlowLayer;
import com.barl_inc.opposing_force.entity.Volt;
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
public class VoltRenderer extends MobRenderer<Volt, VoltModel> {

    private static final ResourceLocation VOLT = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/volt/volt.png");
    private static final ResourceLocation QUASAR_VOLT = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/volt/quasar_volt.png");

    public VoltRenderer(EntityRendererProvider.Context context) {
        super(context, new VoltModel(context.bakeLayer(OPModelLayers.VOLT)), 0.5F);
        this.addLayer(new VoltGlowLayer(this));
        this.addLayer(new VoltChargedLayer(this, context.getModelSet()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Volt entity) {
        return entity.isElite() ? QUASAR_VOLT : VOLT;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Volt entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
