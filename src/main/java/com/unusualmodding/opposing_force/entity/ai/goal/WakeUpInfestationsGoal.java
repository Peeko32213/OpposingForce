package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.blocks.InfestationBlock;
import com.unusualmodding.opposing_force.entity.base.TameableMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;

public class WakeUpInfestationsGoal extends Goal {

    private final LivingEntity monster;
    private int lookForFriends;
    private final Block toCheck;
    
    public WakeUpInfestationsGoal(LivingEntity entity, Block toCheck) {
        this.monster = entity;
        this.toCheck = toCheck;
    }

    public void notifyHurt() {
        notifyHurt(5);
    }
    public void notifyHurt(int tickDelay) {
        if (this.lookForFriends == 0) {
            this.lookForFriends = this.adjustedTickDelay(tickDelay);
        }
    }

    @Override
    public boolean canUse() {
        boolean friends = this.lookForFriends > 0;
        if (this.monster instanceof TameableMonster monster) {
            return friends && !monster.isTame();
        }
        return friends;
    }

    @Override
    public void tick() {
        this.lookForFriends--;
        if (this.lookForFriends <= 0) {
            Level level = this.monster.level();
            RandomSource random = this.monster.getRandom();
            BlockPos blockpos = this.monster.blockPosition();
            for (int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
                for (int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                    for (int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                        BlockPos blockpos1 = blockpos.offset(j, i, k);
                        BlockState blockstate = level.getBlockState(blockpos1);
                        Block block = blockstate.getBlock();
                        if (blockstate.is(toCheck) && block instanceof InfestationBlock) {
                            if (ForgeEventFactory.getMobGriefingEvent(level, this.monster)) {
                                level.destroyBlock(blockpos1, true, this.monster);
                            } else {
                                level.setBlock(blockpos1, ((InfestationBlock) block).hostStateByInfested(level.getBlockState(blockpos1)), 3);
                            }
                            if (random.nextFloat() < 0.25F) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}