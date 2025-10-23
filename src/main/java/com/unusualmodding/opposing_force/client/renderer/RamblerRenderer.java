package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.client.models.entity.RamblerModel;
import com.unusualmodding.opposing_force.client.renderer.layers.RamblerSkullLayer;
import com.unusualmodding.opposing_force.entity.Rambler;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class RamblerRenderer extends MobRenderer<Rambler, RamblerModel> {

    private static final ResourceLocation RAMBLER = modPrefix("textures/entity/rambler/rambler.png");

    public RamblerRenderer(EntityRendererProvider.Context context) {
        super(context, new RamblerModel(context.bakeLayer(OPModelLayers.RAMBLER)), 0.8F);

        this.addLayer(new RamblerSkullLayer(this, skullModelPart -> skullModelPart.middle_skull, entity -> {
                    Rambler.RamblerSkulls skull = Rambler.RamblerSkulls.getVariantId(entity.getMiddleSkull());
                    return modPrefix("textures/entity/rambler/skulls/" + skull.getSerializedName() + ".png");
                }));

        this.addLayer(new RamblerSkullLayer(this,
                skullModelPart -> skullModelPart.left_skull, entity -> {
                    Rambler.RamblerSkulls skull = Rambler.RamblerSkulls.getVariantId(entity.getLeftSkull());
                    return modPrefix("textures/entity/rambler/skulls/" + skull.getSerializedName() + ".png");
                }));

        this.addLayer(new RamblerSkullLayer(this,
                skullModelPart -> skullModelPart.right_skull, entity -> {
                    Rambler.RamblerSkulls skull = Rambler.RamblerSkulls.getVariantId(entity.getRightSkull());
                    return modPrefix("textures/entity/rambler/skulls/" + skull.getSerializedName() + ".png");
                }));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Rambler entity) {
        return RAMBLER;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Rambler entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(RAMBLER);
    }
}
