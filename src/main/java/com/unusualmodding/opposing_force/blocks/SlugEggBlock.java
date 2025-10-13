package com.unusualmodding.opposing_force.blocks;

import com.unusualmodding.opposing_force.entity.Slug;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class SlugEggBlock extends Block {

    public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 1, 3);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape UP_AABB;
    protected static final VoxelShape DOWN_AABB;

    public SlugEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(EGGS, 1));
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.playSound(null, pos, SoundEvents.SLIME_BLOCK_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
        level.destroyBlock(pos, false);
        int i = state.getValue(EGGS);
        for (int j = 0; j < i; j++) {
            Vec3 vec3 = pos.getCenter();
            Slug slug = OPEntities.SLUG.get().create(level);
            if (slug != null) {
                slug.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
                level.addFreshEntity(slug);
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState blockState, boolean flag) {
        level.levelEvent(3009, pos, 0);
        level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
        level.scheduleTick(pos, this, 1200 + level.random.nextInt(300));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        this.tryTrample(level, pos, entity, 100);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        this.tryTrample(level, pos, entity, 3);
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    private void tryTrample(Level level, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(level, trampler)) {
            if (!level.isClientSide && level.random.nextInt(chances) == 0) {
                level.destroyBlock(pos, false);
            }
        }
    }

    private boolean canTrample(Level level, Entity trampler) {
        if (!(trampler instanceof LivingEntity) || trampler instanceof Slug) {
            return false;
        } else {
            return (trampler instanceof Player || ForgeEventFactory.getMobGriefingEvent(level, trampler));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case DOWN -> DOWN_AABB;
            case UP -> UP_AABB;
            case NORTH -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            case EAST -> EAST_AABB;
        };
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.setValue(EGGS, Math.min(3, blockstate.getValue(EGGS) + 1)).setValue(FACING, context.getClickedFace());
        } else {
            return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockPos = pos.relative(direction.getOpposite());
        return level.getBlockState(blockPos).isFaceSturdy(level, blockPos, direction);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor level, BlockPos pos, BlockPos blockPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(state, direction, blockState, level, pos, blockPos);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(EGGS) < 3 || super.canBeReplaced(state, context);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(EGGS, FACING);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter blockGetter, BlockPos pos, PathComputationType computationType) {
        return false;
    }

    static {
        DOWN_AABB = Block.box(0.0D, 7.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        UP_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
        EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 9.0D, 16.0D, 16.0D);
        WEST_AABB = Block.box(7.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 9.0D);
        NORTH_AABB = Block.box(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 16.0D);
    }
}
