package com.unusualmodding.opposing_force;

import net.minecraftforge.common.ForgeConfigSpec;

public class OpposingForceConfig {

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static final String CATEGORY_ITEMS = "items";

    public static final String CATEGORY_BLASTER = "blaster";
    public static ForgeConfigSpec.DoubleValue BLASTER_BOLT_DAMAGE;
    public static ForgeConfigSpec.IntValue BLASTER_COOLDOWN;
    public static ForgeConfigSpec.DoubleValue RAPID_FIRE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue RAPID_FIRE_COOLDOWN_MULTIPLIER;

    public static final String CATEGORY_TESLA_CANNON = "tesla_cannon";
    public static ForgeConfigSpec.DoubleValue TESLA_CANNON_CHARGE_DAMAGE;
    public static ForgeConfigSpec.DoubleValue TESLA_CANNON_CHARGE_SCALE;
    public static ForgeConfigSpec.DoubleValue CAPACITANCE_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue CAPACITANCE_SCALE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue QUASAR_SCALE_ADDITION;
    public static ForgeConfigSpec.DoubleValue REBOUND_BOUNCE_MULTIPLIER;

    public static final String CATEGORY_MOBS = "mobs";

    public static final String CATEGORY_FROWZY = "frowzy";
    public static ForgeConfigSpec.IntValue FROWZY_SPAWN_HEIGHT;
    public static ForgeConfigSpec.DoubleValue FROWZY_REPLACE_ZOMBIE_CHANCE;

    public static final String CATEGORY_GUZZLER = "guzzler";
    public static ForgeConfigSpec.IntValue GUZZLER_SPAWN_HEIGHT;

    public static final String CATEGORY_HANGING_SPIDER = "hanging_spider";
    public static ForgeConfigSpec.IntValue HANGING_SPIDER_SPAWN_HEIGHT;

    public static final String CATEGORY_TERROR = "terror";
    public static ForgeConfigSpec.IntValue TERROR_SPAWN_HEIGHT;

    public static final String CATEGORY_TREMBLER = "trembler";
    public static ForgeConfigSpec.IntValue TREMBLER_SPAWN_HEIGHT;

    public static final String CATEGORY_UMBER_SPIDER = "umber_spider";
    public static ForgeConfigSpec.IntValue UMBER_SPIDER_SPAWN_HEIGHT;
    public static ForgeConfigSpec.DoubleValue UMBER_SPIDER_REPLACE_SPIDER_CHANCE;

    public static final String CATEGORY_VOLT = "volt";
    public static ForgeConfigSpec.IntValue VOLT_SPAWN_HEIGHT;
    public static ForgeConfigSpec.BooleanValue VOLT_SPAWNS_DURING_STORM;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        // Items
        COMMON_BUILDER.push(CATEGORY_ITEMS);

        // Blaster
        COMMON_BUILDER.push(CATEGORY_BLASTER);
        BLASTER_BOLT_DAMAGE = COMMON_BUILDER.comment("Base damage for Laser Bolts shot from Blaster").defineInRange("blasterBoltDamage", 5.0, 0, Integer.MAX_VALUE);
        BLASTER_COOLDOWN = COMMON_BUILDER.comment("Cooldown for each Blaster shot in ticks").defineInRange("blasterCooldown", 15, 0, Integer.MAX_VALUE);
        RAPID_FIRE_DAMAGE = COMMON_BUILDER.comment("Base damage for Laser Bolts shot from Blaster with Rapid Fire enchantment").defineInRange("rapidFireDamage", 5.0, 0, Integer.MAX_VALUE);
        RAPID_FIRE_COOLDOWN_MULTIPLIER = COMMON_BUILDER.comment("Multiplier for Rapid Fire enchantment cooldown reduction").defineInRange("rapidFireCooldownMultiplier", 3.0, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        // Tesla Cannon
        COMMON_BUILDER.push(CATEGORY_TESLA_CANNON);
        TESLA_CANNON_CHARGE_DAMAGE = COMMON_BUILDER.comment("Base damage for Electric Charges shot by a Tesla Cannon").defineInRange("teslaCannonChargeDamage", 4.0, 0, Integer.MAX_VALUE);
        TESLA_CANNON_CHARGE_SCALE = COMMON_BUILDER.comment("Base scale for Electric Charges shot by a Tesla Cannon").defineInRange("teslaCannonChargeScale", 1.0, 0, Integer.MAX_VALUE);
        CAPACITANCE_DAMAGE_MULTIPLIER = COMMON_BUILDER.comment("Damage multiplier for Capacitance enchantment").defineInRange("capacitanceDamageMultiplier", 1.0, 0, Integer.MAX_VALUE);
        CAPACITANCE_SCALE_MULTIPLIER = COMMON_BUILDER.comment("Scale multiplier for Capacitance enchantment").defineInRange("capacitanceScaleMultiplier", 1.0, 0, Integer.MAX_VALUE);
        QUASAR_SCALE_ADDITION = COMMON_BUILDER.comment("Additional scale for Quasar enchantment").defineInRange("quasarScaleAddition", 2.0, 0, Integer.MAX_VALUE);
        REBOUND_BOUNCE_MULTIPLIER = COMMON_BUILDER.comment("Multiplier for Rebound enchantment bounces").defineInRange("reboundBounceMultiplier", 1.0, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.pop();

        // Mobs
        COMMON_BUILDER.push(CATEGORY_MOBS);

        // Frowzy
        COMMON_BUILDER.push(CATEGORY_FROWZY);
        FROWZY_SPAWN_HEIGHT = COMMON_BUILDER.comment("Maximum height frowzy can spawn").defineInRange("frowzySpawnHeight", 32, -64, 320);
        FROWZY_REPLACE_ZOMBIE_CHANCE = COMMON_BUILDER.comment("Chance for frowzy to replace zombie spawns underground").defineInRange("frowzyReplaceZombieChance", 0.5, 0.0, 1.0);
        COMMON_BUILDER.pop();

        // Guzzler
        COMMON_BUILDER.push(CATEGORY_GUZZLER);
        GUZZLER_SPAWN_HEIGHT = COMMON_BUILDER.comment("Maximum height guzzlers can spawn").defineInRange("guzzlerSpawnHeight", -16, -64, 320);
        COMMON_BUILDER.pop();

        // Hanging Spider
        COMMON_BUILDER.push(CATEGORY_HANGING_SPIDER);
        HANGING_SPIDER_SPAWN_HEIGHT = COMMON_BUILDER.comment("Maximum height hanging spiders can spawn").defineInRange("hangingSpiderSpawnHeight", 32, -64, 320);
        COMMON_BUILDER.pop();

        // Terror
        COMMON_BUILDER.push(CATEGORY_TERROR);
        TERROR_SPAWN_HEIGHT = COMMON_BUILDER.comment("Maximum height terrors can spawn").defineInRange("terrorSpawnHeight", -16, -64, 320);
        COMMON_BUILDER.pop();

        // Trembler
        COMMON_BUILDER.push(CATEGORY_TREMBLER);
        TREMBLER_SPAWN_HEIGHT = COMMON_BUILDER.comment("Maximum height tremblers can spawn").defineInRange("tremblerSpawnHeight", 0, -64, 320);
        COMMON_BUILDER.pop();

        // Umber Spider
        COMMON_BUILDER.push(CATEGORY_UMBER_SPIDER);
        UMBER_SPIDER_SPAWN_HEIGHT = COMMON_BUILDER.comment("Maximum height umber spiders can spawn").defineInRange("umberSpiderSpawnHeight", -16, -64, 320);
        UMBER_SPIDER_REPLACE_SPIDER_CHANCE = COMMON_BUILDER.comment("Chance for umber spiders to replace spider spawns underground").defineInRange("umberSpiderReplaceSpiderChance", 0.25, 0.0, 1.0);
        COMMON_BUILDER.pop();

        // Volt
        COMMON_BUILDER.push(CATEGORY_VOLT);
        VOLT_SPAWN_HEIGHT = COMMON_BUILDER.comment("Maximum height volts can spawn").defineInRange("voltSpawnHeight", 0, -64, 320);
        VOLT_SPAWNS_DURING_STORM = COMMON_BUILDER.comment("Do volts spawn on the surface during thunderstorms").define("voltSpawnsDuringStorm", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
