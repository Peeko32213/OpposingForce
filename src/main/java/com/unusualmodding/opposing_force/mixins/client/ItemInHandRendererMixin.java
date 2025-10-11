package com.unusualmodding.opposing_force.mixins.client;

import com.unusualmodding.opposing_force.items.TeslaCannonItem;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(at = @At("HEAD"), method = "evaluateWhichHandsToRender", cancellable = true)
    private static void opposingForce$evaluateWhichHandsToRender(LocalPlayer localPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemstack = localPlayer.getMainHandItem();
        ItemStack itemstack1 = localPlayer.getOffhandItem();
        boolean hasCannon = itemstack.is(OPItems.TESLA_CANNON.get()) || itemstack1.is(OPItems.TESLA_CANNON.get());
        if (hasCannon) {
            cir.setReturnValue(isChargedTeslaCannon(itemstack) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
        }
    }

    @Inject(at = @At("HEAD"), method = "selectionUsingItemWhileHoldingBowLike", cancellable = true)
    private static void opposingForce$selectionUsingItemWhileHoldingBowLike(LocalPlayer localPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        InteractionHand interactionhand = localPlayer.getUsedItemHand();
        ItemStack itemstack = localPlayer.getUseItem();
        if (itemstack.is(OPItems.TESLA_CANNON.get())) {
            cir.setReturnValue(interactionhand == InteractionHand.MAIN_HAND && isChargedTeslaCannon(localPlayer.getOffhandItem()) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
        }
    }

    @Unique
    private static boolean isChargedTeslaCannon(ItemStack stack) {
        return stack.is(OPItems.TESLA_CANNON.get()) && TeslaCannonItem.isCharged(stack);
    }
}