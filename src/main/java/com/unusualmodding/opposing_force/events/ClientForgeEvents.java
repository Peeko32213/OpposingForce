package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.items.BlasterItem;
import com.unusualmodding.opposing_force.items.LaserBladeItem;
import com.unusualmodding.opposing_force.items.SawbladeItem;
import com.unusualmodding.opposing_force.items.TeslaCannonItem;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.utils.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    private static float shakeAmount;
    private static float prevShakeAmount;

    public static final List<ScreenShakeEvent> SCREEN_SHAKE_EVENTS = new ArrayList<>();
    public static final List<ScreenShakeEvent> PENDING_SCREEN_SHAKE_EVENTS = new ArrayList<>();


    @SubscribeEvent
    public void preRenderLiving(RenderLivingEvent.Pre event) {
        if (ClientProxy.blockedEntityRenders.contains(event.getEntity().getUUID())) {
            if (!OpposingForce.PROXY.isFirstPersonPlayer(event.getEntity())) {
                MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(event.getEntity(), event.getRenderer(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
                event.setCanceled(true);
            }
            ClientProxy.blockedEntityRenders.remove(event.getEntity().getUUID());
        }
    }

    @SubscribeEvent
    public static void renderNameplate(RenderNameTagEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == OPItems.DEEPWOVEN_HAT.get()) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onPoseHand(PoseHandEvent event) {
        LivingEntity player = (LivingEntity) event.getEntity();

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
        if (player.getUsedItemHand() == InteractionHand.OFF_HAND && player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof LaserBladeItem) {
            if (player.isUsingItem()) {
                event.getModel().leftArm.xRot = event.getModel().leftArm.xRot * 0.5F - 0.98F;
                event.getModel().leftArm.yRot = -1 * -0.6F;
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DEFAULT);
            }
        }
        if (player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof LaserBladeItem) {
            if (player.isUsingItem()) {
                event.getModel().rightArm.xRot = event.getModel().rightArm.xRot * 0.5F - 0.98F;
                event.getModel().rightArm.yRot = 1 * -0.6F;
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DEFAULT);
            }
        }
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.phase != TickEvent.Phase.END)
            return;

        Entity cameraEntity = minecraft.getCameraEntity();
        prevShakeAmount = shakeAmount;

        if (!PENDING_SCREEN_SHAKE_EVENTS.isEmpty()) {
            SCREEN_SHAKE_EVENTS.addAll(PENDING_SCREEN_SHAKE_EVENTS);
            PENDING_SCREEN_SHAKE_EVENTS.clear();
        }

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

    @SubscribeEvent
    public static void onFovModify(ComputeFovModifierEvent event) {
        float sawbladeFov = SawbladeItem.sawbladeComputeFov(event.getPlayer(), event.getNewFovModifier());
        if (sawbladeFov != event.getNewFovModifier()) {
            event.setNewFovModifier(sawbladeFov);
        }
    }
}
