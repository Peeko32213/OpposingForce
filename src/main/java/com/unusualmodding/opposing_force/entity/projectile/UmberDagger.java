package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEffects;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class UmberDagger extends ThrowableProjectile {

    public UmberDagger(EntityType<? extends UmberDagger> type, Level level) {
        super(type, level);
    }

    public UmberDagger(Level level, LivingEntity shooter) {
        super(OPEntities.UMBER_DAGGER.get(), level, shooter);
    }

    public UmberDagger(Level level, double pX, double pY, double pZ) {
        super(OPEntities.UMBER_DAGGER.get(), level, pX, pY, pZ);
    }

    @Override
    protected DamageSource getDamageSource(Entity shooter) {
        return OPDamageTypes.umberDagger(this.level(), this, shooter);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        MobEffectInstance effect = new MobEffectInstance(OPEffects.GLOOM_TOXIN.get(), 200, 0);
        living.addEffect(effect, this.getEffectSource());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(OPItems.UMBER_DAGGER.get());
    }
    @Override
    public double getBaseDamage() {
        return 4.0D;
    }
}
