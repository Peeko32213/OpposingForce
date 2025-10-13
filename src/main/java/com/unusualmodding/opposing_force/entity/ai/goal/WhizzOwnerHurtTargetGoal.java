package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class WhizzOwnerHurtTargetGoal extends TargetGoal {

    private final Whizz whizz;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public WhizzOwnerHurtTargetGoal(Whizz whizz) {
        super(whizz, false);
        this.whizz = whizz;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.whizz.isTame()) {
            LivingEntity owner = this.whizz.getOwner();
            if (owner == null) {
                return false;
            } else {
                this.ownerLastHurt = owner.getLastHurtMob();
                int hurtTime = owner.getLastHurtMobTimestamp();
                return hurtTime != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.whizz.wantsToAttack(this.ownerLastHurt, owner);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity owner = this.whizz.getOwner();
        if (owner != null) {
            this.timestamp = owner.getLastHurtMobTimestamp();
        }
        super.start();
    }
}