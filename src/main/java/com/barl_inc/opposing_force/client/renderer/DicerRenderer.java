package com.barl_inc.opposing_force.client.renderer;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.entity.DicerModel;
import com.barl_inc.opposing_force.client.renderer.layers.DicerVisorLayer;
import com.barl_inc.opposing_force.entity.Dicer;
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
public class DicerRenderer extends MobRenderer<Dicer, DicerModel> {

    private static final ResourceLocation DICER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/dicer.png");
    private static final ResourceLocation ARCH_DICER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/arch_dicer.png");
    private static final ResourceLocation GIGAN = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/gigan.png");

    public DicerRenderer(EntityRendererProvider.Context context) {
        super(context, new DicerModel(context.bakeLayer(OPModelLayers.DICER)), 0.5F);
        this.addLayer(new DicerVisorLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Dicer entity) {
        if (entity.getName().getString().contains("gigan")) return GIGAN;
        return entity.isElite() ? ARCH_DICER : DICER;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Dicer entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
