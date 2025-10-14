package com.unusualmodding.opposing_force.entity.ai.navigation;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class HangingSpiderMoveControl extends MoveControl {

    private final HangingSpider spider;

    public HangingSpiderMoveControl(HangingSpider spider) {
        super(spider);
        this.spider = spider;
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            Vec3 vector3d = new Vec3(this.wantedX - this.spider.getX(), this.wantedY - this.spider.getY(), this.wantedZ - this.spider.getZ());
            double length = vector3d.length();
            if (length < this.spider.getBoundingBox().getSize()) {
                this.operation = MoveControl.Operation.WAIT;
                this.spider.setDeltaMovement(this.spider.getDeltaMovement().scale(0.5D));
            } else {
                this.spider.setDeltaMovement(this.spider.getDeltaMovement().add(vector3d.scale(this.speedModifier * 1.0D * 0.05D / length)));
                if (this.spider.getTarget() == null) {
                    Vec3 vector3d1 = this.spider.getDeltaMovement();
                    this.spider.setYRot(-((float) Mth.atan2(vector3d1.x, vector3d1.z)) * Mth.RAD_TO_DEG);
                } else {
                    double d2 = this.spider.getTarget().getX() - this.spider.getX();
                    double d1 = this.spider.getTarget().getZ() - this.spider.getZ();
                    this.spider.setYRot(-((float) Mth.atan2(d2, d1)) * Mth.RAD_TO_DEG);
                }
                this.spider.yBodyRot = this.spider.getYRot();
            }
        } else if (this.operation == Operation.STRAFE) {
            this.operation = Operation.WAIT;
        }
    }
}