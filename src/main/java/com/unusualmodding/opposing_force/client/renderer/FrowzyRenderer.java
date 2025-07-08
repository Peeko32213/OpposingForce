package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.FrowzyModel;
import com.unusualmodding.opposing_force.client.renderer.layers.FrowzyItemInHandLayer;
import com.unusualmodding.opposing_force.entity.Frowzy;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class FrowzyRenderer extends MobRenderer<Frowzy, FrowzyModel<Frowzy>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/frowzy.png");

    public FrowzyRenderer(EntityRendererProvider.Context context) {
        super(context, new FrowzyModel(context.bakeLayer(OPModelLayers.FROWZY)), 0.4F);
//        this.layers.removeIf(entry -> entry instanceof ItemInHandLayer);
//        this.addLayer(new FrowzyItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Frowzy entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(Frowzy entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
