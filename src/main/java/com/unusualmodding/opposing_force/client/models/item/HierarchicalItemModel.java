package com.unusualmodding.opposing_force.client.models.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.items.ItemAnimationState;
import com.unusualmodding.opposing_force.client.animations.items.ItemKeyframeAnimations;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.function.Function;

public abstract class HierarchicalItemModel extends Model {

    private final Vector3f ANIMATION_VECTOR_CACHE;

    protected HierarchicalItemModel() {
        this(RenderType::entityCutoutNoCull);
    }

    protected HierarchicalItemModel(Function<ResourceLocation, RenderType> pRenderType) {
        super(pRenderType);
        this.ANIMATION_VECTOR_CACHE = new Vector3f();
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer consumer, int packedLight, int overlay, float r, float g, float b, float a) {
        this.root().render(poseStack, consumer, packedLight, overlay, r, g, b, a);
    }

    public abstract ModelPart root();

    public Optional<ModelPart> getAnyDescendantWithName(String name) {
        return name.equals("root") ? Optional.of(this.root()) : this.root().getAllParts().filter((part) -> part.hasChild(name)).findFirst().map((part) -> part.getChild(name));
    }

    protected void animate(Entity entity, ItemAnimationState nonEAnimationState, AnimationDefinition definition, float ageInTicks) {
        this.animate(entity, nonEAnimationState, definition, ageInTicks, 1.0F);
    }

    protected void animate(Entity entity, ItemAnimationState nonEAnimationState, AnimationDefinition definition, float ageInTicks, float speed) {
        nonEAnimationState.updateTime(ageInTicks, speed);
        if (nonEAnimationState.matchesViewingEntity(entity)) {
            nonEAnimationState.ifStarted((state) -> ItemKeyframeAnimations.animate(this, definition, state.getAccumulatedTime(), 1.0F, this.ANIMATION_VECTOR_CACHE));
        }
    }

    public void setupAnim(Entity entity, ItemStack stack, float ageInTicks) {
    }
}