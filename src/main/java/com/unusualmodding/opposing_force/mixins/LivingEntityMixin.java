package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.items.CloudBootsItem;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Shadow
    public abstract float getJumpBoostPower();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "getJumpPower", cancellable = true)
    private void jumpPower(CallbackInfoReturnable<Float> callbackInfo) {
        float bulk = 0.0F;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack stack = this.getItemBySlot(slot);
                Collection<AttributeModifier> bulk1 = stack.getAttributeModifiers(slot).get(OPAttributes.BULK.get());
                if (!bulk1.isEmpty()) {
                    bulk += (float) bulk1.stream().mapToDouble(AttributeModifier::getAmount).sum();
                }
            }
        }

        if (bulk > 0.0F) {
            callbackInfo.setReturnValue((0.42F * this.getBlockJumpFactor() + this.getJumpBoostPower()) - (bulk / 8));
        }
        if (this.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof CloudBootsItem) {
            callbackInfo.setReturnValue(((0.42F * this.getBlockJumpFactor() + this.getJumpBoostPower()) + 0.5F) - (bulk / 8));
        }
    }
}
