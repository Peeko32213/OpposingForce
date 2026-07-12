package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.entity.projectile.*;
import com.unusualmodding.opposing_force.entity.projectile.*;
import com.barl_inc.opposing_force.items.MobHeadItem;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class OPCompat {

    public static void registerCompat() {
        registerDispenserBehaviors();
        registerCompostables();
        registerFlammables();
    }

    public static void registerDispenserBehaviors() {


        //TODO check if the ones that can be picked up are still able to be picked up!
        DispenserBlock.registerProjectileBehavior(OPItems.ELECTRIC_CHARGE.get());
        DispenserBlock.registerProjectileBehavior(OPItems.TOMAHAWK.get());
        DispenserBlock.registerProjectileBehavior(OPItems.UMBER_DAGGER.get());
        DispenserBlock.registerProjectileBehavior(OPItems.FIRE_BOMB.get());
        DispenserBlock.registerProjectileBehavior(OPItems.KINETIC_BOMB.get());
        DispenserBlock.registerProjectileBehavior(OPItems.DONUT.get());
        DispenserBlock.registerProjectileBehavior(OPItems.TERROR_SAW.get());
        DispenserBlock.registerProjectileBehavior(OPItems.LIGHTNING_BOMB.get());
        DispenserBlock.registerProjectileBehavior(OPItems.WHIZZ_BOMB.get());


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
        DispenserBlock.registerBehavior(OPItems.SKYVERN_HEAD.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.TART_HEAD.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
        DispenserBlock.registerBehavior(OPItems.WHIZZ_HEAD.get(), MobHeadItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static void registerCompostables() {
        registerCompostable(OPBlocks.APPLE_SAPLING.get(), 0.3F);
        registerCompostable(OPBlocks.APPLE_LEAVES.get(), 0.3F);
        registerCompostable(OPBlocks.FLOWERING_APPLE_LEAVES.get(), 0.3F);
        registerCompostable(OPBlocks.FRUITFUL_APPLE_LEAVES.get(), 0.3F);
        registerCompostable(OPBlocks.INFESTED_APPLE_LEAVES.get(), 0.3F);
        registerCompostable(OPBlocks.FLOWERING_INFESTED_APPLE_LEAVES.get(), 0.3F);
        registerCompostable(OPBlocks.FRUITFUL_INFESTED_APPLE_LEAVES.get(), 0.3F);
    }

    public static void registerFlammables() {
        registerFlammable(OPBlocks.APPLE_LEAVES.get(), 30, 60);
        registerFlammable(OPBlocks.FLOWERING_APPLE_LEAVES.get(), 30, 60);
        registerFlammable(OPBlocks.FRUITFUL_APPLE_LEAVES.get(), 30, 60);
        registerFlammable(OPBlocks.INFESTED_APPLE_LEAVES.get(), 30, 60);
        registerFlammable(OPBlocks.FLOWERING_INFESTED_APPLE_LEAVES.get(), 30, 60);
        registerFlammable(OPBlocks.FRUITFUL_INFESTED_APPLE_LEAVES.get(), 30, 60);
    }

    public static void registerCompostable(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static void registerFlammable(Block block, int igniteChance, int burnChance) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, igniteChance, burnChance);
    }
}
