package com.unusualmodding.opposing_force.mixins.client;

import com.unusualmodding.opposing_force.events.PoseHandEvent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin extends Model {

    public HumanoidModelMixin(Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
    }

    @Inject(at = @At("HEAD"), method = "poseRightArm", cancellable = true)
    private void opposingForce$poseRightArm(LivingEntity entity, CallbackInfo callbackInfo) {
        PoseHandEvent event = new PoseHandEvent(entity, (HumanoidModel) ((Model) this), false);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.ALLOW) {
            callbackInfo.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "poseLeftArm", cancellable = true)
    private void opposingForce$poseLeftArm(LivingEntity entity, CallbackInfo callbackInfo) {
        PoseHandEvent event = new PoseHandEvent(entity, (HumanoidModel) ((Model) this), true);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.ALLOW) {
            callbackInfo.cancel();
        }
    }
}
