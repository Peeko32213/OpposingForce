package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.particles.ElectricChargeParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OpposingForce.MOD_ID);

    public static final RegistryObject<ElectricChargeParticleType> ELECTRIC_CHARGE = PARTICLE_TYPES.register("electric_charge", () -> new ElectricChargeParticleType(false));
    public static final RegistryObject<SimpleParticleType> LASER_BOLT_DUST = PARTICLE_TYPES.register("laser_bolt_dust", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ICE_LASER_BOLT_DUST = PARTICLE_TYPES.register("ice_laser_bolt_dust", () -> new SimpleParticleType(false));

}