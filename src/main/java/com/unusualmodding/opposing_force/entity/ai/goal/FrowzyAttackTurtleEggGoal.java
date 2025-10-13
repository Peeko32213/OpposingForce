package com.unusualmodding.opposing_force.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class FrowzyAttackTurtleEggGoal extends RemoveBlockGoal {

    public FrowzyAttackTurtleEggGoal(PathfinderMob frowzy, double speed, int chance) {
        super(Blocks.TURTLE_EGG, frowzy, speed, chance);
    }

    public void playDestroyProgressSound(LevelAccessor level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
    }

    public void playBreakSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
    }

    public double acceptedDistance() {
        return 1.14D;
    }
}