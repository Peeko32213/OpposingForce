package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class HangingSpiderLeapAtTargetGoal extends Goal {

   private final HangingSpider hangingSpider;
   private LivingEntity target;

   public HangingSpiderLeapAtTargetGoal(HangingSpider hangingSpider) {
      this.hangingSpider = hangingSpider;
      this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (this.hangingSpider.isGoingDown() || this.hangingSpider.isGoingUp() || this.hangingSpider.isUpsideDown() || this.hangingSpider.getAttackState() != 0) {
          return false;
      } else if (this.hangingSpider.isVehicle()) {
         return false;
      } else {
         this.target = this.hangingSpider.getTarget();
         if (this.target == null) {
            return false;
         } else {
            double distance = this.hangingSpider.distanceToSqr(this.target);
            if (!(distance < 4.0D) && !(distance > 16.0D)) {
               if (!this.hangingSpider.onGround()) {
                  return false;
               } else {
                  return this.hangingSpider.getRandom().nextInt(reducedTickDelay(5)) == 0;
               }
            } else {
               return false;
            }
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      return !this.hangingSpider.onGround() && !(this.hangingSpider.isGoingDown() || this.hangingSpider.isGoingUp() || this.hangingSpider.isUpsideDown());
   }

   @Override
   public void start() {
      Vec3 vec3 = this.hangingSpider.getDeltaMovement();
      Vec3 vec31 = new Vec3(this.target.getX() - this.hangingSpider.getX(), 0.0D, this.target.getZ() - this.hangingSpider.getZ());
      if (vec31.lengthSqr() > 1.0E-7D) {
         vec31 = vec31.normalize().scale(0.5D).add(vec3.scale(0.2D));
      }
      this.hangingSpider.setDeltaMovement(vec31.x, 0.4D, vec31.z);
   }
}