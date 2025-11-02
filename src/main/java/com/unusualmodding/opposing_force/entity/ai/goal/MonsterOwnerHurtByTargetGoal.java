package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.base.TameableMonster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class MonsterOwnerHurtByTargetGoal extends TargetGoal {

    private final TameableMonster tamedMonster;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public MonsterOwnerHurtByTargetGoal(TameableMonster tameableMonster) {
        super(tameableMonster, false);
        this.tamedMonster = tameableMonster;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.tamedMonster.isTame() && !this.tamedMonster.isOrderedToSit()) {
            LivingEntity livingentity = this.tamedMonster.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurtBy = livingentity.getLastHurtByMob();
                int i = livingentity.getLastHurtByMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && this.tamedMonster.wantsToAttack(this.ownerLastHurtBy, livingentity);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurtBy);
        LivingEntity livingentity = this.tamedMonster.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}