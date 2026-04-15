package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class StratoArrow extends BaseArrow {

    private boolean hasTouchedGround = false;

    public StratoArrow(EntityType<? extends BaseArrow> type, Level level) {
        super(type, level);
    }

    public StratoArrow(Level level, Entity shooter) {
        super(OPEntities.STRATO_ARROW.get(), level, shooter);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        Vec3 knockback = this.getDeltaMovement().multiply(0.6D, 0.0D, 0.6D).normalize();
        if (entity instanceof LivingEntity livingEntity) {
            double knockbackResist = Math.max(0.0D, 1.0D - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            knockback = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(1 * knockbackResist);
        }
        if (knockback.lengthSqr() > 0.0D && this.isCritArrow()) {
            entity.push(knockback.x, 0.25D, knockback.z);
        }
    }

    @Override
    public void tick() {
        super.tick();

        double x1 = this.getDeltaMovement().x;
        double y1 = this.getDeltaMovement().y;
        double z1 = this.getDeltaMovement().z;

        double x2 = this.getX() + x1;
        double y2 = this.getY() + y1;
        double z2 = this.getZ() + z1;

        if (this.isInWaterOrBubble() && !this.inGround) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.05F, 0));
        }

        if (this.level().isClientSide && !this.inGround) {
            if (this.isCritArrow() && !this.isInWaterOrBubble() && this.tickCount > 1) {
                for (int i = 0; i < 1; i++) {
                    this.level().addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), -x1 * 0.1D, -y1 * 0.1D, -z1 * 0.1D);
                }
            }
            if (this.isInWaterOrBubble() && this.tickCount > 1) {
                for (int j = 0; j < 4; j++) {
                    this.level().addParticle(ParticleTypes.BUBBLE, x2 - x1 * 0.25D, y2 - y1 * 0.25D, z2 - z1 * 0.25D, x1, y1, z1);
                }
            }
        }

        if (this.tickCount > 150 && !this.inGround) {
            this.discard();
        }

        if (this.inGround && this.isNoGravity()) {
            this.setNoGravity(false);
            this.hasTouchedGround = true;
        }
        if (!this.inGround && !this.isNoGravity() && !this.hasTouchedGround) {
            this.setNoGravity(true);
        }
    }

    @Override
    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float inaccuracy) {
        float x = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float y = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float z = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(x, y, z, velocity, inaccuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0, vec3.z));
    }
}
