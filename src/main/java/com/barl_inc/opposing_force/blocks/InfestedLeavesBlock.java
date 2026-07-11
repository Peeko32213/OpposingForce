package com.barl_inc.opposing_force.blocks;

import com.barl_inc.opposing_force.blocks.utils.FruitLeaves;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class InfestedLeavesBlock extends LeavesBlock implements FruitLeaves {

    private final Supplier<? extends EntityType<?>> toSpawn;
    private final Supplier<Block> leaves;

    public InfestedLeavesBlock(BlockBehaviour.Properties properties, Supplier<? extends EntityType<?>> toSpawn, Supplier<Block> leaves) {
        super(properties);
        this.toSpawn = toSpawn;
        this.leaves = leaves;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide) this.spawnInfestation((ServerLevel) level, pos);
        level.playSound(null, pos, OPSoundEvents.TART_PLUCK.get(), SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        level.setBlock(pos, leaves.get().defaultBlockState().setValue(LeavesBlock.PERSISTENT, blockState.getValue(LeavesBlock.PERSISTENT)).setValue(LeavesBlock.DISTANCE, blockState.getValue(LeavesBlock.DISTANCE)), 2);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            this.spawnInfestation(level, pos);
        }
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (this.decaying(state)) {
            dropResources(state, level, pos);
            if (level.getRandom().nextFloat() < 0.1F) this.spawnInfestation(level, pos);
            level.removeBlock(pos, false);
        }
    }

    private void spawnInfestation(ServerLevel level, BlockPos pos) {
        if (toSpawn.get() == null) return;
        Entity entity = toSpawn.get().create(level);
        if (entity != null) {
            if(entity instanceof Mob mob) {
                entity.moveTo(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 0.0F, 0.0F);
                level.addFreshEntity(entity);
                ForgeEventFactory.onFinalizeSpawn(mob, level, level.getCurrentDifficultyAt(pos), MobSpawnType.TRIGGERED, null, null);
            }
        }
    }

    @Override
    public FruitLeaves.FruitState getAge() {
        return FruitLeaves.FruitState.FRUITFUL;
    }
}
