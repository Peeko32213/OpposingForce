package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.projectile.UmberDagger;
import com.unusualmodding.opposing_force.entity.projectile.DicerLaser;
import com.unusualmodding.opposing_force.entity.projectile.Tomahawk;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class OPDamageTypes {

    public static final ResourceKey<DamageType> ELECTRIC = register("electric");
    public static final ResourceKey<DamageType> ELECTRIFIED = register("electrified");
    public static final ResourceKey<DamageType> GLOOM_TOXIN = register("gloom_toxin");
    public static final ResourceKey<DamageType> LASER = register("laser");
    public static final ResourceKey<DamageType> LASER_BOLT = register("laser_bolt");
    public static final ResourceKey<DamageType> TOMAHAWK = register("tomahawk");
    public static final ResourceKey<DamageType> UMBER_DAGGER = register("umber_dagger");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(ELECTRIC, new DamageType("electric", 0.1F));
        context.register(ELECTRIFIED, new DamageType("electrified", 0.0F));
        context.register(GLOOM_TOXIN, new DamageType("gloom_toxin", 0.0F));
        context.register(LASER, new DamageType("laser", 0.0F));
        context.register(TOMAHAWK, new DamageType("tomahawk", 0.1F));
        context.register(LASER_BOLT, new DamageType("laser_bolt", 0.1F));
        context.register(UMBER_DAGGER, new DamageType("umber_dagger", 0.1F));
    }

    public static DamageSource laser(Level level, DicerLaser laser, @Nullable Entity indirectEntity) {
        return level.damageSources().source(LASER, laser, indirectEntity);
    }

    public static DamageSource tomahawk(Level level, Tomahawk tomahawk, @Nullable Entity indirectEntity) {
        return level.damageSources().source(TOMAHAWK, tomahawk, indirectEntity);
    }

    public static DamageSource umberDagger(Level level, UmberDagger umberKnife, @Nullable Entity indirectEntity) {
        return level.damageSources().source(UMBER_DAGGER, umberKnife, indirectEntity);
    }

    public static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(OpposingForce.MOD_ID, name));
    }
}
