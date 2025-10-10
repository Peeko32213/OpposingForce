package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Volt;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class VoltLeapGoal extends Goal {

    private final Volt volt;

    public VoltLeapGoal(Volt volt) {
        this.volt = volt;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.volt.getTarget() != null && this.volt.getTarget().isAlive() && this.volt.leapCooldown <= 0 && this.volt.onGround() && this.volt.distanceToSqr(this.volt.getTarget()) < 30;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.volt.onGround();
    }

    @Override
    public void start() {
        if (this.volt.getTarget() != null) {
            float targetAngle = -1;
            float leapYaw = (float) Math.toRadians(targetAngle + 90 + this.volt.getRandom().nextFloat() * 150 - 75);
            float speed = 1.25F;
            this.volt.playSound(OPSoundEvents.VOLT_SQUISH.get(), 0.2F, 1.0F);
            Vec3 movement = this.volt.getDeltaMovement().add(speed * Math.cos(leapYaw), 0, speed * Math.sin(leapYaw));
            this.volt.setDeltaMovement(movement.x, 0.7, movement.z);
            this.volt.getNavigation().stop();
            this.volt.leapCooldown = 20 * 2 + this.volt.getRandom().nextInt(10 * 2);
        }
    }

    @Override
    public void tick() {
        if (this.volt.getTarget() != null) {
            this.volt.getLookControl().setLookAt(this.volt.getTarget(), 30.0F, 30.0F);
        }
    }
}
