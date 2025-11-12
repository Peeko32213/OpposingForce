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

    public static final RegistryObject<BlockEntityType<MobHeadBlockEntity>> MOB_HEAD = BLOCK_ENTITY_TYPES.register("mob_head", () -> BlockEntityType.Builder.of(MobHeadBlockEntity::new,
            OPBlocks.DICER_HEAD.getFirst().get(), OPBlocks.DICER_HEAD.getSecond().get(),
            OPBlocks.FROWZY_HEAD.getFirst().get(), OPBlocks.FROWZY_HEAD.getSecond().get(),
            OPBlocks.ANGRY_RAMBLER_SKULL.getFirst().get(), OPBlocks.ANGRY_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.CLASSIC_RAMBLER_SKULL.getFirst().get(), OPBlocks.CLASSIC_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.CRUNDLY_RAMBLER_SKULL.getFirst().get(), OPBlocks.CRUNDLY_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.DWARVEN_RAMBLER_SKULL.getFirst().get(), OPBlocks.DWARVEN_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.EVIL_RAMBLER_SKULL.getFirst().get(), OPBlocks.EVIL_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.GRINNING_RAMBLER_SKULL.getFirst().get(), OPBlocks.GRINNING_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.IMPRISONED_RAMBLER_SKULL.getFirst().get(), OPBlocks.IMPRISONED_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.INDOMITABLE_RAMBLER_SKULL.getFirst().get(), OPBlocks.INDOMITABLE_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.LEERING_RAMBLER_SKULL.getFirst().get(), OPBlocks.LEERING_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.MAGMATIC_RAMBLER_SKULL.getFirst().get(), OPBlocks.MAGMATIC_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.MUSICAL_RAMBLER_SKULL.getFirst().get(), OPBlocks.MUSICAL_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.NOSY_RAMBLER_SKULL.getFirst().get(), OPBlocks.NOSY_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.SKELETAL_RAMBLER_SKULL.getFirst().get(), OPBlocks.SKELETAL_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.SMILING_RAMBLER_SKULL.getFirst().get(), OPBlocks.SMILING_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.STRANGE_RAMBLER_SKULL.getFirst().get(), OPBlocks.STRANGE_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.VALIANT_RAMBLER_SKULL.getFirst().get(), OPBlocks.VALIANT_RAMBLER_SKULL.getSecond().get(),
            OPBlocks.TART_HEAD.getFirst().get(), OPBlocks.TART_HEAD.getSecond().get()
    ).build(null));


}
