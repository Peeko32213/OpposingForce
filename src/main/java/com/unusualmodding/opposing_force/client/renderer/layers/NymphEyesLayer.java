package com.unusualmodding.opposing_force.client.renderer.layers;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.NymphModel;
import com.unusualmodding.opposing_force.entity.Nymph;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class NymphEyesLayer extends EyesLayer<Nymph, NymphModel> {

    private static final RenderType EYES = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/nymph/nymph_eyes.png"));

    public NymphEyesLayer(RenderLayerParent<Nymph, NymphModel> parentModel) {
        super(parentModel);
    }

    @Override
    public @NotNull RenderType renderType() {
        return EYES;
    }
}