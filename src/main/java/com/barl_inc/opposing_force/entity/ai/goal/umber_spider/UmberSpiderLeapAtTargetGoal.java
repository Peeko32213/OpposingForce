package com.barl_inc.opposing_force.entity.ai.goal.umber_spider;

import com.barl_inc.opposing_force.entity.UmberSpider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class UmberSpiderLeapAtTargetGoal extends Goal {

   private final UmberSpider umberSpider;
   private LivingEntity target;

   public UmberSpiderLeapAtTargetGoal(UmberSpider umberSpider) {
      this.umberSpider = umberSpider;
      this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

    @Override
    public boolean canUse() {
        LivingEntity target = this.umberSpider.getTarget();
        if (target == null) {
            return false;
        }
        this.target = target;
        if (!this.canLeap(target)) {
            return false;
        }
        double distanceToSqr = this.umberSpider.distanceToSqr(target);
        if (distanceToSqr < 4.0D || distanceToSqr > 16.0D) {
            return false;
        }
        if (!this.umberSpider.onGround()) {
            return false;
        }
        return this.umberSpider.getRandom().nextInt(reducedTickDelay(5)) == 0;
    }

    private boolean canLeap(LivingEntity target) {
        if (this.umberSpider.isElite() && !this.umberSpider.isVehicle()) {
            return true;
        }
        return target.level().getBrightness(LightLayer.BLOCK, target.blockPosition()) <= this.umberSpider.getLightThreshold() && !this.umberSpider.isOnFire() && !this.umberSpider.isVehicle();
    }

    @Override
    public boolean canContinueToUse() {
      return !this.umberSpider.onGround() && this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) < umberSpider.getLightThreshold() && this.umberSpider.isAttacking();
    }

    @Override
    public void start() {
      Vec3 deltaMovement = this.umberSpider.getDeltaMovement();
      Vec3 leapVec = new Vec3(this.target.getX() - this.umberSpider.getX(), 0.0D, this.target.getZ() - this.umberSpider.getZ());
      if (leapVec.lengthSqr() > 1.0E-7D) {
         leapVec = leapVec.normalize().scale(0.5D).add(deltaMovement.scale(0.2D));
      }
      this.umberSpider.setDeltaMovement(leapVec.x, 0.4D, leapVec.z);
    }
}