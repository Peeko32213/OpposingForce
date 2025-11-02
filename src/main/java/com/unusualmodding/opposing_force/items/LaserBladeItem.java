package com.unusualmodding.opposing_force.items;

import com.unusualmodding.alkahest.registry.AlkahestMobEffects;
import com.unusualmodding.alkahest.registry.AlkahestSoundEvents;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.enums.OPTiers.OPItemTiers;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LaserBladeItem extends SwordItem {

    public LaserBladeItem(Properties properties) {
        super(OPItemTiers.LASER, 3, -2.4F, properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (super.hurtEnemy(stack, target, attacker)) {
            target.playSound(OPSoundEvents.LASER_BLADE_HIT.get(), 1.0F, 1.0F / (target.level().getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        }
        return false;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
        boolean activated = stack.getOrCreateTag().getBoolean("Activated");

        if (isSelected && !activated) {
            stack.getOrCreateTag().putBoolean("Activated", true);
            if (level.isClientSide) return;
            level.playSound(null, entity.blockPosition(), OPSoundEvents.LASER_BLADE_ACTIVATE.get(), SoundSource.PLAYERS, 0.25F, 1.0F);
        }
        else if (!isSelected && activated) {
            stack.getOrCreateTag().putBoolean("Activated", false);
        }
    }
}
