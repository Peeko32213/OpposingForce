package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At("HEAD"), method = "getJumpBoostPower", cancellable = true)
    private void jumpBoostPower(CallbackInfoReturnable<Float> callbackInfo) {
        if (((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.HEAD).getItem() == OPItems.STONE_HELMET.get() && ((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.CHEST).getItem() == OPItems.STONE_CHESTPLATE.get() && ((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.LEGS).getItem() == OPItems.STONE_LEGGINGS.get() && ((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.FEET).getItem() == OPItems.STONE_BOOTS.get()) {
            callbackInfo.setReturnValue(((LivingEntity) (Object) this).hasEffect(MobEffects.JUMP) ? (0.1F - 0.05F) * ((float) ((LivingEntity) (Object) this).getEffect(MobEffects.JUMP).getAmplifier() + 1.0F) : -0.05F);
        }
    }
}
