package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.entity.projectile.UmberDagger;
import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.entity.projectile.SlugEggs;
import com.unusualmodding.opposing_force.entity.projectile.Tomahawk;
import com.unusualmodding.opposing_force.items.MobHeadItem;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.Vec3;

public class OPCompat {

    public static void registerCompat() {
        registerDispenserBehaviors();
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(OPItems.ELECTRIC_CHARGE.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level level, Position pos, ItemStack itemStack) {
                ElectricCharge entity = new ElectricCharge(level, pos.x(), pos.y(), pos.z(), Vec3.ZERO);
                return entity;
            }
        });

        DispenserBlock.registerBehavior(OPItems.SLUG_EGGS.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position pos, ItemStack itemStack) {
                return new SlugEggs(level, pos.x(), pos.y(), pos.z());
            }
        });

        DispenserBlock.registerBehavior(OPItems.TOMAHAWK.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level level, Position pos, ItemStack itemStack) {
                Tomahawk entity = new Tomahawk(level, pos.x(), pos.y(), pos.z());
                entity.pickup = AbstractArrow.Pickup.ALLOWED;
                return entity;
            }
        });

        DispenserBlock.registerBehavior(OPItems.UMBER_DAGGER.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level level, Position pos, ItemStack itemStack) {
                UmberDagger entity = new UmberDagger(level, pos.x(), pos.y(), pos.z());
                entity.pickup = AbstractArrow.Pickup.ALLOWED;
                return entity;
            }
        });

        DispenserBlock.registerBehavior(OPItems.DICER_HEAD.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.FROWZY_HEAD.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.RAMBLE_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static void registerCompostables() {
    }

    public static void registerFlammables() {
    }

    public static void registerCompostable(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static void registerFlammable(Block block, int igniteChance, int burnChance) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, igniteChance, burnChance);
    }
}
