package com.unusualmodding.opposingforce.common.entity.projectile;

import com.google.common.base.MoreObjects;
import com.unusualmodding.opposingforce.core.registry.OPEffects;
import com.unusualmodding.opposingforce.core.registry.OPEntities;
import com.unusualmodding.opposingforce.core.registry.OPItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SmallElectricBall extends AbstractElectricBall implements GeoAnimatable {
    private static final RawAnimation VOLT_BALL = RawAnimation.begin().thenPlay("animation.volt_ball.orb");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int despawnTimer = 90;

    public SmallElectricBall(EntityType<? extends SmallElectricBall> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SmallElectricBall(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(OPEntities.SMALL_ELECTRICITY_BALL.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public SmallElectricBall(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(OPEntities.SMALL_ELECTRICITY_BALL.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public boolean isOnFire() {
        return false;
    }

    protected Item getDefaultItem() {
        return OPItems.ELECTRIC_CHARGE.get();
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            entity.hurt(this.damageSources().magic(), 2.0F);
            if (entity instanceof LivingEntity target) {
                target.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 200), MoreObjects.firstNonNull(entity1, this));
            }
        }
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected <E extends SmallElectricBall> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        event.setAndContinue(VOLT_BALL);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    @Override
    public void tick() {
        super.tick();
        despawnTimer--;
        if (despawnTimer <= 0) discard();
    }
}
