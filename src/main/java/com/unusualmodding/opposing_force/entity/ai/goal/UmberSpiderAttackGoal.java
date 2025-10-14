package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.UmberSpider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;

import java.util.EnumSet;
import java.util.Objects;

public class UmberSpiderAttackGoal extends AttackGoal {

    private final UmberSpider umberSpider;

    public UmberSpiderAttackGoal(UmberSpider mob) {
        super(mob);
        this.umberSpider = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.umberSpider.fleeLightFor <= 0 && this.umberSpider.getTarget().level().getBrightness(LightLayer.BLOCK, this.umberSpider.getTarget().blockPosition()) <= this.umberSpider.getLightThreshold() && !this.umberSpider.isOnFire();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.umberSpider.fleeLightFor <= 0 && this.umberSpider.getTarget().level().getBrightness(LightLayer.BLOCK, this.umberSpider.getTarget().blockPosition()) <= this.umberSpider.getLightThreshold() && !this.umberSpider.isOnFire();
    }

    @Override
    public void start() {
        super.start();
        this.umberSpider.setAttacking(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.umberSpider.setAttacking(false);
    }

    @Override
    public void tick() {
        LivingEntity target = this.umberSpider.getTarget();
        if (target != null) {
            this.umberSpider.setAttacking(true);

            this.umberSpider.lookAt(Objects.requireNonNull(target), 30F, 30F);
            this.umberSpider.getLookControl().setLookAt(target, 30F, 30F);

            double distance = this.umberSpider.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.umberSpider.getAttackState();

            this.umberSpider.getNavigation().moveTo(target, 1.2F);

            if (attackState == 1) {
                this.timer++;
                if (this.timer == 4) {
                    if (this.umberSpider.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.umberSpider.doHurtTarget(target);
                        this.umberSpider.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer >= 20) {
                    this.timer = 0;
                    this.umberSpider.setAttackState(0);
                }
            } else {
                if (distance <= this.getAttackReachSqr(target)) {
                    this.umberSpider.setAttackState(1);
                }
            }
        }
    }
}