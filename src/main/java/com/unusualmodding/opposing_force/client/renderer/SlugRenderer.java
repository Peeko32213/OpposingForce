package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.SlugModel;
import com.unusualmodding.opposing_force.entity.Slug;
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
public class SlugRenderer extends MobRenderer<Slug, SlugModel> {

    private static final ResourceLocation SLUG = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/slug/slug.png");
    private static final ResourceLocation ELITE_SLUG = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/slug/vile_slug.png");

    public SlugRenderer(EntityRendererProvider.Context context) {
        super(context, new SlugModel(context.bakeLayer(OPModelLayers.SLUG)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Slug entity) {
        return entity.isElite() ? ELITE_SLUG : SLUG;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Slug entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }

    protected void scale(Slug entity, PoseStack pPoseStack, float pPartialTickTime) {
        int size = entity.getSlugSize();
        float scale = 1.0F + 0.15F * (float) size;
        pPoseStack.scale(scale, scale, scale);
    }
}