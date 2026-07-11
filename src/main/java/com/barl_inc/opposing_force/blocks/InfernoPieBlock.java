package com.barl_inc.opposing_force.blocks;

import com.mojang.datafixers.util.Pair;
import com.barl_inc.opposing_force.registry.OPItems;
import com.barl_inc.opposing_force.registry.tags.OPItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class InfernoPieBlock extends Block {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 3);

	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 5.0D, 15.0D);

    public InfernoPieBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BITES, 0));
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		ItemStack heldStack = player.getItemInHand(hand);
        boolean canSlice = ModList.get().isLoaded("farmersdelight") ? heldStack.is(OPItemTags.KNIVES) : heldStack.is(ItemTags.SWORDS);

        if (level.isClientSide) {
			if (canSlice) {
				return cutSlice(level, pos, state, player);
			}
			if (this.consumeBite(level, pos, state, player) == InteractionResult.SUCCESS) {
				return InteractionResult.SUCCESS;
			}
			if (heldStack.isEmpty()) {
				return InteractionResult.CONSUME;
			}
		}

		if (canSlice) {
			return cutSlice(level, pos, state, player);
		}
		return this.consumeBite(level, pos, state, player);
	}

	protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player playerIn) {
		if (!playerIn.canEat(false)) {
			return InteractionResult.PASS;
		} else {
			ItemStack sliceStack = new ItemStack(OPItems.INFERNO_PIE_SLICE.get());
			ItemStack sliceCopy = sliceStack.copy();
			FoodProperties sliceFood = sliceStack.getItem().getFoodProperties();

			playerIn.getFoodData().eat(sliceStack.getItem(), sliceStack);
			ForgeEventFactory.onItemUseFinish(playerIn, sliceCopy, 0, ItemStack.EMPTY);
			if (sliceStack.getItem().isEdible() && sliceFood != null) {
				for (Pair<MobEffectInstance, Float> pair : sliceFood.getEffects()) {
					if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
						playerIn.addEffect(new MobEffectInstance(pair.getFirst()));
					}
				}
			}

			int bites = state.getValue(BITES);
			if (bites < 4 - 1) {
				level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
			} else {
				level.removeBlock(pos, false);
			}
			level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
			return InteractionResult.SUCCESS;
		}
	}

	protected InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
		int bites = state.getValue(BITES);
		if (bites < 4 - 1) {
			level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
		} else {
			level.removeBlock(pos, false);
		}

		Direction direction = player.getDirection().getOpposite();
		ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, new ItemStack(OPItems.INFERNO_PIE_SLICE.get()));
		entity.setDeltaMovement(direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
		level.addFreshEntity(entity);
		level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
		return InteractionResult.SUCCESS;
	}

	@Override
	public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
		return facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).isSolid();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, BITES);
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, @NotNull Level level, @NotNull BlockPos pos) {
		return 4 - blockState.getValue(BITES);
	}

	@Override
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType computationType) {
		return false;
	}
}