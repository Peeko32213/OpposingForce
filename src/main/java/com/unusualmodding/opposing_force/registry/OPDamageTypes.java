package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
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

    public static final ResourceKey<DamageType> ELECTRIFIED = register("electrified");
    public static final ResourceKey<DamageType> GLOOM_TOXIN = register("gloom_toxin");
    public static final ResourceKey<DamageType> LASER = register("laser");
    public static final ResourceKey<DamageType> TOMAHAWK = register("tomahawk");
    public static final ResourceKey<DamageType> STONE_FALL = register("stone_fall");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(ELECTRIFIED, new DamageType(OpposingForce.MOD_ID + ".electrified", 0.0F));
        context.register(GLOOM_TOXIN, new DamageType(OpposingForce.MOD_ID + ".gloom_toxin", 0.0F));
        context.register(LASER, new DamageType(OpposingForce.MOD_ID + ".laser", 0.0F));
        context.register(TOMAHAWK, new DamageType(OpposingForce.MOD_ID + ".tomahawk", 0.1F));
        context.register(STONE_FALL, new DamageType(OpposingForce.MOD_ID + ".stone_fall", 0.0F));

    }

    public static DamageSource laser(Level level, DicerLaser laser, @Nullable Entity indirectEntity) {
        return level.damageSources().source(LASER, laser, indirectEntity);
    }

    public static DamageSource tomahawk(Level level, Tomahawk tomahawk, @Nullable Entity indirectEntity) {
        return level.damageSources().source(TOMAHAWK, tomahawk, indirectEntity);
    }

    public static DamageSource stone_fall(Level level, Entity causingEntity, @Nullable Entity directEntity) {
        return level.damageSources().source(STONE_FALL, causingEntity, directEntity);
    }

    public static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(OpposingForce.MOD_ID, name));
    }
}
