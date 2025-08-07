package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.items.CloudBootsItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Inject(at = @At("HEAD"), method = "getJumpBoostPower", cancellable = true)
    private void jumpBoostPower(CallbackInfoReturnable<Float> callbackInfo) {
        if (this.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof CloudBootsItem) {
            callbackInfo.setReturnValue(((LivingEntity) (Object) this).hasEffect(MobEffects.JUMP) ? 0.5F + (0.1F * ((float) ((LivingEntity) (Object) this).getEffect(MobEffects.JUMP).getAmplifier() + 1.0F)) : 0.5F);
        }
    }
}
