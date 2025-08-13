package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.registry.tags.OPItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    @Shadow
    public abstract void eat(int nutrition, float saturation);

    @Inject(method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V", cancellable = true, remap = false, at = @At(value = "HEAD"))
    public void eatVegetables(Item item, ItemStack itemStack, LivingEntity entity, CallbackInfo ci) {
        if (entity != null && itemStack.is(OPItemTags.VEGAN_FOOD)) {
            int extraFoodFromProduce = 0;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = entity.getItemBySlot(slot);
                Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.VEGAN_NOURISHMENT.get());
                if (!modifiers.isEmpty()) {
                    extraFoodFromProduce += (int) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                }
            }

            if (extraFoodFromProduce != 0) {
                ci.cancel();
                if (item.isEdible()) {
                    FoodProperties foodproperties = itemStack.getFoodProperties(entity);
                    this.eat(foodproperties.getNutrition() + extraFoodFromProduce, foodproperties.getSaturationModifier() + (extraFoodFromProduce * 0.125F));
                }
            }
        }
    }
}
