package com.unusualmodding.opposing_force.items.armor;

import com.unusualmodding.opposing_force.registry.OPParticles;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MoonShoesItem extends ConfigurableArmorItem {

    public MoonShoesItem(Properties properties) {
        super(Type.BOOTS, properties, OPArmorDefinitions.MOON_SHOES);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
        if (entity instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.FEET) == stack) {
            if (!living.onGround() && !living.onClimbable() && !living.isInWaterOrBubble()) {
                if (level.getRandom().nextFloat() < 0.5F) {
                    living.level().addParticle(OPParticles.MOON_SHOES.get(), living.position().x, living.position().y, living.position().z, (level.getRandom().nextFloat() - 0.5F) / 3.0F, 0.0D, (level.getRandom().nextFloat() - 0.5F) / 3.0F);
                }
            }
            living.resetFallDistance();
        }
    }
}
