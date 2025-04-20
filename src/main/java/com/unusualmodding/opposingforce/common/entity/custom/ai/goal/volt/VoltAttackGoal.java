package com.unusualmodding.opposingforce.common.entity.custom.ai.goal.volt;

import com.unusualmodding.opposingforce.common.entity.custom.monster.VoltEntity;
import com.unusualmodding.opposingforce.common.entity.custom.projectile.ElectricBall;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.EnumSet;

public class VoltAttackGoal extends Goal {

    protected final VoltEntity volt;
    private int attackTime = 0;
    private int retreatCooldown = 0;
    private BlockPos jumpTarget = null;
    private boolean jumpEnqueued = false;

    public VoltAttackGoal(VoltEntity mob) {
        this.volt = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return volt.getTarget() != null && volt.getTarget().isAlive();
    }

    public void start() {
        volt.setRunning(true);
        volt.setAttackState(0);
        this.jumpEnqueued = false;
        this.attackTime = 0;
    }

    public void stop() {
        volt.setRunning(false);
        volt.setAttackState(0);
        this.jumpEnqueued = false;
    }

    public void tick() {
        LivingEntity target = volt.getTarget();
        if (target != null) {

            volt.lookAt(volt.getTarget(), 30F, 30F);
            volt.getLookControl().setLookAt(volt.getTarget(), 30F, 30F);

            double distance = volt.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = volt.getAttackState();

            if (attackState == 21) {
                tickShootAttack();
            }
            else if (jumpEnqueued) {
                if (jumpTarget == null) {
                    jumpTarget = findJumpTarget(target, distance > 20.0F);
                } else {
                    if (volt.isLeaping()) {
                        Vec3 vec3 = this.volt.getDeltaMovement();
                        Vec3 vec31 = new Vec3(this.jumpTarget.getX() + 0.5F - this.volt.getX(), 0.0D, this.jumpTarget.getZ() + 0.5F - this.volt.getZ());
                        if (vec31.lengthSqr() > 1.0E-7D) {
                            vec31 = vec31.scale(0.155F).add(vec3.scale(0.2D));
                        }
                        this.volt.setDeltaMovement(vec31.x, 0.2F + vec31.length() * 0.3F, vec31.z);
                        jumpEnqueued = false;
                        jumpTarget = null;
                    }
                    else if (volt.onGround()) {
                        volt.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(jumpTarget));
                    }
                }
            }
            else {
                this.retreatCooldown = Math.max(this.retreatCooldown - 1, 0);
                this.volt.getNavigation().moveTo(target, 1.0D);
                this.checkForCloseRangeAttack(distance);
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        if (distance > 10 && volt.onGround()) {
            volt.setAttackState(21);
        }
        if (distance <= 10 && this.retreatCooldown == 0) {
            this.startCleanJump();
        }
    }

    protected void tickShootAttack () {
        attackTime++;
        LivingEntity target = volt.getTarget();
        volt.setDeltaMovement(0, volt.getDeltaMovement().y, 0);

        if (attackTime == 10) {
            ElectricBall projectile = new ElectricBall(volt, volt.level());
            double tx = target.getX() - volt.getX();
            double ty = target.getY() + target.getEyeHeight() - 1.1D - projectile.getY();
            double tz = target.getZ() - volt.getZ();
            float heightOffset = Mth.sqrt((float) (tx * tx + tz * tz)) * 0.01F;
            projectile.shoot(tx, ty + heightOffset, tz, 0.6F, 1.0F);
            this.volt.level().addFreshEntity(projectile);
        }
        if (attackTime >= 24) {
            attackTime =0;
            this.volt.setAttackState(0);
        }
    }

    private void startCleanJump() {
        jumpTarget = null;
        jumpEnqueued = true;
    }

    private BlockPos findJumpTarget(LivingEntity target, boolean far) {
        int lengthOfRadius = far ? volt.getRandom().nextInt(2) + 4 : volt.getRandom().nextInt(10) + 15;
        Vec3 offset = target.position().add(new Vec3(0, 0, lengthOfRadius).yRot((float) (Math.PI * 2F * volt.getRandom().nextFloat())));
        Vec3 vec3 = null;
        if (far) {
            BlockPos farPos = LandRandomPos.movePosUpOutOfSolid(volt, BlockPos.containing(offset));
            if (farPos != null) {
                vec3 = Vec3.atCenterOf(farPos);
            }
        } else {
            vec3 = LandRandomPos.getPosTowards(this.volt, 20, 10, offset);
        }
        if (vec3 != null) {
            BlockPos blockpos = BlockPos.containing(vec3);
            AABB aabb = this.volt.getBoundingBox().move(vec3.add(0.5F, 1, 0.5F).subtract(this.volt.position()));
            if (volt.level().getBlockState(blockpos.below()).isSolidRender(volt.level(), blockpos.below()) && volt.getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic(volt.level(), blockpos.mutable())) == 0.0F && volt.level().isUnobstructed(this.volt, Shapes.create(aabb))) {
                return blockpos;
            }
        }
        return null;
    }
}