package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    @Shadow
    public abstract void eat(int nutrition, float saturation);

//    @Inject(method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V", shift = At.Shift.AFTER, ordinal = 0))
//    public void eatWood(Item item, ItemStack stack, LivingEntity entity, CallbackInfo callbackInfo) {
//        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(OPItems.WOODEN_MASK.get()) && entity.getItemBySlot(EquipmentSlot.CHEST).is(OPItems.WOODEN_CHESTPLATE.get()) && entity.getItemBySlot(EquipmentSlot.LEGS).is(OPItems.WOODEN_COVER.get()) && entity.getItemBySlot(EquipmentSlot.FEET).is(OPItems.WOODEN_BOOTS.get())) {
//            callbackInfo.cancel();
//            if (stack.is(ItemTags.PLANKS)) {
//                FoodProperties foodproperties = stack.getFoodProperties(entity);
//                this.eat(Mth.ceil(foodproperties.getNutrition() * 0.4F), Mth.ceil((foodproperties.getSaturationModifier() * 0.4F)));
//            }
//        }
//    }
}
