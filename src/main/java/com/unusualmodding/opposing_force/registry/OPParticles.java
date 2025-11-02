package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OpposingForce.MOD_ID);

    public static final RegistryObject<SimpleParticleType> LASER_BOLT_DUST = PARTICLE_TYPES.register("laser_bolt_dust", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MOON_SHOES = PARTICLE_TYPES.register("moon_shoes", () -> new SimpleParticleType(true));

}