package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class AttackGoal extends Goal {

    protected int attackTime = 0;
    protected final Monster monster;

    public AttackGoal(Monster monster) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.monster = monster;
    }

    @Override
    public void start() {
        this.monster.setAggressive(true);
        this.attackTime = 0;
        if (this.monster instanceof IAnimatedAttacker animatedAttacker) {
            animatedAttacker.setAttackState(0);
        }
    }

    @Override
    public void stop() {
        LivingEntity target = this.monster.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.monster.setTarget(null);
        }
        this.monster.setAggressive(false);
        this.monster.getNavigation().stop();
        if (this.monster instanceof IAnimatedAttacker animatedAttacker) {
            animatedAttacker.setAttackState(0);
        }
    }

    @Override
    public boolean canUse() {
        return this.monster.getTarget() != null && this.monster.getTarget().isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.monster.getTarget();
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!this.monster.isWithinRestriction(target.blockPosition())) {
            return false;
        } else {
            return !(target instanceof Player) || !target.isSpectator() && !((Player) target).isCreative() || !this.monster.getNavigation().isDone();
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity target) {
        return this.monster.getBbWidth() * 2.0F * this.monster.getBbWidth() * 2.0F + target.getBbWidth();
    }
}
