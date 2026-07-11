package com.barl_inc.opposing_force.client.renderer;

import com.barl_inc.opposing_force.entity.projectile.StratoArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StratoArrowRenderer extends ArrowRenderer<StratoArrow> {

	public static final ResourceLocation ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");

	public StratoArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull StratoArrow entity) {
		return ARROW;
	}
}