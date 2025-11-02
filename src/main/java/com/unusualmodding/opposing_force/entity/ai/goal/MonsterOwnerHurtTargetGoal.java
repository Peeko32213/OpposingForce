package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.base.TameableMonster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class MonsterOwnerHurtTargetGoal extends TargetGoal {

    private final TameableMonster tamedMonster;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public MonsterOwnerHurtTargetGoal(TameableMonster tameableMonster) {
        super(tameableMonster, false);
        this.tamedMonster = tameableMonster;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.tamedMonster.isTame() && !this.tamedMonster.isOrderedToSit()) {
            LivingEntity livingentity = this.tamedMonster.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurt = livingentity.getLastHurtMob();
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.tamedMonster.wantsToAttack(this.ownerLastHurt, livingentity);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = this.tamedMonster.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}