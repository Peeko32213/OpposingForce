package com.barl_inc.opposing_force.blocks;

import com.barl_inc.opposing_force.blocks.entity.MobHeadBlockEntity;
import com.barl_inc.opposing_force.registry.OPBlockEntityTypes;
import com.barl_inc.opposing_force.registry.OPBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class MobHeadBlock extends BaseEntityBlock implements Equipable {

    public static final int MAX = RotationSegment.getMaxSegmentIndex();
    private final Type type;
    private static final int ROTATIONS = MAX + 1;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    protected static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 8, 12);
    protected static final VoxelShape WHIZZ_SHAPE = Block.box(4, 0, 4, 12, 6, 12);
    protected static final VoxelShape TART_SHAPE = Block.box(4.5, 0, 4.5, 11.5, 7, 11.5);
    protected static final VoxelShape SKYVERN_SHAPE = Block.box(2, 0, 2, 14, 12, 14);

    public MobHeadBlock(Type type, Properties properties) {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return switch (this.type) {
            case Types.SKYVERN -> SKYVERN_SHAPE;
            case Types.TART -> TART_SHAPE;
            case Types.WHIZZ -> WHIZZ_SHAPE;
            case null, default -> SHAPE;
        };
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(ROTATION, RotationSegment.convertToSegment(context.getRotation()));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), ROTATIONS));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), ROTATIONS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(ROTATION);
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType computationType) {
        return false;
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            if (shouldAnimate(state)) {
                return createTickerHelper(type, OPBlockEntityTypes.MOB_HEAD.get(), MobHeadBlockEntity::animation);
            }
        }
        return null;
    }

    public static boolean shouldAnimate(BlockState state) {
        if (state.is(OPBlocks.WHIZZ_HEAD.getFirst().get()) || state.is(OPBlocks.WHIZZ_HEAD.getSecond().get())) {
            return true;
        }
        return state.is(OPBlocks.SKYVERN_HEAD.getFirst().get()) || state.is(OPBlocks.SKYVERN_HEAD.getSecond().get());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MobHeadBlockEntity(pos, state);
    }

    @Nullable
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    public interface Type {
    }

    public enum Types implements Type {
        DICER,
        FROWZY,
        RAMBLER_ANGRY,
        RAMBLER_CLASSIC,
        RAMBLER_CRUNDLY,
        RAMBLER_DWARVEN,
        RAMBLER_EVIL,
        RAMBLER_GRINNING,
        RAMBLER_IMPRISONED,
        RAMBLER_INDOMITABLE,
        RAMBLER_LEERING,
        RAMBLER_MAGMATIC,
        RAMBLER_MUSICAL,
        RAMBLER_NOSY,
        RAMBLER_SKELETAL,
        RAMBLER_SMILING,
        RAMBLER_STRANGE,
        RAMBLER_VALIANT,
        SKYVERN,
        TART,
        WHIZZ
    }
}