package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.particles.lightning.LightningParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OPParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, OpposingForce.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MOON_SHOES = PARTICLE_TYPES.register("moon_shoes", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LASER_SWEEP = PARTICLE_TYPES.register("laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WHITE_LASER_SWEEP = PARTICLE_TYPES.register("white_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHT_GRAY_LASER_SWEEP = PARTICLE_TYPES.register("light_gray_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAY_LASER_SWEEP = PARTICLE_TYPES.register("gray_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLACK_LASER_SWEEP = PARTICLE_TYPES.register("black_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BROWN_LASER_SWEEP = PARTICLE_TYPES.register("brown_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_LASER_SWEEP = PARTICLE_TYPES.register("red_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ORANGE_LASER_SWEEP = PARTICLE_TYPES.register("orange_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YELLOW_LASER_SWEEP = PARTICLE_TYPES.register("yellow_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIME_LASER_SWEEP = PARTICLE_TYPES.register("lime_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GREEN_LASER_SWEEP = PARTICLE_TYPES.register("green_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CYAN_LASER_SWEEP = PARTICLE_TYPES.register("cyan_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHT_BLUE_LASER_SWEEP = PARTICLE_TYPES.register("light_blue_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLUE_LASER_SWEEP = PARTICLE_TYPES.register("blue_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PURPLE_LASER_SWEEP = PARTICLE_TYPES.register("purple_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGENTA_LASER_SWEEP = PARTICLE_TYPES.register("magenta_laser_sweep", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PINK_LASER_SWEEP = PARTICLE_TYPES.register("pink_laser_sweep", () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LASER_BOLT_DUST = PARTICLE_TYPES.register("laser_bolt_dust", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LASER_IMPACT = PARTICLE_TYPES.register("laser_impact", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TREMBLING_SLAMMER_IMPACT = PARTICLE_TYPES.register("trembling_slammer_impact", () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_BOMB_EXPLOSION = PARTICLE_TYPES.register("fire_bomb_explosion", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KINETIC_BOMB_EXPLOSION = PARTICLE_TYPES.register("kinetic_bomb_explosion", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTNING_BOMB_EXPLOSION = PARTICLE_TYPES.register("lightning_bomb_explosion", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WHIZZ_BOMB_EXPLOSION = PARTICLE_TYPES.register("whizz_bomb_explosion", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_BOMB_FLASH = PARTICLE_TYPES.register("fire_bomb_flash", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KINETIC_BOMB_FLASH = PARTICLE_TYPES.register("kinetic_bomb_flash", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTNING_BOMB_FLASH = PARTICLE_TYPES.register("lightning_bomb_flash", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WHIZZ_BOMB_FLASH = PARTICLE_TYPES.register("whizz_bomb_flash", () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, LightningParticleType> LIGHTNING = PARTICLE_TYPES.register("lightning", () -> new LightningParticleType(false));
}