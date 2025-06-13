package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract void playSound(SoundEvent soundEvent, float pVolume, float pPitch);

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void playStepSound(BlockPos pos, BlockState state, CallbackInfo callback) {

        float volumeModifier = 0.15F;

        if ((Entity) (Object) this instanceof LivingEntity mob) {
            if (mob.getItemBySlot(EquipmentSlot.FEET).getItem() == OPItems.DEEPWOVEN_BOOTS.get()) {
                callback.cancel();
                volumeModifier = 0.015F;
                SoundType soundtype = state.getSoundType(mob.level, pos, mob);
                this.playSound(soundtype.getStepSound(), soundtype.getVolume() * volumeModifier, soundtype.getPitch());
            }
        }
    }
}
