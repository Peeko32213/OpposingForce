package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.items.BladeOfTheMountainItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
    public abstract ItemStack getMainHandItem();

    @Shadow
    public abstract MobEffectInstance getEffect(MobEffect effect);

    @Shadow
    public abstract boolean hasEffect(MobEffect pEffect);

    @Inject(method = "getCurrentSwingDuration", at = @At("TAIL"), cancellable = true)
    private void opposingForce$getSwingDuration(CallbackInfoReturnable<Integer> cir) {
        if (this.getMainHandItem().getItem() instanceof BladeOfTheMountainItem) {
            cir.setReturnValue(this.hasEffect(MobEffects.DIG_SLOWDOWN) ? 12 + (1 + this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 2 : 12);
        }
    }
}
