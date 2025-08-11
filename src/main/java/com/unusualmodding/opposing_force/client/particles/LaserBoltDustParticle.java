package com.unusualmodding.opposing_force.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class LaserBoltDustParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected LaserBoltDustParticle(ClientLevel level, double x, double y, double z, double xMotion, double yMotion, double zMotion, SpriteSet sprites) {
        super(level, x, y, z, xMotion, yMotion, zMotion);
        this.friction = 0.96F;
        this.gravity = -0.1F - (0.1F * level.random.nextFloat());
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = sprites;
        this.xd *= 0.25F;
        this.yd *= 0.25F;
        this.zd *= 0.25F;
        this.rCol = 1.0F;
        this.gCol = level.random.nextFloat() * 0.25F;
        this.bCol = level.random.nextFloat() * 0.25F;
        this.quadSize *= 0.75F + random.nextFloat() * 0.5F;
        this.lifetime = (int) ((double) 8 / ((double) level.random.nextFloat() * 0.8D + 0.2D));
        this.lifetime = Math.max(this.lifetime, 1);
        this.setSpriteFromAge(sprites);
        this.hasPhysics = false;
    }

    public float getQuadSize(float f) {
        return this.quadSize * Mth.clamp(((float) this.age + f) / (float) this.lifetime * 48.0F, 0.0F, 1.0F);
    }

    public void tick() {
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public int getLightColor(float partialTicks) {
        return 240;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LaserBoltDustParticle particle = new LaserBoltDustParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return particle;
        }
    }
}
