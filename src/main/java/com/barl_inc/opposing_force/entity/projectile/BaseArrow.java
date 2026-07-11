package com.barl_inc.opposing_force.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class BaseArrow extends AbstractArrow {

    public BaseArrow(EntityType<? extends BaseArrow> type, Level level) {
        super(type, level);
    }

    public BaseArrow(EntityType<? extends BaseArrow> type, Level level, Entity shooter) {
        super(type, level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}