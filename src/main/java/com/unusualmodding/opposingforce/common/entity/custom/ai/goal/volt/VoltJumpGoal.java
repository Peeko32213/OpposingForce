package com.unusualmodding.opposingforce.common.entity.custom.ai.goal.volt;

import com.unusualmodding.opposingforce.common.entity.custom.monster.VoltEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.EnumSet;

public class VoltJumpGoal extends Goal {

    private VoltEntity volt;
    private BlockPos jumpTarget = null;
    private boolean hasPreformedJump = false;

    public VoltJumpGoal(VoltEntity entity) {
        this.volt = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = volt.getTarget();
        if (volt.onGround() && (target == null || !target.isAlive()) && volt.getRandom().nextInt(140) == 0) {
            BlockPos findTarget = findJumpTarget();
            if (findTarget != null) {
                jumpTarget = findTarget;
                return true;
            }
        }
        return false;
    }

    private BlockPos findJumpTarget() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.volt, 16, 10);
        if (vec3 != null) {
            BlockPos blockpos = BlockPos.containing(vec3);
            AABB aabb = this.volt.getBoundingBox().move(vec3.add(0.5F, 1, 0.5F).subtract(this.volt.position()));
            if (volt.level().getBlockState(blockpos.below()).isSolidRender(volt.level(), blockpos.below()) && volt.getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic(volt.level(), blockpos.mutable())) == 0.0F && volt.level().isUnobstructed(this.volt, Shapes.create(aabb))) {
                return blockpos;
            }
        }
        return null;
    }

    @Override
    public void start() {
        hasPreformedJump = false;
        volt.getNavigation().stop();
        volt.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(jumpTarget));
    }

    @Override
    public boolean canContinueToUse() {
        return (volt.isLeaping()) && jumpTarget != null;
    }

    @Override
    public void tick() {
        if (volt.isLeaping()) {
            if (!hasPreformedJump) {
                hasPreformedJump = true;
                Vec3 vec3 = this.volt.getDeltaMovement();
                Vec3 vec31 = new Vec3(this.jumpTarget.getX() + 0.5F - this.volt.getX(), 0.0D, this.jumpTarget.getZ() + 0.5F - this.volt.getZ());
                if (vec31.length() > 100) {
                    vec31 = vec3.normalize().scale(100);
                }
                if (vec31.lengthSqr() > 1.0E-7D) {
                    vec31 = vec31.scale(0.155F).add(vec3.scale(0.2D));
                }
                this.volt.setDeltaMovement(vec31.x, 0.2F + vec31.length() * 0.3F, vec31.z);
            }
        }
    }
}
