package com.unusualmodding.opposing_force.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LaserBoltDustParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected LaserBoltDustParticle(ClientLevel level, double x, double y, double z, double xMotion, double yMotion, double zMotion, SpriteSet sprites) {
        super(level, x, y, z, xMotion, yMotion, zMotion);
        this.sprites = sprites;
        this.friction = 0.9F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.xd *= 0.25F;
        this.yd *= 0.25F;
        this.zd *= 0.25F;
        this.rCol = 1.0F;
        this.gCol = 0.141F;
        this.bCol = 0.427F;
        this.quadSize *= 0.75F + random.nextFloat() * 0.5F;
        this.lifetime = (int) ((double) 8 / ((double) level.random.nextFloat() * 0.8D + 0.2D));
        this.lifetime = Math.max(this.lifetime, 1);
        this.setSpriteFromAge(sprites);
        this.hasPhysics = false;
    }

    @Override
    public float getQuadSize(float f) {
        return this.quadSize * Mth.clamp(((float) this.age + f) / (float) this.lifetime * 48.0F, 0.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 240;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new LaserBoltDustParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}
