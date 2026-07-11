package com.barl_inc.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.barl_inc.opposing_force.client.models.entity.FrowzyModel;
import com.barl_inc.opposing_force.client.renderer.layers.FrowzyHelmetLayer;
import com.barl_inc.opposing_force.entity.Frowzy;
import com.barl_inc.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

import static com.barl_inc.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class FrowzyRenderer extends MobRenderer<Frowzy, FrowzyModel> {

    public FrowzyRenderer(EntityRendererProvider.Context context) {
        super(context, new FrowzyModel(context.bakeLayer(OPModelLayers.FROWZY)), 0.4F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new FrowzyHelmetLayer(this, context));
    }

    @Override
    protected void scale(Frowzy entity, @NotNull PoseStack poseStack, float partialTicks) {
        if (entity.isBaby()) {
            this.shadowRadius = 0.2F;
        } else {
            this.shadowRadius = 0.4F;
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Frowzy entity) {
        Frowzy.FrowzyVariant variant = Frowzy.FrowzyVariant.byId(entity.getVariant().getId());
        return modPrefix("textures/entity/frowzy/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(Frowzy entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(getTextureLocation(entity));

    }
}
