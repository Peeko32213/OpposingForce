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

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.push(CATEGORY_ITEMS);
        COMMON_BUILDER.push(CATEGORY_BLASTER);

        BLASTER_BOLT_DAMAGE = COMMON_BUILDER
                .comment("Base damage for Laser Bolts shot from Blaster")
                .defineInRange("blasterBoltDamage", 5.0, 0, Integer.MAX_VALUE);

        BLASTER_COOLDOWN = COMMON_BUILDER
                .comment("Cooldown for each Blaster shot in ticks")
                .defineInRange("blasterCooldown", 15, 0, Integer.MAX_VALUE);

        RAPID_FIRE_DAMAGE = COMMON_BUILDER
                .comment("Base damage for Laser Bolts shot from Blaster with Rapid Fire enchantment")
                .defineInRange("rapidFireDamage", 5.0, 0, Integer.MAX_VALUE);

        RAPID_FIRE_COOLDOWN_MULTIPLIER = COMMON_BUILDER
                .comment("Multiplier for Rapid Fire enchantment cooldown reduction")
                .defineInRange("rapidFireCooldownMultiplier", 3.0, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(CATEGORY_TESLA_CANNON);

        TESLA_CANNON_CHARGE_DAMAGE = COMMON_BUILDER
                .comment("Base damage for Electric Charges shot by a Tesla Cannon")
                .defineInRange("teslaCannonChargeDamage", 4.0, 0, Integer.MAX_VALUE);

        TESLA_CANNON_CHARGE_SCALE = COMMON_BUILDER
                .comment("Base scale for Electric Charges shot by a Tesla Cannon")
                .defineInRange("teslaCannonChargeScale", 1.0, 0, Integer.MAX_VALUE);

        CAPACITANCE_DAMAGE_MULTIPLIER = COMMON_BUILDER
                .comment("Damage multiplier for Capacitance enchantment")
                .defineInRange("capacitanceDamageMultiplier", 1.0, 0, Integer.MAX_VALUE);

        CAPACITANCE_SCALE_MULTIPLIER = COMMON_BUILDER
                .comment("Scale multiplier for Capacitance enchantment")
                .defineInRange("capacitanceScaleMultiplier", 1.0, 0, Integer.MAX_VALUE);

        QUASAR_SCALE_ADDITION = COMMON_BUILDER
                .comment("Additional scale for Quasar enchantment")
                .defineInRange("quasarScaleAddition", 2.0, 0, Integer.MAX_VALUE);

        REBOUND_BOUNCE_MULTIPLIER = COMMON_BUILDER
                .comment("Multiplier for Rebound enchantment bounces")
                .defineInRange("reboundBounceMultiplier", 1.0, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
