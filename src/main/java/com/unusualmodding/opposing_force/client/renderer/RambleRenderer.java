package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.RambleModel;
import com.unusualmodding.opposing_force.entity.RambleEntity;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class RambleRenderer extends MobRenderer<RambleEntity, RambleModel<RambleEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/ramble.png");

    public RambleRenderer(EntityRendererProvider.Context context) {
        super(context, new RambleModel<>(context.bakeLayer(OPModelLayers.RAMBLE_LAYER)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(RambleEntity entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(RambleEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
