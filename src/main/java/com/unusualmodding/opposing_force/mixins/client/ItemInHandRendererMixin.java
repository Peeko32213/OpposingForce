package com.unusualmodding.opposing_force.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.items.TeslaCannonItem;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow
    protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm, float f);

    @Shadow
    protected abstract void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm arm, float f);

    @Shadow
    public abstract void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext context, boolean flag, PoseStack poseStack, MultiBufferSource bufferSource, int i);

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At("HEAD"), method = "evaluateWhichHandsToRender", cancellable = true)
    private static void opposingForce$evaluateWhichHandsToRender(LocalPlayer localPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemstack = localPlayer.getMainHandItem();
        ItemStack itemstack1 = localPlayer.getOffhandItem();
        boolean hasCannon = itemstack.is(OPItems.TESLA_CANNON.get()) || itemstack1.is(OPItems.TESLA_CANNON.get());
        if (hasCannon) {
            cir.setReturnValue(opposingForce$isChargedTeslaCannon(itemstack) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
        }
    }

    @Inject(at = @At("HEAD"), method = "selectionUsingItemWhileHoldingBowLike", cancellable = true)
    private static void opposingForce$selectionUsingItemWhileHoldingBowLike(LocalPlayer localPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        InteractionHand interactionhand = localPlayer.getUsedItemHand();
        ItemStack itemstack = localPlayer.getUseItem();
        if (itemstack.is(OPItems.TESLA_CANNON.get())) {
            cir.setReturnValue(interactionhand == InteractionHand.MAIN_HAND && opposingForce$isChargedTeslaCannon(localPlayer.getOffhandItem()) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
        }
    }

    @Inject(at = @At("HEAD"), method = "renderArmWithItem", cancellable = true)
    private void opposingForce$renderArmWithItem(AbstractClientPlayer player, float v, float v1, InteractionHand hand, float v2, ItemStack stack, float v3, PoseStack poseStack, MultiBufferSource bufferSource, int i1, CallbackInfo ci) {
        boolean flag = hand == InteractionHand.MAIN_HAND;
        HumanoidArm humanoidarm = flag ? player.getMainArm() : player.getMainArm().getOpposite();
        if (stack.getItem() instanceof TeslaCannonItem) {
            ci.cancel();
            poseStack.pushPose();
            boolean flag1 = TeslaCannonItem.isCharged(stack);
            boolean flag2 = humanoidarm == HumanoidArm.RIGHT;
            int i = flag2 ? 1 : -1;
            if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand) {
                this.applyItemArmTransform(poseStack, humanoidarm, v3);
                poseStack.translate((float) i * -0.4785682F, -0.094387F, 0.05731531F);
                poseStack.mulPose(Axis.XP.rotationDegrees(-11.935F));
                poseStack.mulPose(Axis.YP.rotationDegrees((float)i * 65.3F));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float)i * -9.785F));
                float f9 = (float) stack.getUseDuration() - ((float) this.minecraft.player.getUseItemRemainingTicks() - v + 1.0F);
                float f13 = f9 / (float) TeslaCannonItem.getChargeDuration(stack);
                if (f13 > 1.0F) {
                    f13 = 1.0F;
                }

                if (f13 > 0.1F) {
                    float f16 = Mth.sin((f9 - 0.1F) * 1.3F);
                    float f3 = f13 - 0.1F;
                    float f4 = f16 * f3;
                    poseStack.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                }
                poseStack.translate(f13 * 0.0F, f13 * 0.0F, f13 * 0.04F);
                poseStack.scale(1.0F, 1.0F, 1.0F + f13 * 0.2F);
                poseStack.mulPose(Axis.YN.rotationDegrees((float) i * 45.0F));
            } else {
                float f = -0.4F * Mth.sin(Mth.sqrt(v2) * (float) Math.PI);
                float f1 = 0.2F * Mth.sin(Mth.sqrt(v2) * ((float) Math.PI * 2F));
                float f2 = -0.2F * Mth.sin(v2 * (float) Math.PI);
                poseStack.translate((float) i * f, f1, f2);
                this.applyItemArmTransform(poseStack, humanoidarm, v3);
                this.applyItemArmAttackTransform(poseStack, humanoidarm, v2);
                if (flag1 && v2 < 0.001F && flag) {
                    poseStack.translate((float) i * -0.641864F, 0.0F, 0.0F);
                    poseStack.mulPose(Axis.YP.rotationDegrees((float) i * 10.0F));
                }
            }
            this.renderItem(player, stack, flag2 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !flag2, poseStack, bufferSource, i1);
            poseStack.popPose();
        }
    }

    @Unique
    private static boolean opposingForce$isChargedTeslaCannon(ItemStack stack) {
        return stack.is(OPItems.TESLA_CANNON.get()) && TeslaCannonItem.isCharged(stack);
    }
}