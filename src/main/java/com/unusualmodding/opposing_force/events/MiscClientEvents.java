package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.items.BlasterItem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MiscClientEvents {

    private static float shakeAmount;
    private static float prevShakeAmount;

    public static final List<ScreenShakeEvent> SCREEN_SHAKE_EVENTS = new ArrayList<>();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onPoseHand(PoseHandEvent event) {
        LivingEntity player = (LivingEntity) event.getEntityIn();

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BlasterItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                event.getModel().rightArm.xRot = (event.getModel().head.xRot - (float) Math.toRadians(80F));
                event.getModel().rightArm.yRot = event.getModel().head.yRot;
                event.getModel().rightArm.zRot = 0;
            } else {
                event.getModel().leftArm.xRot = (event.getModel().head.xRot - (float) Math.toRadians(80F));
                event.getModel().leftArm.yRot = event.getModel().head.yRot;
                event.getModel().leftArm.zRot = 0;
            }
            event.setResult(Event.Result.ALLOW);
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof BlasterItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                event.getModel().leftArm.xRot = (event.getModel().head.xRot - (float) Math.toRadians(80F));
                event.getModel().leftArm.yRot = event.getModel().head.yRot;
                event.getModel().leftArm.zRot = 0;
            } else {
                event.getModel().rightArm.xRot = (event.getModel().head.xRot - (float) Math.toRadians(80F));
                event.getModel().rightArm.yRot = event.getModel().head.yRot;
                event.getModel().rightArm.zRot = 0;
            }
            event.setResult(Event.Result.ALLOW);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (event.phase == TickEvent.Phase.END) {
            Entity cameraEntity = mc.getCameraEntity();
            prevShakeAmount = shakeAmount;
            float shake = 0.0F;
            Iterator<ScreenShakeEvent> groundShakeMomentIterator = SCREEN_SHAKE_EVENTS.iterator();
            while (groundShakeMomentIterator.hasNext()) {
                ScreenShakeEvent groundShakeMoment = groundShakeMomentIterator.next();
                groundShakeMoment.tick();
                if (groundShakeMoment.isDone()) groundShakeMomentIterator.remove();
                else shake = Math.max(shake, groundShakeMoment.getDegree(cameraEntity, 1.0F));
            }
            shakeAmount = shake * mc.options.screenEffectScale().get().floatValue();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft mc = Minecraft.getInstance();
        float partialTicks = (float) event.getPartialTick();

        float lerpedShakeAmount = Mth.clamp(prevShakeAmount + (shakeAmount - prevShakeAmount) * partialTicks, 0, 4.0F);
        if (lerpedShakeAmount > 0) {
            float time = mc.cameraEntity == null ? 0.0F : mc.cameraEntity.tickCount + mc.getPartialTick();
            event.setRoll((float) (lerpedShakeAmount * Math.sin(2.0F * time)));
        }
    }
}
