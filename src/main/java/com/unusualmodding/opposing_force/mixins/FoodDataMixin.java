package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.items.WoodenArmorItem;
import com.unusualmodding.opposing_force.registry.tags.OPItemTags;
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

    @Inject(method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V", cancellable = true, remap = false, at = @At(value = "HEAD"))
    public void eatVegetables(Item item, ItemStack stack, LivingEntity entity, CallbackInfo ci) {
        if (entity != null && stack.is(OPItemTags.RAW_VEGETABLES)) {
            int extraSaturation = WoodenArmorItem.getExtraSaturationFromArmor(entity);
            if (extraSaturation != 0) {
                ci.cancel();
                if (item.isEdible()) {
                    FoodProperties properties = stack.getFoodProperties(entity);
                    this.eat(properties.getNutrition() + extraSaturation, properties.getSaturationModifier() + (extraSaturation * 0.05F));
                }
            }
        }
    }
}
