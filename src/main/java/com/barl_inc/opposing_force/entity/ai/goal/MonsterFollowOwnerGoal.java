package com.barl_inc.opposing_force.entity.ai.goal;

import com.barl_inc.opposing_force.entity.base.TameableMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

public class MonsterFollowOwnerGoal extends Goal {

    private final TameableMonster tamedMonster;
    private LivingEntity owner;
    private final LevelReader level;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private final float startDistance;
    private float oldWaterCost;
    private final boolean canFly;

    public MonsterFollowOwnerGoal(TameableMonster tamedMonster, double speedModifier, float startDistance, float stopDistance, boolean canFly) {
        this.tamedMonster = tamedMonster;
        this.level = tamedMonster.level();
        this.speedModifier = speedModifier;
        this.navigation = tamedMonster.getNavigation();
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.canFly = canFly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(tamedMonster.getNavigation() instanceof GroundPathNavigation) && !(tamedMonster.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for MonsterFollowOwnerGoal");
        }
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.tamedMonster.getOwner();
        if (owner == null) {
            return false;
        } else if (owner.isSpectator()) {
            return false;
        } else if (this.unableToMove()) {
            return false;
        } else if (this.tamedMonster.distanceToSqr(owner) < (double) (this.startDistance * this.startDistance)) {
            return false;
        } else {
            this.owner = owner;
            return this.shouldFollow() && !this.isInCombat();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.navigation.isDone()) {
            return false;
        } else if (this.unableToMove()) {
            return false;
        } else {
            return !(this.tamedMonster.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance)) && this.shouldFollow() && !this.isInCombat();
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tamedMonster.getPathfindingMalus(BlockPathTypes.WATER);
        this.tamedMonster.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.tamedMonster.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.tamedMonster.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tamedMonster.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            if (this.tamedMonster.distanceToSqr(this.owner) >= 144.0D) {
                this.teleportToOwner();
            } else {
                this.navigation.moveTo(this.owner, this.speedModifier);
            }
        }
    }

    private boolean unableToMove() {
        return this.tamedMonster.isOrderedToSit() || this.tamedMonster.isPassenger() || this.tamedMonster.isLeashed();
    }

    private void teleportToOwner() {
        BlockPos blockpos = this.owner.blockPosition();
        for (int i = 0; i < 10; ++i) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }
    }

    private boolean maybeTeleportTo(int x, int y, int z) {
        if (Math.abs((double) x - this.owner.getX()) < 2.0D && Math.abs((double) z - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.tamedMonster.moveTo((double) x + 0.5D, y, (double) z + 0.5D, this.tamedMonster.getYRot(), this.tamedMonster.getXRot());
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos blockPos) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, blockPos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.level.getBlockState(blockPos.below());
            if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = blockPos.subtract(this.tamedMonster.blockPosition());
                return this.level.noCollision(this.tamedMonster, this.tamedMonster.getBoundingBox().move(blockpos));
            }
        }
    }

    private boolean shouldFollow() {
        return tamedMonster.getCommand() == 2;
    }

    private boolean isInCombat() {
        Entity owner = tamedMonster.getOwner();
        if (owner != null) {
            return tamedMonster.distanceTo(owner) < 30 && tamedMonster.getTarget() != null && tamedMonster.getTarget().isAlive();
        }
        return false;
    }

    private int randomIntInclusive(int min, int max) {
        return this.tamedMonster.getRandom().nextInt(max - min + 1) + min;
    }
}