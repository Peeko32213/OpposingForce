package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Volt;
import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class VoltShootGoal extends AttackGoal {

    private final Volt volt;
    private int cooldown = 0;

    public VoltShootGoal(Volt volt) {
        super(volt);
        this.volt = volt;
    }

    @Override
    public void tick() {
        LivingEntity target = this.volt.getTarget();
        if (target != null) {
            this.volt.lookAt(this.volt.getTarget(), 30F, 30F);
            this.volt.getLookControl().setLookAt(this.volt.getTarget(), 30F, 30F);
            boolean random = this.volt.getRandom().nextBoolean();

            if (volt.getAttackState() == 1) {
                this.timer++;
                this.cooldown = 24;
                this.volt.getNavigation().stop();
                if (this.timer == 7) {
                    this.volt.playSound(OPSoundEvents.VOLT_SHOOT.get(), 2.0F, 1.0F / (this.volt.getRandom().nextFloat() * 0.4F + 0.8F));
                }
                if (this.timer == 8) {
                    ElectricCharge electricCharge = new ElectricCharge(this.volt, this.volt.level(), this.volt.position().x(), this.volt.getEyePosition().y(), this.volt.position().z());
                    double tx = target.getX() - this.volt.getX();
                    double ty = target.getY() + target.getEyeHeight() - 1.1D - electricCharge.getY();
                    double tz = target.getZ() - this.volt.getZ();
                    float heightOffset = Mth.sqrt((float) (tx * tx + tz * tz)) * 0.01F;
                    electricCharge.setChargeScale(0.5F);
                    electricCharge.setChargeDamage(3.0F);
                    electricCharge.shoot(tx, ty + heightOffset, tz, 0.3F, 2.0F);
                    if (this.volt.isPowered()) {
                        electricCharge.setChargeScale(1.5F);
                        electricCharge.setChargeDamage(6.0F);
                    }
                    this.volt.level().addFreshEntity(electricCharge);
                }
                if (this.timer >= 20) {
                    this.timer = 0;
                    this.volt.setAttackState(0);
                }
            } else {
                if (this.volt.onGround()) {
                    this.cooldown--;
                }
                this.volt.getMoveControl().strafe(random ? 0.3F : -0.3F, random ? 0.3F : -0.3F);
                if ((this.volt.onGround() || this.volt.isInWaterOrBubble()) && this.cooldown <= 0) {
                    this.volt.setAttackState(1);
                }
            }
        }
    }
}