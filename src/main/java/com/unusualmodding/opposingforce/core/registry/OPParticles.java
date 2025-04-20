package com.unusualmodding.opposingforce.core.registry;

import com.unusualmodding.opposingforce.OpposingForce;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OpposingForce.MODID);
    public static final RegistryObject<SimpleParticleType> ELECTRIC_ORB = PARTICLE_TYPES.register("electric_orb", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> LARGE_ELECTRIC_ORB = PARTICLE_TYPES.register("large_electric_orb", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ELECTRIC_ORB_IMPACT = PARTICLE_TYPES.register("electric_orb_impact", () -> new SimpleParticleType(false));
}