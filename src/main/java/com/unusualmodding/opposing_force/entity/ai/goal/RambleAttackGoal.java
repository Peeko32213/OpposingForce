package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Rambler;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class RambleAttackGoal extends AttackGoal {

    private final Rambler ramble;

    public RambleAttackGoal(Rambler ramble) {
        super(ramble);
        this.ramble = ramble;
    }

    @Override
    public void start() {
        super.start();
        this.ramble.setFlailing(false);
    }

    @Override
    public void stop() {
        super.stop();
        this.ramble.setFlailing(false);
    }

    @Override
    public void tick() {
        LivingEntity target = this.ramble.getTarget();
        if (target != null) {
            this.ramble.lookAt(target, 30F, 30F);
            this.ramble.getLookControl().setLookAt(target, 30F, 30F);
            double distance = this.ramble.distanceToSqr(target.getX(), target.getY(), target.getZ());

            if (this.ramble.isFlailing()) {
                this.timer++;
                this.ramble.getNavigation().moveTo(target, 2.0D);
                Vec3 pos = this.ramble.position();
                if (this.timer > 3) {
                    this.ramble.hurtEntitiesAround(pos, 2.25F, true);
                    if ((this.ramble.tickCount / 2) % 2 == 0) {
                        this.ramble.playSound(OPSoundEvents.RAMBLE_ATTACK.get(), 1.0F, 1.0F / (this.ramble.getRandom().nextFloat() * 0.4F + 0.8F));
                    }
                }
                if (this.timer > 200) {
                    this.timer = 0;
                    this.ramble.flailCooldown();
                    this.ramble.setFlailing(false);
                }
            } else {
                if (this.ramble.getFlailCooldown() <= 0) {
                    this.ramble.getNavigation().moveTo(target, 1.5D);
                    if (distance <= this.getAttackReachSqr(target)) {
                        this.ramble.setFlailing(true);
                    }
                } else {
                    this.ramble.getNavigation().stop();
                }
            }
        }
    }
}