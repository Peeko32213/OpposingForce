package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.items.BlasterItem;
import com.unusualmodding.opposing_force.items.TeslaCannonItem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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

        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof TeslaCannonItem) {
            if (!TeslaCannonItem.isCharged(player.getItemInHand(InteractionHand.OFF_HAND)) && player.getUseItemRemainingTicks() > 0) {
                event.getModel().leftArm.yRot = 0.8F;
                event.getModel().leftArm.xRot = -0.97079635F;
                event.getModel().rightArm.xRot = event.getModel().leftArm.xRot;
                float f = (float) TeslaCannonItem.getChargeDuration(player.getUseItem());
                float f1 = Mth.clamp((float) player.getTicksUsingItem(), 0.0F, f);
                float f2 = f1 / f;
                event.getModel().rightArm.yRot = Mth.lerp(f2, 0.4F, 0.85F) * (float) (-1);
                event.getModel().rightArm.xRot = Mth.lerp(f2, event.getModel().rightArm.xRot, (-(float) Math.PI / 2F));
                event.setResult(Event.Result.ALLOW);
            } else if (player.getUseItemRemainingTicks() == 0 && TeslaCannonItem.isCharged(player.getItemInHand(InteractionHand.OFF_HAND))) {
                event.getModel().leftArm.yRot = 0.3F + event.getModel().head.yRot;
                event.getModel().rightArm.yRot = -0.6F + event.getModel().head.yRot;
                event.getModel().leftArm.xRot = (-(float) Math.PI / 2F) + event.getModel().head.xRot + 0.1F;
                event.getModel().rightArm.xRot = -1.5F + event.getModel().head.xRot;
                event.setResult(Event.Result.ALLOW);
            }
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof TeslaCannonItem) {
            if (!TeslaCannonItem.isCharged(player.getItemInHand(InteractionHand.MAIN_HAND)) && player.getUseItemRemainingTicks() > 0) {
                event.getModel().rightArm.yRot = -0.8F;
                event.getModel().rightArm.xRot = -0.97079635F;
                event.getModel().leftArm.xRot = event.getModel().rightArm.xRot;
                float f = (float) TeslaCannonItem.getChargeDuration(player.getUseItem());
                float f1 = Mth.clamp((float) player.getTicksUsingItem(), 0.0F, f);
                float f2 = f1 / f;
                event.getModel().leftArm.yRot = Mth.lerp(f2, 0.4F, 0.85F);
                event.getModel().leftArm.xRot = Mth.lerp(f2, event.getModel().leftArm.xRot, (-(float) Math.PI / 2F));
                event.setResult(Event.Result.ALLOW);
            } else if (player.getUseItemRemainingTicks() == 0 && TeslaCannonItem.isCharged(player.getItemInHand(InteractionHand.MAIN_HAND))) {
                event.getModel().rightArm.yRot = -0.3F + event.getModel().head.yRot;
                event.getModel().leftArm.yRot = 0.6F + event.getModel().head.yRot;
                event.getModel().rightArm.xRot = (-(float) Math.PI / 2F) + event.getModel().head.xRot + 0.1F;
                event.getModel().leftArm.xRot = -1.5F + event.getModel().head.xRot;
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END) {
            Entity cameraEntity = minecraft.getCameraEntity();
            prevShakeAmount = shakeAmount;
            float shake = 0.0F;
            Iterator<ScreenShakeEvent> groundShakeMomentIterator = SCREEN_SHAKE_EVENTS.iterator();
            while (groundShakeMomentIterator.hasNext()) {
                ScreenShakeEvent groundShakeMoment = groundShakeMomentIterator.next();
                groundShakeMoment.tick();
                if (groundShakeMoment.isDone()) groundShakeMomentIterator.remove();
                else shake = Math.max(shake, groundShakeMoment.getDegree(cameraEntity, 1.0F));
            }
            shakeAmount = shake * minecraft.options.screenEffectScale().get().floatValue();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft minecraft = Minecraft.getInstance();
        float partialTicks = (float) event.getPartialTick();

        float lerpedShakeAmount = Mth.clamp(prevShakeAmount + (shakeAmount - prevShakeAmount) * partialTicks, 0, 4.0F);
        if (lerpedShakeAmount > 0) {
            float time = minecraft.cameraEntity == null ? 0.0F : minecraft.cameraEntity.tickCount + minecraft.getPartialTick();
            event.setRoll((float) (lerpedShakeAmount * Math.sin(2.0F * time)));
        }
    }
}
