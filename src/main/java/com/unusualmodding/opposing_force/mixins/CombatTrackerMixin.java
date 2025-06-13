package com.unusualmodding.opposing_force.mixins;

import com.google.common.collect.Lists;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CombatTracker.class)
public abstract class CombatTrackerMixin {
    @Shadow
    private LivingEntity mob;
    private final List<CombatEntry> entries = Lists.newArrayList();

    public CombatTrackerMixin(LivingEntity entity) {
        this.mob = entity;
    }

    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void modifyDeathMessage(CallbackInfoReturnable<Component> callbackInfo) {
        Logger LOGGER = LogManager.getLogger(OpposingForce.MOD_ID);
        CombatTracker combatTracker = (CombatTracker) (Object) this;
        if (mob != null) {
            if (mob.getItemBySlot(EquipmentSlot.HEAD).getItem() == OPItems.DEEPWOVEN_HELMET.get()) {
                if (!mob.level.isClientSide) {
                    LOGGER.info("The mysterious figure was " + mob.getDisplayName().getString());
                }
                Component deathMessage = Component.translatable("death.anonymous");
                callbackInfo.setReturnValue(deathMessage);
            }
            if (entries.isEmpty()) {
                if (mob.getItemBySlot(EquipmentSlot.HEAD).getItem() == OPItems.DEEPWOVEN_HELMET.get()) {
                    if (!mob.level.isClientSide) {
                        LOGGER.info("The mysterious figure was " + mob.getDisplayName().getString());
                    }
                    Component modifiedMessage = Component.translatable("death.assassinated", this.mob.getDisplayName());
                    callbackInfo.setReturnValue(modifiedMessage);
                }
            }
        }
    }
}
