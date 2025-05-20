package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.projectile.ElectricBall;
import com.unusualmodding.opposing_force.entity.projectile.Tomahawk;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class OPDamageTypes {
    public static final ResourceKey<DamageType> TOMAHAWK = createKey("tomahawk");
    public static final ResourceKey<DamageType> ELECTRIFIED = createKey("electrified");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(TOMAHAWK, new DamageType("hole.tomahawk", 0.1F));
        context.register(ELECTRIFIED, new DamageType("hole.electrified", 0.1F));

    }

    public static DamageSource tomahawk(Level level, Tomahawk tomahawk, @Nullable Entity indirectEntity) {
        return level.damageSources().source(TOMAHAWK, tomahawk, indirectEntity);
    }

    public static DamageSource electrified(Level level, ElectricBall electricBall, @Nullable Entity indirectEntity) {
        return level.damageSources().source(ELECTRIFIED, electricBall, indirectEntity);
    }

    public static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(OpposingForce.MOD_ID, name));
    }

}
