package com.peeko32213.hole.common.entity.projectile;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.peeko32213.hole.core.registry.HoleEffects;
import com.peeko32213.hole.core.registry.HoleEntities;
import com.peeko32213.hole.core.registry.HoleItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
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

public class EntitySmallElectricBall extends EntityAbstractElectricBall implements GeoAnimatable {
    private static final RawAnimation VOLT_BALL = RawAnimation.begin().thenPlay("animation.volt_ball.orb");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public EntitySmallElectricBall(EntityType<? extends EntitySmallElectricBall> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EntitySmallElectricBall(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HoleEntities.SMALL_ELECTRICITY_BALL.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public EntitySmallElectricBall(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HoleEntities.SMALL_ELECTRICITY_BALL.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }


    public boolean isOnFire() {
        return false;
    }

    protected Item getDefaultItem() {
        return HoleItems.ELECTRIC_CHARGE.get();
    }
    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            entity.hurt(this.damageSources().magic(), 2.0F);
            if (entity instanceof LivingEntity target) {
                target.addEffect(new MobEffectInstance(HoleEffects.ELECTRIFIED.get(), 200), MoreObjects.firstNonNull(entity1, this));
            }
        }
    }



    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.discard();
        }

    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected <E extends EntitySmallElectricBall> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
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
}
