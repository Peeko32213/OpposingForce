package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.UmberSpider;
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
      if (this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) > umberSpider.getLightThreshold() && this.umberSpider.fleeLightFor > 0 && this.umberSpider.fleeFromPosition != null && !this.umberSpider.isAttacking()) {
          return false;
      } else if (this.umberSpider.isVehicle()) {
         return false;
      } else {
         this.target = this.umberSpider.getTarget();
         if (this.target == null) {
            return false;
         } else {
            double d0 = this.umberSpider.distanceToSqr(this.target);
            if (!(d0 < 4.0D) && !(d0 > 16.0D)) {
               if (!this.umberSpider.onGround()) {
                  return false;
               } else {
                  return this.umberSpider.getRandom().nextInt(reducedTickDelay(5)) == 0;
               }
            } else {
               return false;
            }
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      return !this.umberSpider.onGround() && this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) < umberSpider.getLightThreshold() && this.umberSpider.isAttacking();
   }

   @Override
   public void start() {
      Vec3 vec3 = this.umberSpider.getDeltaMovement();
      Vec3 vec31 = new Vec3(this.target.getX() - this.umberSpider.getX(), 0.0D, this.target.getZ() - this.umberSpider.getZ());
      if (vec31.lengthSqr() > 1.0E-7D) {
         vec31 = vec31.normalize().scale(0.5D).add(vec3.scale(0.2D));
      }
      this.umberSpider.setDeltaMovement(vec31.x, 0.4D, vec31.z);
   }
}