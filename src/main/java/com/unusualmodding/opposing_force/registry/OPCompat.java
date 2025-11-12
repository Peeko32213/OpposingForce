package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.entity.projectile.*;
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
import org.jetbrains.annotations.NotNull;

public class OPCompat {

    public static void registerCompat() {
        registerDispenserBehaviors();
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(OPItems.ELECTRIC_CHARGE.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack itemStack) {
                return new ElectricCharge(level, pos.x(), pos.y(), pos.z(), Vec3.ZERO);
            }
        });

        DispenserBlock.registerBehavior(OPItems.TOMAHAWK.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack itemStack) {
                Tomahawk entity = new Tomahawk(level, pos.x(), pos.y(), pos.z());
                entity.pickup = AbstractArrow.Pickup.ALLOWED;
                return entity;
            }
        });

        DispenserBlock.registerBehavior(OPItems.UMBER_DAGGER.get(), new AbstractProjectileDispenseBehavior() {
            @Override
            protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack itemStack) {
                UmberDagger entity = new UmberDagger(level, pos.x(), pos.y(), pos.z());
                entity.pickup = AbstractArrow.Pickup.ALLOWED;
                return entity;
            }
        });

        DispenserBlock.registerBehavior(OPItems.FIRE_BOMB.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                return new FireBomb(level, position.x(), position.y(), position.z());
            }
        });

        DispenserBlock.registerBehavior(OPItems.KINETIC_BOMB.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                return new KineticBomb(level, position.x(), position.y(), position.z());
            }
        });

        DispenserBlock.registerBehavior(OPItems.LIGHTNING_BOMB.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                return new LightningBomb(level, position.x(), position.y(), position.z());
            }
        });

        DispenserBlock.registerBehavior(OPItems.WHIZZ_BOMB.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position position, ItemStack itemStack) {
                return new WhizzBomb(level, position.x(), position.y(), position.z());
            }
        });

        DispenserBlock.registerBehavior(OPItems.DICER_HEAD.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.FROWZY_HEAD.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.ANGRY_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.CLASSIC_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.CRUNDLY_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.DWARVEN_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.EVIL_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.GRINNING_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.IMPRISONED_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.INDOMITABLE_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.LEERING_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.MAGMATIC_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.MUSICAL_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.NOSY_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.SKELETAL_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.SMILING_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.STRANGE_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.VALIANT_RAMBLER_SKULL.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);

        DispenserBlock.registerBehavior(OPItems.TART_HEAD.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
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
