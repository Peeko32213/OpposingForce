package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.particles.LightningBallParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OpposingForce.MOD_ID);
    public static final RegistryObject<LightningBallParticleType> ELECTRIC_ORB = PARTICLE_TYPES.register("electric_orb", () -> new LightningBallParticleType(false));
    public static final RegistryObject<SimpleParticleType> LARGE_ELECTRIC_ORB = PARTICLE_TYPES.register("large_electric_orb", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ELECTRIC_ORB_IMPACT = PARTICLE_TYPES.register("electric_orb_impact", () -> new SimpleParticleType(false));
}