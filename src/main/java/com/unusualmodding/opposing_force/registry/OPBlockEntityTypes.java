package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.entity.OPSkullBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, OpposingForce.MOD_ID);

    public static final RegistryObject<BlockEntityType<OPSkullBlockEntity>> MOB_HEAD =
            BLOCK_ENTITIES.register("mob_head", () ->
                    BlockEntityType.Builder.of(OPSkullBlockEntity::new,
                            OPBlocks.DICER_HEAD.get(),
                            OPBlocks.DICER_WALL_HEAD.get())
                                    .build(null));

}
