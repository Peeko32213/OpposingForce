package com.barl_inc.opposing_force.mixins;

import com.barl_inc.opposing_force.registry.OPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract void playSound(SoundEvent soundEvent, float pVolume, float pPitch);

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void opposingForce$playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        float volumeModifier;

        if ((Entity) (Object) this instanceof LivingEntity mob) {
            if (mob.getItemBySlot(EquipmentSlot.FEET).getItem() == OPItems.DEEPWOVEN_BOOTS.get()) {
                ci.cancel();
                volumeModifier = 0.01F;
                SoundType soundtype = state.getSoundType(mob.level, pos, mob);
                this.playSound(soundtype.getStepSound(), soundtype.getVolume() * volumeModifier, soundtype.getPitch());
            }
        }
    }

    @Inject(method = "dampensVibrations", at = @At("RETURN"), cancellable = true)
    private void opposingforce$dampensVibrations(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;

        if (!(entity instanceof LivingEntity living)) return;

        if (opposingForce$isWearingFullDeepwovenSet(living)) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private static boolean opposingForce$isWearingFullDeepwovenSet(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(OPItems.DEEPWOVEN_HAT.get())
                && entity.getItemBySlot(EquipmentSlot.CHEST).is(OPItems.DEEPWOVEN_TUNIC.get())
                && entity.getItemBySlot(EquipmentSlot.LEGS).is(OPItems.DEEPWOVEN_PANTS.get())
                && entity.getItemBySlot(EquipmentSlot.FEET).is(OPItems.DEEPWOVEN_BOOTS.get());
    }

}
