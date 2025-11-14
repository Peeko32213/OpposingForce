package com.unusualmodding.opposing_force.blocks;

import com.unusualmodding.opposing_force.blocks.entity.MobHeadBlockEntity;
import com.unusualmodding.opposing_force.registry.OPBlockEntityTypes;
import com.unusualmodding.opposing_force.registry.OPBlocks;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
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
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (this.type == Types.SKYVERN) return SKYVERN_SHAPE;
        else if (this.type == Types.TART) return TART_SHAPE;
        else if (this.type == Types.WHIZZ) return WHIZZ_SHAPE;
        else return SHAPE;
    }

    @Override
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(ROTATION, RotationSegment.convertToSegment(context.getRotation()));
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), ROTATIONS));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
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
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull PathComputationType computationType) {
        return false;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide) {
            if (shouldAnimate(state)) {
                return createTickerHelper(type, OPBlockEntityTypes.MOB_HEAD.get(), MobHeadBlockEntity::animation);
            }
        }
        return null;
    }

    public static boolean shouldAnimate(BlockState state) {
        if (state.is(OPBlocks.WHIZZ_HEAD.getFirst().get()) || state.is(OPBlocks.WHIZZ_HEAD.getSecond().get())) return true;
        else if (state.is(OPBlocks.SKYVERN_HEAD.getFirst().get()) || state.is(OPBlocks.SKYVERN_HEAD.getSecond().get())) return true;
        else return false;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new MobHeadBlockEntity(pos, state);
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