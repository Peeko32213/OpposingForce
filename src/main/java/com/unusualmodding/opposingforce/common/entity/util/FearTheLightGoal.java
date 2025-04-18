package com.unusualmodding.opposingforce.common.entity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class FearTheLightGoal extends Goal {
    protected final PathfinderMob creature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final double movementSpeed;
    private final Level world;
    private int executeChance;
    private final int lightLevel;

    public FearTheLightGoal(PathfinderMob mob, double movementSpeed) {
        this(mob, movementSpeed, 100, 10);
    }

    public FearTheLightGoal(PathfinderMob mob, double movementSpeed, int chance, int level) {
        this.creature = mob;
        this.movementSpeed = movementSpeed;
        this.world = mob.level();
        this.executeChance = chance;
        this.lightLevel = level;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (this.world.getMaxLocalRawBrightness(this.creature.blockPosition()) < lightLevel) {
            return false;
        } else {
            return this.isPossibleShelter();
        }
    }

    protected boolean isPossibleShelter() {
        Vec3 vec3 = this.findPossibleShelter();
        if (vec3 == null) {
            return false;
        } else {
            this.shelterX = vec3.x;
            this.shelterY = vec3.y;
            this.shelterZ = vec3.z;
            return true;
        }
    }

    public boolean canContinueToUse() {
        return !this.creature.getNavigation().isDone();
    }

    public void start() {
        this.creature.getNavigation().moveTo(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    @Nullable
    protected Vec3 findPossibleShelter() {
        RandomSource randomSource = this.creature.getRandom();
        BlockPos blockPos = this.creature.blockPosition();

        for(int lvt_3_1_ = 0; lvt_3_1_ < 10; ++lvt_3_1_) {
            BlockPos lvt_4_1_ = blockPos.offset(randomSource.nextInt(20) - 10, randomSource.nextInt(6) - 3, randomSource.nextInt(20) - 10);
            if (this.creature.level().getMaxLocalRawBrightness(lvt_4_1_) < lightLevel) {
                return Vec3.atBottomCenterOf(lvt_4_1_);
            }
        }

        return null;
    }
}
