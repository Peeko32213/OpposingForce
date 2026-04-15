package com.unusualmodding.opposing_force.entity.ai.control;

import com.unusualmodding.opposing_force.entity.base.OPMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OPMoveControl extends MoveControl {

    protected final OPMonster monster;

    public OPMoveControl(OPMonster monster) {
        super(monster);
        this.monster = monster;
    }

    @Override
    public void tick() {
        if (!monster.refuseToMove()) {
            if (this.operation == Operation.STRAFE) {
                this.doStrafing();
            } else if (this.operation == Operation.MOVE_TO) {
                this.doMoveTo();
            } else if (operation == Operation.JUMPING) {
                this.mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (mob.onGround()) {
                    this.operation = Operation.WAIT;
                }
            } else {
                this.mob.setZza(0.0F);
            }
        }
    }

    protected void doStrafing() {
        float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
        float forwards = strafeForwards;
        float right = strafeRight;
        float f4 = Mth.sqrt(forwards * forwards + right * right);
        if (f4 < 1.0F) {
            f4 = 1.0F;
        }

        f4 = speed / f4;
        forwards *= f4;
        right *= f4;
        float f5 = Mth.sin(mob.getYRot() * ((float) Math.PI / 180F));
        float f6 = Mth.cos(mob.getYRot() * ((float) Math.PI / 180F));
        float f7 = forwards * f6 - right * f5;
        float f8 = right * f6 + forwards * f5;
        if (!this.isWalkable(f7, f8)) {
            this.strafeForwards = 1.0F;
            this.strafeRight = 0.0F;
        }
        this.mob.setSpeed(speed);
        this.mob.setZza(strafeForwards);
        this.mob.setXxa(strafeRight);
        this.operation = Operation.WAIT;
    }

    protected void doMoveTo() {
        this.operation = Operation.WAIT;
        double x = wantedX - mob.getX();
        double y = wantedY - mob.getY();
        double z = wantedZ - mob.getZ();
        double direction = x * x + y * y + z * z;
        if (direction < (double) 2.5000003E-7F) {
            this.mob.setZza(0.0F);
            return;
        }
        float newYRot = (float) Mth.wrapDegrees(Mth.atan2(z, x) * Mth.RAD_TO_DEG - 90);
        this.mob.setYRot(this.rotlerp(mob.getYRot(), newYRot, 90.0F));
        this.mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
        if (mob.isInWater()) {
            this.mob.setSpeed(mob.getSpeed() * 2);
        }
        BlockPos blockpos = mob.blockPosition();
        BlockState blockstate = mob.level().getBlockState(blockpos);
        VoxelShape voxelshape = blockstate.getCollisionShape(mob.level(), blockpos);
        if (y > (double) mob.getStepHeight() && x * x + z * z < (double) Math.max(1.0F, mob.getBbWidth()) || !voxelshape.isEmpty() && mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
            this.mob.getJumpControl().jump();
            this.operation = Operation.JUMPING;
        }
    }
}
