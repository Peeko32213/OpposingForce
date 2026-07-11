package com.barl_inc.opposing_force.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

public class SmoothAnimationState extends AnimationState {

    public static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public float factorOld;
    public float factor;
    public final float lerpSpeed;

    public SmoothAnimationState(float lerpSpeed) {
        this.lerpSpeed = lerpSpeed;
    }

    public SmoothAnimationState() {
        this(0.5F);
    }

    @Override
    public void animateWhen(boolean condition, int tickCount) {
        float target = condition ? 1.0F : 0.0F;
        this.factorOld = this.factor;
        this.factor += (target - this.factor) * this.lerpSpeed;
        this.factor = Mth.clamp(this.factor, 0.0F, 1.0F);
        if (condition) {
            this.startIfStopped(tickCount);
        } else {
            this.stop();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float factor() {
        return Mth.lerp(Minecraft.getInstance().getPartialTick(), this.factorOld, this.factor);
    }

    @OnlyIn(Dist.CLIENT)
    public void animate(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks) {
        this.animate(model, definition, ageInTicks, this.factor(), 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public void animate(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float speed) {
        this.animate(model, definition, ageInTicks, this.factor(), speed);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateIdle(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float limbSwingAmount, float animationScaleFactor, SmoothAnimationState... states) {
        this.animateIdle(model, definition, ageInTicks, limbSwingAmount, animationScaleFactor, 0.01F, states);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateIdle(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float limbSwingAmount, float animationScaleFactor, float threshold, SmoothAnimationState... states) {
        float totalFactor = 1.0F;
        float extraFactor = 0.0F;
        for (SmoothAnimationState state : states) {
            float factor = state.factor();
            totalFactor *= 1.0F - factor;
            extraFactor += factor;
        }
        float limb = Math.min((limbSwingAmount * (totalFactor + extraFactor)) * animationScaleFactor, 1.0F);
        this.animate(model, definition, ageInTicks, Math.max(this.factor() * (1.0F - limb), threshold), 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public void animate(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float factor, float speed) {
        this.updateTime(ageInTicks, speed);
        KeyframeAnimations.animate(model, definition, this.getAccumulatedTime(), factor, ANIMATION_VECTOR_CACHE);
    }
}