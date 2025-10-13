package com.unusualmodding.opposing_force.client.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DicerAnimations {

	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(16.0F).looping()
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-5.0F, 5.0F, -4.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-7.17F, 5.0F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-5.0F, 5.0F, -4.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(-7.17F, 5.0F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(-5.0F, 5.0F, -4.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(-7.17F, 5.0F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(-5.0F, 5.0F, -4.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(-7.17F, 5.0F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(-6.25F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(-2.83F, -5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(-5.0F, -5.0F, 4.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(-7.17F, -5.0F, 0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.degreeVec(-2.83F, -5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(-5.0F, -5.0F, 4.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(-7.17F, -5.0F, 0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.degreeVec(-2.83F, -5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(-5.0F, -5.0F, 4.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(-7.17F, -5.0F, 0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.degreeVec(-2.83F, -5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(-5.0F, -5.0F, 4.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(-7.17F, -5.0F, 0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(-6.25F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, -0.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.posVec(0.0F, -0.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.posVec(0.0F, -0.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.posVec(0.0F, -0.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.posVec(0.0F, -0.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.posVec(0.0F, -0.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.posVec(0.0F, -0.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.posVec(0.0F, -0.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, -0.9F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 9.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(7.17F, 0.0F, 7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(7.17F, 0.0F, 0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 0.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 2.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(7.17F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(7.17F, 0.0F, 5.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(6.25F, 0.0F, 5.54F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, -9.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, -12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(7.17F, 0.0F, -7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, -7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(7.17F, 0.0F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, -0.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, -2.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(7.17F, 0.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(5.0F, 0.0F, -7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(7.17F, 0.0F, -5.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(6.25F, 0.0F, -5.54F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 9.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-2.17F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(2.17F, 0.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-2.17F, 0.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(2.17F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(-2.17F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(2.17F, 0.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(-2.17F, 0.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(2.17F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(-2.17F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(2.17F, 0.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(-2.17F, 0.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(2.17F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(-2.17F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(2.17F, 0.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(-2.17F, 0.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(2.17F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 7.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 7.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 7.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.5417F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 10.96F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 11.76F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.25F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 17.59F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(-17.78F, 31.11F, 15.54F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(-160.0F, 40.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(-158.72F, 40.0F, -7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(-157.44F, 40.0F, -2.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(-154.87F, 40.0F, -7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(-153.59F, 40.0F, -2.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(-151.03F, 40.0F, -7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(-149.74F, 40.0F, -2.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.degreeVec(-148.46F, 40.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.5F, KeyframeAnimations.degreeVec(-147.5F, 40.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(-108.33F, 26.67F, -1.44F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.0F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(-20.0F, 10.0F, 9.28F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.5417F, KeyframeAnimations.posVec(0.0F, -0.03F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.posVec(0.0F, -0.15F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.25F, KeyframeAnimations.posVec(0.0F, -1.65F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.posVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.posVec(0.0F, -7.52F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.posVec(0.0F, -8.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.posVec(0.0F, -7.52F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.posVec(0.0F, -8.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.posVec(0.0F, -7.52F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.posVec(0.0F, -8.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.5F, KeyframeAnimations.posVec(0.0F, -7.8F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.posVec(0.0F, -6.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.posVec(0.0F, -0.52F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-158.72F, -40.0F, 7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-157.44F, -40.0F, 2.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-154.87F, -40.0F, 7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(-153.59F, -40.0F, 2.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(-151.03F, -40.0F, 7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(-149.74F, -40.0F, 2.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(-148.46F, -40.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.5F, KeyframeAnimations.degreeVec(-147.5F, -40.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(-108.33F, -26.67F, 1.44F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.0F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(-20.0F, -10.0F, -9.28F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -7.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -7.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -7.83F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.5417F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -10.96F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -11.76F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.25F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -17.59F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(-17.78F, -31.11F, -15.54F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, -7.52F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, -8.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, -7.52F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.posVec(0.0F, -8.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.posVec(0.0F, -7.52F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.posVec(0.0F, -8.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.5F, KeyframeAnimations.posVec(0.0F, -7.8F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.posVec(0.0F, -6.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.posVec(0.0F, -0.52F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.posVec(0.0F, 0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.5417F, KeyframeAnimations.posVec(0.0F, -0.03F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.posVec(0.0F, -0.15F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.25F, KeyframeAnimations.posVec(0.0F, -1.65F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.posVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(107.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.8333F, KeyframeAnimations.degreeVec(-5.0F, 10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(5.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(5.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.75F, KeyframeAnimations.degreeVec(10.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, -0.08F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, -0.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.posVec(0.0F, -0.08F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.posVec(0.0F, -0.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.posVec(0.0F, -0.08F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.posVec(0.0F, -0.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.posVec(0.0F, -0.08F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.posVec(0.0F, -0.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.posVec(0.0F, -0.25F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.8333F, KeyframeAnimations.posVec(-1.5F, 1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.posVec(-1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.posVec(-1.0F, -0.01F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.posVec(-1.0F, -0.44F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.posVec(-1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.posVec(-1.0F, -0.01F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.posVec(-1.0F, -0.44F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.posVec(-1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.posVec(-1.0F, -0.01F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.posVec(-1.0F, -0.44F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.posVec(-1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.posVec(-1.0F, -0.01F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.posVec(-1.0F, -0.44F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.posVec(-1.0F, -0.45F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.75F, KeyframeAnimations.degreeVec(10.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.8333F, KeyframeAnimations.degreeVec(-5.0F, -10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(1.0F, -0.01F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(1.0F, -0.44F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.posVec(1.0F, -0.01F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.posVec(1.0F, -0.44F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.posVec(1.0F, -0.01F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.posVec(1.0F, -0.44F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.posVec(1.0F, -0.01F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.posVec(1.0F, -0.44F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.posVec(1.0F, -0.45F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.posVec(0.0F, -0.08F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.posVec(0.0F, -0.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.posVec(0.0F, -0.08F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.posVec(0.0F, -0.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.posVec(0.0F, -0.08F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.posVec(0.0F, -0.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.posVec(0.0F, -0.08F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.posVec(0.0F, -0.17F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.posVec(0.0F, -0.25F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.8333F, KeyframeAnimations.posVec(1.5F, 1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailStart", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-45.0F, 27.32F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-45.0F, -7.32F, 5.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-45.0F, 27.32F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(-45.0F, -7.32F, 5.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(-45.0F, 27.32F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(-45.0F, -7.32F, 5.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(-45.0F, 27.32F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(-45.0F, -7.32F, 5.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, -14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(-45.0F, -27.32F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(-45.0F, 7.32F, -5.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, -14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(-45.0F, -27.32F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(-45.0F, 7.32F, -5.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, -14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(-45.0F, -27.32F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(-45.0F, 7.32F, -5.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, -14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(-45.0F, -27.32F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(-45.0F, 7.32F, -5.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailMid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(45.0F, 20.0F, 28.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(45.0F, 45.98F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(45.0F, 20.0F, 28.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(45.0F, 45.98F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(45.0F, 20.0F, 28.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(45.0F, 45.98F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(45.0F, 20.0F, 28.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(45.0F, 45.98F, 20.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(45.0F, 35.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(45.0F, 5.98F, -11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(45.0F, -20.0F, -28.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(45.0F, -45.98F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.degreeVec(45.0F, 5.98F, -11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(45.0F, -20.0F, -28.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(45.0F, -45.98F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.degreeVec(45.0F, 5.98F, -11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(45.0F, -20.0F, -28.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(45.0F, -45.98F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.degreeVec(45.0F, 5.98F, -11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(45.0F, -20.0F, -28.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(45.0F, -45.98F, -20.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(45.0F, -35.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailBack", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(20.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.6667F, KeyframeAnimations.degreeVec(20.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.3333F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.6667F, KeyframeAnimations.degreeVec(20.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(5.3333F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.6667F, KeyframeAnimations.degreeVec(20.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.3333F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(20.0F, 20.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.6667F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.3333F, KeyframeAnimations.degreeVec(20.0F, -17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(10.6667F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(11.3333F, KeyframeAnimations.degreeVec(20.0F, -17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(12.6667F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(13.3333F, KeyframeAnimations.degreeVec(20.0F, -17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(14.6667F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.3333F, KeyframeAnimations.degreeVec(20.0F, -17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(15.5F, KeyframeAnimations.degreeVec(20.0F, -20.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(16.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

	public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(2.0F).looping()
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-5.0F, -5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-5.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.33F, 4.33F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-4.33F, -4.33F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(4.33F, -4.33F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-4.33F, 4.33F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(4.33F, 4.33F, 4.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-4.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(4.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-4.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(4.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(18.66F, -42.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, -37.5F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.34F, -35.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.34F, -37.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(10.0F, -42.5F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(18.66F, -45.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(18.66F, -42.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(-1.0F, 0.13F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(-1.0F, 0.13F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(-1.0F, 1.87F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(-1.0F, 1.87F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(-1.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(106.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(106.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(88.84F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(88.84F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.34F, 37.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, 42.5F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(18.66F, 45.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(18.66F, 42.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(10.0F, 37.5F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1.34F, 35.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.34F, 37.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(1.0F, 1.87F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(1.0F, 1.87F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(1.0F, 0.13F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(1.0F, 0.13F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(1.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(88.84F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(88.84F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(106.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(106.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(40.0F, -2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(35.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-15.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(25.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5833F, KeyframeAnimations.posVec(-1.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.posVec(-0.5F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(25.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(40.0F, 2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5833F, KeyframeAnimations.degreeVec(35.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-15.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.5F, -0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5833F, KeyframeAnimations.posVec(1.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailStart", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-28.66F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-11.34F, 17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-28.66F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-20.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-11.34F, -17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-28.66F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailMid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(8.66F, -17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-8.66F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(8.66F, 17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-8.66F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(8.66F, -17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("BodyLower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(20.0F, -10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailBack", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(8.66F, -17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-8.66F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(8.66F, 17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-8.66F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

	public static final AnimationDefinition RUN = AnimationDefinition.Builder.withLength(0.5F).looping()
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(25.0F, -5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(25.0F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -3.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -3.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -2.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -3.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, -3.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -2.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(44.33F, 4.33F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(44.33F, 2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(35.67F, -2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(35.67F, -4.33F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(40.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(44.33F, -4.33F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(44.33F, -2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(35.67F, 2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(35.67F, 4.33F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(40.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(44.33F, 4.33F, 4.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-49.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-49.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-40.67F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-40.67F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-49.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-49.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-40.67F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-40.67F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(29.33F, 20.0F, 53.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(29.33F, 19.33F, 55.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(25.0F, 17.5F, 53.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(20.67F, 15.0F, 50.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(20.67F, 12.5F, 45.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(25.0F, 10.67F, 40.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(29.33F, 10.0F, 36.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(29.33F, 10.67F, 35.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(25.0F, 12.5F, 36.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(20.67F, 15.0F, 40.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(20.67F, 17.5F, 45.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(25.0F, 19.33F, 50.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(29.33F, 20.0F, 53.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.posVec(1.0F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(1.0F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(1.0F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(1.0F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(1.0F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(1.0F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.posVec(1.0F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(17.32F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(17.32F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-17.32F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-17.32F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(17.32F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-17.32F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-17.32F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(17.32F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(29.33F, -10.0F, -36.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(29.33F, -10.67F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(25.0F, -12.5F, -45.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(20.67F, -15.0F, -50.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(20.67F, -17.5F, -53.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(25.0F, -19.33F, -55.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(29.33F, -20.0F, -53.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(29.33F, -19.33F, -50.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(25.0F, -17.5F, -45.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(20.67F, -15.0F, -40.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(20.67F, -12.5F, -36.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(25.0F, -10.67F, -35.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(29.33F, -10.0F, -36.34F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.posVec(-1.0F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(-1.0F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(-1.0F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-1.0F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(-1.0F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(-1.0F, 0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.posVec(-1.0F, -0.43F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-17.32F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-17.32F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(17.32F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(17.32F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(10.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-17.32F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(17.32F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(17.32F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-17.32F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(55.0F, -2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(35.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(15.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(45.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(55.0F, -2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -3.5F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(-1.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(-0.5F, -1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, -3.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -3.5F, 1.5F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(15.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(45.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(55.0F, 2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(35.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-15.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.posVec(0.5F, -1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -3.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -3.5F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(1.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailStart", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-50.0F, 5.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-40.0F, 8.66F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-35.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-40.0F, 8.66F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-50.0F, 5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-50.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-40.0F, -8.66F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(-35.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-40.0F, -8.66F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-50.0F, -5.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailMid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, -10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(5.67F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(5.67F, 10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(14.33F, 20.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(14.33F, 17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(5.67F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.67F, -10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(10.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(14.33F, -20.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(14.33F, -17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, -10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("BodyLower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(20.0F, -10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(20.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LegControl", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailBack", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.33F, -17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-4.33F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-4.33F, 10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(4.33F, 20.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(4.33F, 17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-4.33F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(-4.33F, -10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(4.33F, -20.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(4.33F, -17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

	public static final AnimationDefinition CLAW1 = AnimationDefinition.Builder.withLength(0.8333F)
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.degreeVec(20.0F, -30.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(10.0F, 50.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.posVec(0.0F, -0.5F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.posVec(0.0F, -1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 9.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.degreeVec(20.0F, 30.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(-5.0F, 50.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 9.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.degreeVec(10.0F, -30.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(20.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-52.5F, 20.0F, 70.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(50.0F, 17.5F, 57.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(1.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(2.0F, -2.4F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.posVec(-1.0F, -0.4F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.scaleVec(1.0F, 0.8F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-40.0F, 10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(30.0F, -10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-30.0F, -10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(30.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4861F, KeyframeAnimations.degreeVec(-60.0F, -180.0F, -100.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.degreeVec(-125.0F, -7.5F, 72.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4861F, KeyframeAnimations.posVec(0.0F, 1.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.posVec(2.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6944F, KeyframeAnimations.posVec(2.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.4861F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.scaleVec(1.0F, 1.2F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6597F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.5208F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6597F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6597F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.5208F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6597F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(10.0F, 30.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(20.0F, -20.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(-20.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6944F, KeyframeAnimations.degreeVec(-20.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7639F, KeyframeAnimations.degreeVec(-5.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(2.0F, 2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.posVec(1.0F, -1.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6944F, KeyframeAnimations.posVec(1.0F, -1.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7639F, KeyframeAnimations.posVec(1.0F, 2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailStart", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1389F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 4.3301F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3472F, KeyframeAnimations.degreeVec(-25.0F, -20.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6944F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7639F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 4.3301F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailMid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0694F, KeyframeAnimations.degreeVec(45.0F, -25.9808F, -8.6603F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3819F, KeyframeAnimations.degreeVec(70.0F, -30.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7292F, KeyframeAnimations.degreeVec(45.0F, 35.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7986F, KeyframeAnimations.degreeVec(45.0F, -25.9808F, -8.6603F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailBack", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(45.0F, -30.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, -30.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7639F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("BodyLower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LegControl", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

	public static final AnimationDefinition CLAW2 = AnimationDefinition.Builder.withLength(0.8333F)
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.83F, -5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.degreeVec(20.0F, 30.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(10.0F, -50.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-2.83F, -5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.posVec(0.0F, -0.5F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.posVec(0.0F, -1.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, -9.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.degreeVec(20.0F, -30.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(-5.0F, -50.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(2.83F, 0.0F, -9.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.degreeVec(10.0F, 30.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(20.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-160.0F, 40.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4861F, KeyframeAnimations.degreeVec(-60.0F, 180.0F, 100.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.degreeVec(-125.0F, 7.5F, -72.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-160.0F, 40.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4861F, KeyframeAnimations.posVec(0.0F, 1.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.posVec(-2.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6944F, KeyframeAnimations.posVec(-2.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.4861F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.scaleVec(1.0F, 1.2F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-40.0F, -10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(30.0F, 10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-30.0F, 10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(30.0F, -10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-52.5F, -20.0F, -70.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(50.0F, -17.5F, -57.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, -30.0F, -12.17F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(-1.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(-2.0F, -2.4F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.posVec(1.0F, -0.4F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.scaleVec(1.0F, 0.8F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6597F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.5208F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6597F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6597F, KeyframeAnimations.degreeVec(30.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.5208F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6597F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(20.0F, 20.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(-20.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6944F, KeyframeAnimations.degreeVec(-20.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7639F, KeyframeAnimations.degreeVec(-5.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(5.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(-2.0F, 2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.posVec(-1.0F, -1.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6944F, KeyframeAnimations.posVec(-1.0F, -1.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7639F, KeyframeAnimations.posVec(-1.0F, 2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(-1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(10.0F, -30.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailStart", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, -14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1389F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, -4.3301F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3472F, KeyframeAnimations.degreeVec(-25.0F, 20.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6944F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7639F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, -4.3301F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, -14.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailMid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 5.98F, -11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0694F, KeyframeAnimations.degreeVec(45.0F, 25.9808F, 8.6603F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3819F, KeyframeAnimations.degreeVec(70.0F, 30.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5903F, KeyframeAnimations.degreeVec(0.0F, 30.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7292F, KeyframeAnimations.degreeVec(45.0F, -35.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7986F, KeyframeAnimations.degreeVec(45.0F, 25.9808F, 8.6603F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(45.0F, 5.98F, -11.34F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailBack", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(45.0F, 30.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 30.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7639F, KeyframeAnimations.degreeVec(0.0F, -25.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("BodyLower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4514F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5556F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LegControl", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

	public static final AnimationDefinition LASER = AnimationDefinition.Builder.withLength(2.5F)
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1563F, KeyframeAnimations.degreeVec(-2.52F, 5.0F, -3.46F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3125F, KeyframeAnimations.degreeVec(-2.59F, 5.0F, -4.27F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4688F, KeyframeAnimations.degreeVec(-3.02F, 5.0F, -4.81F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5729F, KeyframeAnimations.degreeVec(-3.48F, 5.0F, -4.98F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(-8.75F, 5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7813F, KeyframeAnimations.degreeVec(-7.17F, 5.0F, -4.81F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9375F, KeyframeAnimations.degreeVec(-5.65F, 5.0F, -4.27F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0938F, KeyframeAnimations.degreeVec(-6.52F, 5.0F, -3.46F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-7.17F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4063F, KeyframeAnimations.degreeVec(-7.48F, 5.0F, -1.54F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5625F, KeyframeAnimations.degreeVec(-7.41F, 5.0F, -0.73F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.7188F, KeyframeAnimations.degreeVec(-6.98F, 5.0F, -0.19F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(-6.25F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0313F, KeyframeAnimations.degreeVec(-5.33F, 5.0F, -0.19F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1875F, KeyframeAnimations.degreeVec(-4.35F, 5.0F, -0.73F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.3438F, KeyframeAnimations.degreeVec(-3.16F, 5.0F, -2.02F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1563F, KeyframeAnimations.posVec(0.0F, -0.39F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3125F, KeyframeAnimations.posVec(0.0F, -0.2F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -0.07F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4063F, KeyframeAnimations.posVec(0.0F, -0.67F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5625F, KeyframeAnimations.posVec(0.0F, -0.7F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5729F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(-10.0F, -5.0F, 5.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.1875F, KeyframeAnimations.degreeVec(-10.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5729F, KeyframeAnimations.degreeVec(-10.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6771F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7292F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7813F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8854F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9375F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9896F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0417F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0938F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1458F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1979F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3021F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3542F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.4063F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5104F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5625F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6146F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7188F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7708F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8229F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.9271F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.9792F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0313F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0833F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.1354F, KeyframeAnimations.degreeVec(13.27F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.1875F, KeyframeAnimations.degreeVec(16.73F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.2396F, KeyframeAnimations.degreeVec(11.06F, -3.33F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.2917F, KeyframeAnimations.degreeVec(11.15F, -6.67F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.3438F, KeyframeAnimations.degreeVec(6.63F, -10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.3958F, KeyframeAnimations.degreeVec(5.58F, -13.33F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4479F, KeyframeAnimations.degreeVec(2.21F, -16.67F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1042F, KeyframeAnimations.degreeVec(-10.0F, 30.0F, 12.41F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-10.0F, 30.0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3125F, KeyframeAnimations.degreeVec(-10.0F, 30.0F, 12.41F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3646F, KeyframeAnimations.degreeVec(-10.0F, 30.0F, 12.31F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-40.0F, 32.0F, 11.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(-100.0F, 36.0F, 8.77F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(-160.0F, 40.0F, 6.25F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7292F, KeyframeAnimations.degreeVec(-160.0F, 40.0F, 5.65F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7813F, KeyframeAnimations.degreeVec(-160.0F, 40.0F, 5.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-166.67F, 23.33F, -1.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9375F, KeyframeAnimations.degreeVec(-180.0F, -10.0F, -15.65F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-180.56F, -10.56F, -15.69F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-181.67F, -11.67F, -15.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-182.78F, -12.78F, -14.72F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-183.89F, -13.89F, -13.28F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.7708F, KeyframeAnimations.degreeVec(-184.44F, -14.44F, -12.32F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(-185.0F, -15.0F, -11.25F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.9792F, KeyframeAnimations.degreeVec(-155.83F, -7.5F, -7.31F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1875F, KeyframeAnimations.degreeVec(-97.5F, 7.5F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.3958F, KeyframeAnimations.degreeVec(-39.17F, 22.5F, 8.43F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(-10.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.1042F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, -0.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3125F, KeyframeAnimations.posVec(0.0F, -0.35F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, -3.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.posVec(0.0F, -6.62F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5729F, KeyframeAnimations.posVec(0.0F, -8.16F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.posVec(0.14F, -8.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7292F, KeyframeAnimations.posVec(0.43F, -7.96F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.71F, -7.8F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9375F, KeyframeAnimations.posVec(1.0F, -7.65F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.posVec(1.0F, -7.18F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.1458F, KeyframeAnimations.posVec(1.0F, -6.76F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.posVec(1.0F, -6.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3542F, KeyframeAnimations.posVec(1.0F, -6.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.posVec(1.0F, -5.85F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.7708F, KeyframeAnimations.posVec(1.0F, -5.29F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.posVec(1.0F, -5.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.9792F, KeyframeAnimations.posVec(0.71F, -3.79F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0833F, KeyframeAnimations.posVec(0.43F, -2.44F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1875F, KeyframeAnimations.posVec(0.14F, -1.07F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2396F, KeyframeAnimations.posVec(0.0F, -0.37F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2917F, KeyframeAnimations.posVec(0.0F, -0.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.7813F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9375F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0833F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2396F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.7813F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9375F, KeyframeAnimations.degreeVec(110.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0833F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2396F, KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, -30.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0521F, KeyframeAnimations.degreeVec(10.0F, -30.0F, -9.67F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1042F, KeyframeAnimations.degreeVec(-24.0F, -32.0F, -6.35F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-92.0F, -36.0F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3125F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 6.77F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 7.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4688F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 7.31F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.degreeVec(-166.67F, -23.33F, 10.75F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(-180.0F, 10.0F, 17.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7292F, KeyframeAnimations.degreeVec(-176.67F, 9.44F, 17.97F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-173.33F, 8.89F, 18.28F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-166.67F, 7.78F, 18.47F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-160.0F, 6.67F, 18.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-153.33F, 5.56F, 18.19F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5625F, KeyframeAnimations.degreeVec(-150.0F, 5.0F, 18.23F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-131.18F, 0.88F, 14.31F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.7708F, KeyframeAnimations.degreeVec(-112.35F, -3.24F, 10.53F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(-93.53F, -7.35F, 6.91F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-55.88F, -15.59F, 0.19F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-18.23F, -23.82F, -5.96F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.3958F, KeyframeAnimations.degreeVec(0.59F, -27.94F, -8.88F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.4479F, KeyframeAnimations.degreeVec(10.0F, -30.0F, -10.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(10.0F, -30.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1042F, KeyframeAnimations.posVec(0.0F, -3.55F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, -6.7F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2604F, KeyframeAnimations.posVec(0.0F, -8.26F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3125F, KeyframeAnimations.posVec(-0.14F, -8.22F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.43F, -8.1F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5208F, KeyframeAnimations.posVec(-0.71F, -7.96F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.posVec(-1.0F, -7.8F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7292F, KeyframeAnimations.posVec(-1.0F, -7.27F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(-1.0F, -6.76F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9375F, KeyframeAnimations.posVec(-1.0F, -6.29F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.posVec(-1.0F, -5.88F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.1458F, KeyframeAnimations.posVec(-1.0F, -5.53F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.posVec(-1.0F, -5.24F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3542F, KeyframeAnimations.posVec(-1.0F, -4.99F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(-1.0F, -4.31F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.posVec(-1.0F, -3.75F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.9792F, KeyframeAnimations.posVec(-1.0F, -3.42F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0833F, KeyframeAnimations.posVec(-1.0F, -3.07F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1354F, KeyframeAnimations.posVec(-1.0F, -2.89F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1875F, KeyframeAnimations.posVec(-0.86F, -2.54F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2917F, KeyframeAnimations.posVec(-0.57F, -1.83F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.3958F, KeyframeAnimations.posVec(-0.29F, -1.11F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -0.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.4688F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.3438F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.4688F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(110.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.3438F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, -0.51F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, -0.43F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, -0.31F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, -0.22F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, -0.2F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.posVec(0.0F, -0.44F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(1.0F, -0.16F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(1.0F, -0.06F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.posVec(1.0F, -0.04F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(1.0F, -0.35F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailStart", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-45.0F, 17.32F, 4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(-45.0F, 20.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-45.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-45.0F, -17.32F, -4.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(-45.0F, -20.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-45.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-45.0F, -10.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 4.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailMid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, -25.98F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(45.0F, -30.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(45.0F, -25.98F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(45.0F, -15.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.degreeVec(45.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(45.0F, 25.98F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(45.0F, 30.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(45.0F, 25.98F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(45.0F, 15.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0833F, KeyframeAnimations.degreeVec(45.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2917F, KeyframeAnimations.degreeVec(45.0F, -15.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(45.0F, -25.98F, -8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailBack", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(20.0F, -10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(20.0F, -17.32F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(20.0F, -20.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(20.0F, -17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.degreeVec(20.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(20.0F, 10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.875F, KeyframeAnimations.degreeVec(20.0F, 20.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0833F, KeyframeAnimations.degreeVec(20.0F, 17.32F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2917F, KeyframeAnimations.degreeVec(20.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

	public static final AnimationDefinition CROSS_SLASH = AnimationDefinition.Builder.withLength(2.0F)
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-2.83F, 5.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, -1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 9.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.83F, 0.0F, 9.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-200.0F, 12.0F, -88.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-200.0F, 12.0F, -70.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-40.0F, -36.0F, -95.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-7.5F, -36.0F, -95.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 30.0F, 12.17F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.posVec(-1.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(-1.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(102.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(102.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-200.0F, -12.0F, 88.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-200.0F, -12.0F, 70.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-40.0F, 36.0F, 95.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-7.5F, 36.0F, 95.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-160.0F, -40.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Arm2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.4F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.posVec(1.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(1.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -8.38F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(102.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(97.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Claw4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(102.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.375F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, 2.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 2.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(5.0F, -10.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Leg2", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 1.5F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(1.0F, -0.3F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("root", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.375F, KeyframeAnimations.posVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(1.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailStart", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.375F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.7083F, KeyframeAnimations.degreeVec(-16.25F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-45.0F, 10.0F, 14.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailMid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5417F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2917F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(45.0F, -5.98F, 11.34F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailBack", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.625F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.125F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(57.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, -8.66F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("TailSpike", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.7083F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.375F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();
}