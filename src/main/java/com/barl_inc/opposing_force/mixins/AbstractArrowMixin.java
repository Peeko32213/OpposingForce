package com.barl_inc.opposing_force.mixins;

import com.barl_inc.opposing_force.entity.projectile.StratoArrow;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {

    protected AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private void hostileTakeover$changeArrowCritParticles(Level level, ParticleOptions particle, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        if ((Object) this instanceof StratoArrow) {
        }
        else {
            level.addParticle(particle, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
