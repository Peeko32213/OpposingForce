package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.UmberSpiderModel;
import com.unusualmodding.opposing_force.client.renderer.layers.UmberSpiderEyesLayer;
import com.unusualmodding.opposing_force.entity.UmberSpider;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class UmberSpiderRenderer extends MobRenderer<UmberSpider, UmberSpiderModel> {

    private static final ResourceLocation UMBER_SPIDER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider/umber_spider.png");
    private static final ResourceLocation ELITE_UMBER_SPIDER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider/tenebrous_umber_spider.png");

    public UmberSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new UmberSpiderModel(context.bakeLayer(OPModelLayers.UMBER_SPIDER)), 0.8F);
        this.addLayer(new UmberSpiderEyesLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull UmberSpider entity) {
        return entity.isElite() ? ELITE_UMBER_SPIDER : UMBER_SPIDER;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull UmberSpider entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }

    @Override
    protected int getBlockLightLevel(@NotNull UmberSpider entity, @NotNull BlockPos pos) {
        return entity.isElite() ? 15 : super.getBlockLightLevel(entity, pos);
    }
}
