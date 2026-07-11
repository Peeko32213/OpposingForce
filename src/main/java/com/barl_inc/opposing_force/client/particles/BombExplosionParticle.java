package com.barl_inc.opposing_force.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

public class BombExplosionParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected BombExplosionParticle(ClientLevel level, double x, double y, double z, double quadSizeMultiplier, int color, SpriteSet sprites) {
        super(level, x, y, z, 0, 0, 0);
        this.setSize(0.5F, 0.5F);
        this.lifetime = 4 + this.random.nextInt(6);
        this.quadSize = 2.0F * (1.0F - (float) quadSizeMultiplier * 0.5F);
        this.sprites = sprites;
        float randCol = level.random.nextFloat() * 0.05F;
        this.setColor(Math.min(FastColor.ARGB32.red(color) / 255F + randCol, 1), Math.min(1F, FastColor.ARGB32.green(color) / 255F + randCol), Math.min(1F, FastColor.ARGB32.blue(color) / 255F + randCol));
        this.setSpriteFromAge(sprites);
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

    @Override
    public int getLightColor(float partialTicks) {
        return 15728880;
    }

    public static class FireBombFactory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public FireBombFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BombExplosionParticle(level, x, y, z, xSpeed, 0xfb921b, spriteSet);
        }
    }

    public static class KineticBombFactory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public KineticBombFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BombExplosionParticle(level, x, y, z, xSpeed, 0xdda4fc, spriteSet);
        }
    }

    public static class LightningBombFactory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public LightningBombFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BombExplosionParticle(level, x, y, z, xSpeed, 0x6bc4e2, spriteSet);
        }
    }

    public static class WhizzBombFactory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public WhizzBombFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BombExplosionParticle(level, x, y, z, xSpeed, 0xbd9af8, spriteSet);
        }
    }
}