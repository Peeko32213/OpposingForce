package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class WhizzOwnerHurtByTargetGoal extends TargetGoal {

    private final Whizz whizz;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public WhizzOwnerHurtByTargetGoal(Whizz whizz) {
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
                this.ownerLastHurtBy = owner.getLastHurtByMob();
                int hurtTime = owner.getLastHurtByMobTimestamp();
                return hurtTime != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && this.whizz.wantsToAttack(this.ownerLastHurtBy, owner);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurtBy);
        LivingEntity owner = this.whizz.getOwner();
        if (owner != null) {
            this.timestamp = owner.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}