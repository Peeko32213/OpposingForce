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

    protected LaserSweepParticle(ClientLevel level, double x, double y, double z, double size, SpriteSet sprites) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.sprites = sprites;
        this.lifetime = 5;
        this.quadSize = 1.0F - (float) size * 0.5F;
        this.setSpriteFromAge(sprites);
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

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xspeed, double yspeed, double zspeed) {
            return new LaserSweepParticle(level, x, y, z, xspeed, this.sprites);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FireFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FireFactory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xspeed, double yspeed, double zspeed) {
            return new LaserSweepParticle(level, x, y, z, xspeed, this.sprites);
        }
    }
}