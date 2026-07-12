package com.barl_inc.opposing_force.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class OPFoodValues {

    public static final FoodProperties FIRE_GEL = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.1F).fast()
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0), 1.0F)
            .build();

    public static final FoodProperties INFERNO_PIE = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.3F).fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0), 1.0F)
            .build();

    public static final FoodProperties RAW_TERROR_LEG = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3F)
            .build();

    public static final FoodProperties FRIED_TERROR_LEG = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.6F)
            .build();

    public static final FoodProperties SPICY_TERROR_LEG = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0F)
            .build();

    public static final FoodProperties RAW_TART = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.2F)
            .build();

    public static final FoodProperties COOKED_TART = (new FoodProperties.Builder())
            .nutrition(5).saturationModifier(0.8F)
            .build();
}
