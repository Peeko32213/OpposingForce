package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.entity.projectile.SlugEggs;
import com.unusualmodding.opposing_force.entity.projectile.Tomahawk;
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
        registerCompostables();
        registerFlammables();
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
    }

    public static void registerCompostables() {
        registerCompostable(OPBlocks.CAVE_PATTY.get(), 0.65F);
        registerCompostable(OPBlocks.COPPER_ENOKI.get(), 0.65F);
        registerCompostable(OPBlocks.RAINCAP.get(), 0.65F);
        registerCompostable(OPBlocks.CREAM_CAP.get(), 0.65F);
        registerCompostable(OPBlocks.CHICKEN_OF_THE_CAVES.get(), 0.65F);
        registerCompostable(OPBlocks.POWDER_GNOME.get(), 0.65F);
        registerCompostable(OPBlocks.CAP_OF_EYE.get(), 0.65F);
        registerCompostable(OPBlocks.PURPLE_KNOB.get(), 0.65F);
        registerCompostable(OPBlocks.SLIPPERY_TOP.get(), 0.65F);
        registerCompostable(OPBlocks.COPPER_ENOKI_BLOCK.get(), 0.85F);
        registerCompostable(OPBlocks.CREAM_CAP_BLOCK.get(), 0.85F);
        registerCompostable(OPBlocks.SLIPPERY_TOP_BLOCK.get(), 0.85F);
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
