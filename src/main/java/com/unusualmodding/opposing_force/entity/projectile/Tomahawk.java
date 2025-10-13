package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Tomahawk extends ThrowableProjectile {

    public Tomahawk(EntityType<? extends Tomahawk> type, Level level) {
        super(type, level);
    }

    public Tomahawk(Level level, LivingEntity shooter) {
        super(OPEntities.TOMAHAWK.get(), level, shooter);
    }

    public Tomahawk(Level level, double x, double y, double z) {
        super(OPEntities.TOMAHAWK.get(), level, x, y, z);
    }

    @Override
    protected DamageSource getDamageSource(Entity shooter) {
        return OPDamageTypes.tomahawk(this.level(), this, shooter);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(OPItems.TOMAHAWK.get());
    }

    @Override
    public double getBaseDamage() {
        return 5.0D;
    }
}
