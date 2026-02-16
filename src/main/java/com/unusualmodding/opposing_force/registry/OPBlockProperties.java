package com.unusualmodding.opposing_force.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class OPBlockProperties {

    public static final BlockBehaviour.Properties TREMBLING_BLOCK = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(2.0F, 3.0F);
    public static final BlockBehaviour.Properties DEEP_WEB = BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().noCollission().requiresCorrectToolForDrops().strength(4.0F).pushReaction(PushReaction.DESTROY);
    public static final BlockBehaviour.Properties DEEP_SILK_BLOCK = BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).requiresCorrectToolForDrops().strength(1.0F).pushReaction(PushReaction.DESTROY).sound(SoundType.WOOL);

    public static final BlockBehaviour.Properties VILE_STONE = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).instrument(NoteBlockInstrument.DIDGERIDOO).sound(SoundType.SLIME_BLOCK).requiresCorrectToolForDrops().strength(3.0F, 1.0F);
    public static final BlockBehaviour.Properties VILE_COBBLESTONE = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).instrument(NoteBlockInstrument.DIDGERIDOO).sound(SoundType.SLIME_BLOCK).requiresCorrectToolForDrops().strength(3.0F, 1.5F);
    public static final BlockBehaviour.Properties SLUG_EGGS = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).instrument(NoteBlockInstrument.DIDGERIDOO).sound(SoundType.SLIME_BLOCK).instabreak().noOcclusion().strength(1.0F);

    public static final BlockBehaviour.Properties INFERNO_PIE = BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY).lightLevel(state -> 5);
    public static final BlockBehaviour.Properties GUZZLER_SCALE_BLOCK = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.GUITAR).sound(SoundType.BASALT).requiresCorrectToolForDrops().strength(2.0F, 3.0F);

    public static final BlockBehaviour.Properties MOB_HEAD = BlockBehaviour.Properties.of().strength(1.0F).pushReaction(PushReaction.DESTROY).mapColor(MapColor.STONE);

    public static final BlockBehaviour.Properties DICER_LENS_BLOCK = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instrument(NoteBlockInstrument.CHIME).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 3.0F);

    public static BlockBehaviour.Properties mobHead(NoteBlockInstrument instrument) {
        return BlockBehaviour.Properties.of().strength(1.0F).pushReaction(PushReaction.DESTROY).mapColor(MapColor.STONE).instrument(instrument);
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }
}
