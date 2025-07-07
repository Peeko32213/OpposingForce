package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.entity.MobHeadBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, OpposingForce.MOD_ID);

    public static final RegistryObject<BlockEntityType<MobHeadBlockEntity>> MOB_HEAD = BLOCK_ENTITY_TYPES.register("mob_head", () -> BlockEntityType.Builder.of(MobHeadBlockEntity::new, OPBlocks.DICER_HEAD.get(), OPBlocks.DICER_WALL_HEAD.get(), OPBlocks.FROWZY_HEAD.get(), OPBlocks.FROWZY_WALL_HEAD.get(), OPBlocks.RAMBLE_SKULL.get(), OPBlocks.RAMBLE_WALL_SKULL.get()).build(null));


}
