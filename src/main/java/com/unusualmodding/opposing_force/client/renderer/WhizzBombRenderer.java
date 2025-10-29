package com.unusualmodding.opposing_force.client.renderer;

import com.unusualmodding.opposing_force.entity.projectile.WhizzBomb;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class WhizzBombRenderer extends AbstractBombRenderer<WhizzBomb> {

    private static final ResourceLocation WHIZZ_BOMB = modPrefix("textures/item/whizz_bomb.png");

    public WhizzBombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WhizzBomb bomb) {
        return WHIZZ_BOMB;
    }
}
