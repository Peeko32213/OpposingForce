package com.unusualmodding.opposing_force.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BombFlashParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected BombFlashParticle(ClientLevel level, double x, double y, double z, int color, SpriteSet sprites) {
        super(level, x, y, z, 0, 0, 0);
        this.lifetime = 4;
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
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 240;
    }

    @Override
    public void render(@NotNull VertexConsumer consumer, @NotNull Camera camera, float partialTicks) {
        this.setAlpha(0.6F - ((float) this.age + partialTicks - 1.0F) * 0.25F * 0.5F);
        super.render(consumer, camera, partialTicks);
    }

    @Override
    public float getQuadSize(float pScaleFactor) {
        return 7.1F * Mth.sin(((float)this.age + pScaleFactor - 1.0F) * 0.25F * (float)Math.PI);
    }

    public static class FireBombFactory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public FireBombFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BombFlashParticle(level, x, y, z, 0xfb921b, spriteSet);
        }
    }

    public static class KineticBombFactory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public KineticBombFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BombFlashParticle(level, x, y, z, 0xdda4fc, spriteSet);
        }
    }

    public static class LightningBombFactory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public LightningBombFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BombFlashParticle(level, x, y, z, 0x6bc4e2, spriteSet);
        }
    }

    public static class WhizzBombFactory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public WhizzBombFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BombFlashParticle(level, x, y, z, 0xbd9af8, spriteSet);
        }
    }
}