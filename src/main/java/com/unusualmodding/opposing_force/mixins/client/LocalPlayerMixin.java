package com.unusualmodding.opposing_force.mixins.client;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(at = @At("HEAD"), method = "hasEnoughImpulseToStartSprinting", cancellable = true)
    private void hasEnoughImpulseToStartSprinting(CallbackInfoReturnable<Boolean> callbackInfo) {
        if (((LocalPlayer) (Object) this).getItemBySlot(EquipmentSlot.HEAD).getItem() == OPItems.STONE_HELMET.get() && ((LocalPlayer) (Object) this).getItemBySlot(EquipmentSlot.CHEST).getItem() == OPItems.STONE_CHESTPLATE.get() && ((LocalPlayer) (Object) this).getItemBySlot(EquipmentSlot.LEGS).getItem() == OPItems.STONE_LEGGINGS.get() && ((LocalPlayer) (Object) this).getItemBySlot(EquipmentSlot.FEET).getItem() == OPItems.STONE_BOOTS.get()) {
            callbackInfo.setReturnValue(false);
        }
    }
}

