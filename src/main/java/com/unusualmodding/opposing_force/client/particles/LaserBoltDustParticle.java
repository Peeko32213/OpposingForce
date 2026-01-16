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

    protected LaserBoltDustParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.sprites = sprites;
        this.quadSize *= 0.75F + random.nextFloat() * 0.5F;
        this.xd = (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.yd = (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.zd = (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.lifetime = (int) ((double) 8 / ((double) level.random.nextFloat() * 0.8D + 0.2D));
        this.lifetime = Math.max(this.lifetime, 1);
        this.setSpriteFromAge(sprites);
        this.hasPhysics = false;
        this.setColor((float) xSpeed, (float) ySpeed, (float) zSpeed);
    }

    @Override
    public float getQuadSize(float f) {
        return this.quadSize * Mth.clamp(((float) this.age + f) / (float) this.lifetime * 48.0F, 0.0F, 1.0F);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 240;
    }

    @OnlyIn(Dist.CLIENT)
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
