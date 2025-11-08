package com.unusualmodding.opposing_force.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LaserSweepParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected LaserSweepParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.sprites = sprites;
        this.lifetime = 5;
        this.quadSize = 1.0F;
        this.setSpriteFromAge(sprites);
        this.setColor((float) xSpeed, (float) ySpeed, (float) zSpeed);
    }

    @Override
    public int getLightColor(float color) {
        return 240;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LaserSweepParticle sweepParticle = new LaserSweepParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
            sweepParticle.setColor(1, 1, 1);
            return sweepParticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class DyedFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public DyedFactory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new LaserSweepParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }
    }
}