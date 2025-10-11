package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.utils.OPMath;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class OPEntityDataSerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, OpposingForce.MOD_ID);

    public static final RegistryObject<EntityDataSerializer<Optional<Vec3>>> OPTIONAL_VEC_3 = ENTITY_DATA_SERIALIZERS.register("optional_vec_3", () -> EntityDataSerializer.optional(OPMath::writeVec3, OPMath::readVec3));

}
