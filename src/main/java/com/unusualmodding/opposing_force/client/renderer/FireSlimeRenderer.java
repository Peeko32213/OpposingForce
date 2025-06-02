package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.FireSlimeModel;
import com.unusualmodding.opposing_force.entity.FireSlimeEntity;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class FireSlimeRenderer extends MobRenderer<FireSlimeEntity, FireSlimeModel<FireSlimeEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/fire_slime.png");

    public FireSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new FireSlimeModel<>(context.bakeLayer(OPModelLayers.FIRE_SLIME_LAYER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(FireSlimeEntity entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(FireSlimeEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
