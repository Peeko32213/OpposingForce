package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TerrorSaw extends ThrowableProjectile {

    public TerrorSaw(EntityType<? extends TerrorSaw> type, Level level) {
        super(type, level);
    }

    public TerrorSaw(Level level, LivingEntity shooter) {
        super(OPEntities.TERROR_SAW.get(), level, shooter);
    }

    public TerrorSaw(Level level, double x, double y, double z) {
        super(OPEntities.TERROR_SAW.get(), level, x, y, z);
    }

    @Override
    protected DamageSource getDamageSource(Entity shooter) {
        return OPDamageTypes.terrorSaw(this.level(), this, shooter);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return new ItemStack(OPItems.TERROR_SAW.get());
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(OPItems.TERROR_SAW.get());
    }

    @Override
    public double getBaseDamage() {
        return 6.0D;
    }

    @Override
    protected boolean tryPickup(Player player) {
        return switch (this.pickup) {
            case ALLOWED -> player.getInventory().add(this.getPickupItem());
            case CREATIVE_ONLY -> player.getAbilities().instabuild;
            default -> false;
        };
    }
}
