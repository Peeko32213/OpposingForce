package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Slug;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class SlugOwnerHurtTargetGoal extends TargetGoal {

    private final Slug slug;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public SlugOwnerHurtTargetGoal(Slug slug) {
        super(slug, false);
        this.slug = slug;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.slug.isTame()) {
            LivingEntity owner = this.slug.getOwner();
            if (owner == null) {
                return false;
            } else {
                this.ownerLastHurt = owner.getLastHurtMob();
                int hurtTime = owner.getLastHurtMobTimestamp();
                return hurtTime != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.slug.wantsToAttack(this.ownerLastHurt, owner);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity owner = this.slug.getOwner();
        if (owner != null) {
            this.timestamp = owner.getLastHurtMobTimestamp();
        }
        super.start();
    }
}