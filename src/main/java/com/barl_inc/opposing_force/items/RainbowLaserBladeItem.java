package com.barl_inc.opposing_force.items;

import com.barl_inc.opposing_force.registry.OPParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RainbowLaserBladeItem extends LaserBladeItem {

    public RainbowLaserBladeItem(ParticleOptions sweepParticle) {
        super(sweepParticle);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slot, isSelected);
        if (entity.tickCount % 10 == 0) this.sweepParticle = this.getRandomSweepColor(level.getRandom().nextInt(15));
    }

    private ParticleOptions getRandomSweepColor(int i) {
        return switch (i) {
            case 1 -> OPParticles.LIGHT_GRAY_LASER_SWEEP.get();
            case 2 -> OPParticles.GRAY_LASER_SWEEP.get();
            case 3 -> OPParticles.BLACK_LASER_SWEEP.get();
            case 4 -> OPParticles.BROWN_LASER_SWEEP.get();
            case 5 -> OPParticles.RED_LASER_SWEEP.get();
            case 6 -> OPParticles.ORANGE_LASER_SWEEP.get();
            case 7 -> OPParticles.YELLOW_LASER_SWEEP.get();
            case 8 -> OPParticles.LIME_LASER_SWEEP.get();
            case 9 -> OPParticles.GREEN_LASER_SWEEP.get();
            case 10 -> OPParticles.CYAN_LASER_SWEEP.get();
            case 11 -> OPParticles.LIGHT_BLUE_LASER_SWEEP.get();
            case 12 -> OPParticles.BLUE_LASER_SWEEP.get();
            case 13 -> OPParticles.PURPLE_LASER_SWEEP.get();
            case 14 -> OPParticles.MAGENTA_LASER_SWEEP.get();
            case 15 -> OPParticles.PINK_LASER_SWEEP.get();
            default -> OPParticles.WHITE_LASER_SWEEP.get();
        };
    }
}
