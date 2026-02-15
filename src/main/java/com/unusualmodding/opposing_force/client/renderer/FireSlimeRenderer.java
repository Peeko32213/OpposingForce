package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.FireSlimeModel;
import com.unusualmodding.opposing_force.entity.FireSlime;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class FireSlimeRenderer extends MobRenderer<FireSlime, FireSlimeModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/fire_slime/fire_slime.png");
    private static final ResourceLocation TEXTURE_CHUD = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/fire_slime/chud.png");

    public FireSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new FireSlimeModel(context.bakeLayer(OPModelLayers.FIRE_SLIME)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FireSlime entity) {
        return entity.getVariant() == FireSlime.FireSlimeVariant.CHUD ? TEXTURE_CHUD : TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull FireSlime entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutout(this.getTextureLocation(entity));
    }

    @Override
    protected void scale(FireSlime slime, PoseStack poseStack, float scale) {
        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0F, 0.005F, 0.0F);
        float squish = Mth.lerp(scale, slime.oSquish, slime.squish) / (0.5F + 1.0F);
        float squishScale = 1.0F / (squish + 1.0F);
        poseStack.scale(squishScale, 1.0F / squishScale, squishScale);
    }

    @Override
    protected int getBlockLightLevel(@NotNull FireSlime entity, @NotNull BlockPos pos) {
        return 15;
    }
}
