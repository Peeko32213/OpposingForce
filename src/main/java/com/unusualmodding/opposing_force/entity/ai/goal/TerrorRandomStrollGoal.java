package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Terror;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class TerrorRandomStrollGoal extends RandomStrollGoal {

     private final Terror entity;

     public TerrorRandomStrollGoal(Terror entity, double speedModifier) {
         super(entity, speedModifier);
         this.entity = entity;
     }

     @Override
     public boolean canUse() {
         return super.canUse() && this.entity.isLandNavigator && !entity.isInWater();
     }

     @Override
     public boolean canContinueToUse() {
         return super.canContinueToUse() && this.entity.isLandNavigator && !entity.isInWater();
     }
}