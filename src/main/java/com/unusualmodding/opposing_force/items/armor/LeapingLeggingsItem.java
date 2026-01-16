package com.unusualmodding.opposing_force.items.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LeapingLeggingsItem extends ConfigurableArmorItem {

    public LeapingLeggingsItem(Properties properties) {
        super(Type.LEGGINGS, properties, OPArmorDefinitions.LEAPING_LEGGINGS);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
        if (entity instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.LEGS) == stack) {
            living.resetFallDistance();
        }
    }
}
