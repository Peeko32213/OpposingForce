package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.items.CloudBootsItem;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Shadow public abstract Abilities getAbilities();

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getFlyingSpeed", at = @At(value = "RETURN"), cancellable = true)
    protected void getFlyingSpeed(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (!this.isPassenger() && !this.getAbilities().flying) {
            float airSpeed = 0.0F;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack stack = this.getItemBySlot(slot);
                    Collection<AttributeModifier> airSpeedAmount = stack.getAttributeModifiers(slot).get(OPAttributes.AIR_SPEED.get());
                    if (!airSpeedAmount.isEmpty()) {
                        airSpeed += (float) airSpeedAmount.stream().mapToDouble(AttributeModifier::getAmount).sum();
                    }
                }
            }

            float airSpeedModifier = airSpeed / 10;
            if (airSpeed > 0.0F) {
                if (this.isSprinting()) {
                    callbackInfoReturnable.setReturnValue(Math.max(0.025999999F + airSpeedModifier, 0.005F));
                } else {
                    callbackInfoReturnable.setReturnValue(Math.max(0.02F + airSpeedModifier, 0.005F));
                }
            }
        }
    }
}
