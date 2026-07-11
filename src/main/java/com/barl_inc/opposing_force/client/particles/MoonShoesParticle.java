package com.barl_inc.opposing_force.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MoonShoesParticle extends TextureSheetParticle {

   private final SpriteSet sprites;

   protected MoonShoesParticle(ClientLevel pLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
      super(pLevel, x, y, z, 0.0D, 0.0D, 0.0D);
      this.friction = 0.96F;
      this.sprites = sprites;
      this.xd *= 0.1F;
      this.yd *= 0.1F;
      this.zd *= 0.1F;
      this.xd += xSpeed;
      this.yd += ySpeed;
      this.zd += zSpeed;
      float f1 = 1.0F - (float)(Math.random() * (double)0.3F);
      this.rCol = f1;
      this.gCol = f1;
      this.bCol = f1;
      this.quadSize *= 1.875F;
      int i = (int) (8.0D / (Math.random() * 0.8D + 0.3D));
      this.lifetime = (int) Math.max((float) i * 2.5F, 1.0F);
      this.hasPhysics = false;
      this.setSpriteFromAge(sprites);
   }

   @Override
   public @NotNull ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public float getQuadSize(float pScaleFactor) {
      return this.quadSize * Mth.clamp(((float)this.age + pScaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   @Override
   public void tick() {
       super.tick();
       this.setSpriteFromAge(this.sprites);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Factory(SpriteSet sprites) {
         this.sprites = sprites;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double pXSpeed, double pYSpeed, double pZSpeed) {
         return new MoonShoesParticle(level, x, y, z, pXSpeed, pYSpeed, pZSpeed, this.sprites);
      }
   }
}