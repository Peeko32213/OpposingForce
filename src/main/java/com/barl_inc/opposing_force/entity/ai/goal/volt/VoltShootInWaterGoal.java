package com.barl_inc.opposing_force.entity.ai.goal.volt;

import com.barl_inc.opposing_force.entity.Volt;
import com.barl_inc.opposing_force.entity.ai.goal.AttackGoal;
import com.barl_inc.opposing_force.entity.projectile.ElectricCharge;
import com.barl_inc.opposing_force.entity.utils.OPPoses;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class VoltShootInWaterGoal extends AttackGoal {

    private final Volt volt;
    private int cooldown = 0;

    public VoltShootInWaterGoal(Volt volt) {
        super(volt);
        this.volt = volt;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && volt.isInWater();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && volt.isInWater();
    }

    @Override
    public void tick() {
        LivingEntity target = this.volt.getTarget();
        if (target != null) {
            this.volt.lookAt(this.volt.getTarget(), 30F, 30F);
            this.volt.getLookControl().setLookAt(this.volt.getTarget().getX(), this.volt.getTarget().getY(), this.volt.getTarget().getZ());
            double distance = this.volt.distanceToSqr(target.getX(), target.getY(), target.getZ());

            if (volt.getAttackState() == 1) {
                this.timer++;
                this.cooldown = 30;
                this.volt.getNavigation().stop();
                if (this.timer == 1) this.volt.setPose(OPPoses.SHOOTING.get());
                if (this.timer == 10) this.volt.playSound(OPSoundEvents.VOLT_SHOOT.get(), 3.0F, 1.0F / (this.volt.getRandom().nextFloat() * 0.4F + 0.8F));
                if (this.timer == 12) {
                    ElectricCharge electricCharge = new ElectricCharge(this.volt, this.volt.level(), this.volt.position().x(), this.volt.getEyePosition().y(), this.volt.position().z());
                    double tx = target.getX() - this.volt.getX();
                    double ty = target.getY() + target.getEyeHeight() - 1.1D - electricCharge.getY();
                    double tz = target.getZ() - this.volt.getZ();
                    float heightOffset = Mth.sqrt((float) (tx * tx + tz * tz)) * 0.01F;
                    float speed = volt.isElite() ? 0.5F : 0.25F;
                    if (this.volt.isPowered()) {
                        speed += 0.25F;
                    }
                    if (this.volt.isElite()) {
                        electricCharge.setRainbow(true);
                        speed += 0.33F;
                    }
                    electricCharge.shoot(tx, ty + heightOffset, tz, speed, 2.0F);
                    this.volt.level().addFreshEntity(electricCharge);
                }
                if (this.timer > 20) {
                    this.timer = 0;
                    this.volt.setAttackState(0);
                }
            } else {
                this.volt.getNavigation().moveTo(target, 1.1F);
                if (this.cooldown > 0) {
                    this.cooldown--;
                }
                else if (this.cooldown == 0 && distance <= this.getAttackReachSqr(target)) {
                    this.volt.setAttackState(1);
                }
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.monster.getBbWidth() * 3.0F * this.monster.getBbWidth() * 3.0F + target.getBbWidth();
    }
}