package com.unusualmodding.opposing_force.blocks;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class InfestationBlock extends Block{

    private final Block hostBlock;
    private static final Map<BlockState, BlockState> INFESTED_TO_HOST_STATES = Maps.newIdentityHashMap();

    private final Supplier<? extends EntityType<?>> toSpawn;
    public InfestationBlock(Block hostBlock, Properties properties, Supplier<? extends EntityType<?>> toSpawn, float explosionResistance) {
        super(properties.destroyTime(hostBlock.defaultDestroyTime() / 2.0F).explosionResistance(explosionResistance));
        this.hostBlock = hostBlock;
        this.toSpawn = toSpawn;
    }

    public Block getHostBlock() {
        return this.hostBlock;
    }

    private void spawnInfestation(ServerLevel level, BlockPos pos) {
        if(toSpawn.get() == null) return;
        Entity entity = toSpawn.get().create(level);
        if (entity != null) {
            entity.moveTo((double) pos.getX() + (double) 0.5F, pos.getY(), (double) pos.getZ() + (double) 0.5F, 0.0F, 0.0F);
            level.addFreshEntity(entity)
        }
    }

    @Override
    public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            this.spawnInfestation(level, pos);
        }
    }

    public BlockState hostStateByInfested(BlockState pInfested) {
        return getNewStateWithProperties(pInfested, () -> this.getHostBlock().defaultBlockState());
    }

    private static BlockState getNewStateWithProperties(BlockState state, Supplier<BlockState> supplier) {
        return InfestationBlock.INFESTED_TO_HOST_STATES.computeIfAbsent(state, (block) -> {
            BlockState blockState = supplier.get();
            for (Property property : block.getProperties()) {
                blockState = blockState.hasProperty(property) ? blockState.setValue(property, block.getValue(property)) : blockState;
            }
            return blockState;
        });
    }
}
