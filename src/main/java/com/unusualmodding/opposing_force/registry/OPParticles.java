package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.particles.lightning.LightningParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OpposingForce.MOD_ID);

    public static final RegistryObject<SimpleParticleType> LASER_BOLT_DUST = PARTICLE_TYPES.register("laser_bolt_dust", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MOON_SHOES = PARTICLE_TYPES.register("moon_shoes", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LASER_SWEEP = PARTICLE_TYPES.register("laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WHITE_LASER_SWEEP = PARTICLE_TYPES.register("white_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LIGHT_GRAY_LASER_SWEEP = PARTICLE_TYPES.register("light_gray_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GRAY_LASER_SWEEP = PARTICLE_TYPES.register("gray_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BLACK_LASER_SWEEP = PARTICLE_TYPES.register("black_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BROWN_LASER_SWEEP = PARTICLE_TYPES.register("brown_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RED_LASER_SWEEP = PARTICLE_TYPES.register("red_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ORANGE_LASER_SWEEP = PARTICLE_TYPES.register("orange_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> YELLOW_LASER_SWEEP = PARTICLE_TYPES.register("yellow_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LIME_LASER_SWEEP = PARTICLE_TYPES.register("lime_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GREEN_LASER_SWEEP = PARTICLE_TYPES.register("green_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> CYAN_LASER_SWEEP = PARTICLE_TYPES.register("cyan_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LIGHT_BLUE_LASER_SWEEP = PARTICLE_TYPES.register("light_blue_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BLUE_LASER_SWEEP = PARTICLE_TYPES.register("blue_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PURPLE_LASER_SWEEP = PARTICLE_TYPES.register("purple_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> MAGENTA_LASER_SWEEP = PARTICLE_TYPES.register("magenta_laser_sweep", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PINK_LASER_SWEEP = PARTICLE_TYPES.register("pink_laser_sweep", () -> new SimpleParticleType(true));

    public static final RegistryObject<LightningParticleType> LIGHTNING = PARTICLE_TYPES.register("lightning", () -> new LightningParticleType(false));
}