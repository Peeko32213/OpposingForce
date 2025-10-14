package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.blocks.InfestedAmethystBlock;
import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;

public class WhizzWakeUpFriendsGoal extends Goal {

    private final Whizz whizz;
    private int lookForFriends;

    public WhizzWakeUpFriendsGoal(Whizz whizz) {
        this.whizz = whizz;
    }

    public void notifyHurt() {
        if (this.lookForFriends == 0) {
            this.lookForFriends = this.adjustedTickDelay(5);
        }
    }

    @Override
    public boolean canUse() {
        return this.lookForFriends > 0 && !this.whizz.isTame();
    }

    @Override
    public void tick() {
        this.lookForFriends--;
        if (this.lookForFriends <= 0) {
            Level level = this.whizz.level();
            RandomSource random = this.whizz.getRandom();
            BlockPos blockpos = this.whizz.blockPosition();
            for (int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
                for (int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                    for (int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                        BlockPos blockpos1 = blockpos.offset(j, i, k);
                        BlockState blockstate = level.getBlockState(blockpos1);
                        Block block = blockstate.getBlock();
                        if (block instanceof InfestedAmethystBlock) {
                            if (ForgeEventFactory.getMobGriefingEvent(level, this.whizz)) {
                                level.destroyBlock(blockpos1, true, this.whizz);
                            } else {
                                level.setBlock(blockpos1, ((InfestedAmethystBlock) block).hostStateByInfested(level.getBlockState(blockpos1)), 3);
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