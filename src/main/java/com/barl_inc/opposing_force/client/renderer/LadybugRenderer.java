package com.barl_inc.opposing_force.client.renderer;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.entity.LadybugModel;
import com.barl_inc.opposing_force.entity.Ladybug;
import com.barl_inc.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LadybugRenderer extends MobRenderer<Ladybug, LadybugModel> {

    private static final ResourceLocation LADYBUG = OpposingForce.modPrefix("textures/entity/ladybug/ladybug.png");
    private static final ResourceLocation ELITE_LADYBUG = OpposingForce.modPrefix("textures/entity/ladybug/shady_ladybug.png");

    public LadybugRenderer(EntityRendererProvider.Context context) {
        super(context, new LadybugModel(context.bakeLayer(OPModelLayers.LADYBUG)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Ladybug entity) {
        return entity.isElite() ? ELITE_LADYBUG : LADYBUG;
    }
}
