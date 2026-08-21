package com.barl_inc.opposing_force.blocks;

import com.barl_inc.opposing_force.blocks.entity.MobHeadBlockEntity;
import com.barl_inc.opposing_force.registry.OPBlockEntityTypes;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("deprecation")
public class WallMobHeadBlock extends BaseEntityBlock implements Equipable {

    private final MobHeadBlock.Type type;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH, Block.box(4, 4, 8, 12, 12, 16),
                    Direction.SOUTH, Block.box(4, 4, 0, 12, 12, 8),
                    Direction.EAST, Block.box(0, 4, 4, 8, 12, 12),
                    Direction.WEST, Block.box(8, 4, 4, 16, 12, 12)
        )
    );

    private static final Map<Direction, VoxelShape> SKYVERN_AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH, Block.box(2, 2, 4, 14, 14, 16),
                    Direction.SOUTH, Block.box(2, 2, 0, 14, 14, 12),
                    Direction.EAST,  Block.box(0, 2, 2, 12, 14, 14),
                    Direction.WEST,  Block.box(4, 2, 2, 16, 14, 14)
            )
    );

    private static final Map<Direction, VoxelShape> TART_AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH, Block.box(4.5, 4, 8.5, 11.5, 11, 15.5),
                    Direction.SOUTH, Block.box(4.5, 4, 0.5, 11.5, 11, 7.5),
                    Direction.EAST, Block.box(0.5, 4, 4.5, 7.5, 11, 11.5),
                    Direction.WEST, Block.box(8.5, 4, 4.5, 15.5, 11, 11.5)
            )
    );

    private static final Map<Direction, VoxelShape> WHIZZ_AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH, Block.box(4, 4, 8, 12, 10, 16),
                    Direction.SOUTH, Block.box(4, 4, 0, 12, 10, 8),
                    Direction.EAST, Block.box(0, 4, 4, 8, 10, 12),
                    Direction.WEST, Block.box(8, 4, 4, 16, 10, 12)
            )
    );

    public WallMobHeadBlock(MobHeadBlock.Type type, Properties properties) {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return switch (this.type) {
            case MobHeadBlock.Types.SKYVERN -> SKYVERN_AABBS.get(state.getValue(FACING));
            case MobHeadBlock.Types.TART -> TART_AABBS.get(state.getValue(FACING));
            case MobHeadBlock.Types.WHIZZ -> WHIZZ_AABBS.get(state.getValue(FACING));
            case null, default -> AABBS.get(state.getValue(FACING));
        };
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for (Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (!blockgetter.getBlockState(blockpos.relative(direction)).canBeReplaced(context)) {
                    return blockstate;
                }
            }
        }
        return null;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(FACING);
    }

    public MobHeadBlock.Type getType() {
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
            if (MobHeadBlock.shouldAnimate(state)) {
                return createTickerHelper(type, OPBlockEntityTypes.MOB_HEAD.get(), MobHeadBlockEntity::animation);
            }
        }
        return null;
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
}