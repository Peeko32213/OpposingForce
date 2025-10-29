package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.entity.projectile.KineticBomb;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class KineticBombRenderer extends AbstractBombRenderer<KineticBomb> {

    private static final ResourceLocation KINETIC_BOMB = modPrefix("textures/item/kinetic_bomb.png");

    public KineticBombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull KineticBomb bomb) {
        return KINETIC_BOMB;
    }
}
