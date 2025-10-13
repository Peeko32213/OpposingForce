package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.blocks.InfestedAmethystBlock;
import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class WhizzWanderGoal extends Goal {

    private final Whizz whizz;

    @Nullable
    private Direction selectedDirection;
    private boolean doMerge;

    public WhizzWanderGoal(Whizz whizz) {
        this.whizz = whizz;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.whizz.getTarget() != null) {
            return false;
        } else if (!this.whizz.getNavigation().isDone()) {
            return false;
        } else {
            RandomSource randomsource = this.whizz.getRandom();
            if (ForgeEventFactory.getMobGriefingEvent(this.whizz.level(), this.whizz) && randomsource.nextInt(reducedTickDelay(10)) == 0) {
                this.selectedDirection = Direction.getRandom(randomsource);
                BlockPos blockpos = BlockPos.containing(this.whizz.getX(), this.whizz.getY() + (double) 0.5F, this.whizz.getZ()).relative(this.selectedDirection);
                BlockState blockstate = this.whizz.level().getBlockState(blockpos);
                if (InfestedAmethystBlock.isCompatibleHostBlock(blockstate)) {
                    this.doMerge = true;
                    return true;
                }
            }
            this.doMerge = false;
            return this.whizz.getNavigation().isDone() && this.whizz.getRandom().nextInt(10) == 0;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.doMerge && this.whizz.getNavigation().isInProgress();
    }

    @Override
    public void start() {
        if (!this.doMerge) {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                this.whizz.getNavigation().moveTo(this.whizz.getNavigation().createPath(BlockPos.containing(vec3), 1), 1.0);
            }
        } else {
            LevelAccessor levelaccessor = this.whizz.level();
            BlockPos blockpos = BlockPos.containing(this.whizz.getX(), this.whizz.getY() + (double) 0.5F, this.whizz.getZ()).relative(this.selectedDirection);
            BlockState blockstate = levelaccessor.getBlockState(blockpos);
            if (InfestedAmethystBlock.isCompatibleHostBlock(blockstate)) {
                levelaccessor.setBlock(blockpos, InfestedAmethystBlock.infestedStateByHost(blockstate), 3);
                this.whizz.spawnAnim();
                this.whizz.discard();
            }
        }
    }

    public Vec3 findPos() {
        Vec3 vec32;
        vec32 = this.whizz.getViewVector(0.0F);

        Vec3 vec33 = HoverRandomPos.getPos(this.whizz, 8, 7, vec32.x, vec32.z, 1.5707964F, 3, 1);
        return vec33 != null ? vec33 : AirAndWaterRandomPos.getPos(this.whizz, 8, 4, -2, vec32.x, vec32.z, 1.5707963705062866);
    }
}