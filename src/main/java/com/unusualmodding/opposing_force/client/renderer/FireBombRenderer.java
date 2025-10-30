package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.entity.projectile.FireBomb;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class FireBombRenderer extends AbstractBombRenderer<FireBomb> {

    private static final ResourceLocation FIRE_BOMB = modPrefix("textures/item/fire_bomb.png");

    public FireBombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FireBomb bomb) {
        return FIRE_BOMB;
    }
}
