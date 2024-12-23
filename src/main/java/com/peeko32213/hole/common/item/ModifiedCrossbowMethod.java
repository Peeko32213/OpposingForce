package com.peeko32213.hole.common.item;

import com.peeko32213.hole.common.entity.projectile.EntitySmallElectricBall;
import com.peeko32213.hole.core.registry.HoleItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public interface ModifiedCrossbowMethod {

    public default float getMultiShotAngle() {
        // Default value is 10 degrees resulting in a opening angle of 20 degrees
        return 22.5F;
    }

    public default float[] modifiedGetShotPitches(RandomSource randomSource, final int multiShotLevel) {
        if (multiShotLevel <= 0) {
            return new float[] { 1.0F };
        }
        float[] result = new float[multiShotLevel * 2 + 1];

        for (int i = 0; i < multiShotLevel; i++) {
            boolean currentFlag = randomSource.nextBoolean();
            result[i] = CrossbowItem.getRandomShotPitch(currentFlag, randomSource);
            result[result.length - (i + 1)] = CrossbowItem.getRandomShotPitch(!currentFlag, randomSource);
        }
        result[multiShotLevel] = 1.0F;

        return result;
    }

    public default void modifiedPerformShooting(Level world, LivingEntity shooter, InteractionHand handUsed, ItemStack crossbow, float speed, float divergence) {
        List<ItemStack> list = CrossbowItem.getChargedProjectiles(crossbow);
        final int msLevel = (list.size() - 1) / 2;
        final boolean creativeModeFlag = shooter instanceof Player && ((Player) shooter).getAbilities().instabuild;

        if(list.isEmpty()) {
            list.add(new ItemStack(HoleItems.ELECTRIC_CHARGE.get(), 1));
        }

        if (msLevel <= 0) {
            modifiedShootProjectile(world, shooter, handUsed, crossbow, list.get(0), 1.0F, creativeModeFlag, speed, divergence, 0.0F);
        } else {

            float[] afloat = this.modifiedGetShotPitches(shooter.getRandom(), msLevel);

            // DONE: Adjust to not be hardcoded to indexes

            // Multishot defines additional projectiles per side
            final float anglePerIteration = this.getMultiShotAngle() / ((float) msLevel); // Divide by the multishot level:
            float currentAngle = -this.getMultiShotAngle();

            for (int i = 0; i < list.size(); ++i) {
                ItemStack itemstack = list.get(i);

                if (!itemstack.isEmpty()) {
                    modifiedShootProjectile(world, shooter, handUsed, crossbow, itemstack, afloat[i], creativeModeFlag, speed, divergence, currentAngle);
                }
                currentAngle += anglePerIteration;
            }
        }
        CrossbowItem.onCrossbowShot(world, shooter, crossbow);
    }

    public default void modifiedShootProjectile(Level world, LivingEntity shooter, InteractionHand handUsed, ItemStack crossbow, ItemStack projectileStack, float shootSoundPitch, boolean flagProjectileCantBePickedUp, float speed, float divergence,
                                                float simulated) {
        if (!world.isClientSide) {
            boolean flag = projectileStack.getItem() == Items.FIREWORK_ROCKET;
            Projectile projectileentity;
            if (flag) {
                projectileentity = new FireworkRocketEntity(world, projectileStack, shooter, shooter.getX(), shooter.getEyeY() - (double) 0.15F, shooter.getZ(), true);
            } else {
                projectileentity = getElectricCharge(world, shooter, crossbow, projectileStack);
                if (flagProjectileCantBePickedUp || simulated != 0.0F) {
                    //((EntitySmallElectricBall) projectileentity).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }

            if (shooter instanceof CrossbowAttackMob) {
                CrossbowAttackMob icrossbowuser = (CrossbowAttackMob) shooter;
                icrossbowuser.shootCrossbowProjectile(icrossbowuser.getTarget(), crossbow, projectileentity, simulated);
            } else {
                Vec3 vec31 = shooter.getUpVector(1.0F);
                Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(simulated * ((float)Math.PI / 180F)), vec31.x, vec31.y, vec31.z);
                Vec3 vec3 = shooter.getViewVector(1.0F);
                Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
                projectileentity.shoot((double) vector3f.x(), (double) vector3f.y(), (double) vector3f.z(), speed * this.getProjectileSpeedModifier(), divergence);
            }

            crossbow.hurtAndBreak(flag ? 3 : 1, shooter, (shooterTmp) -> {
                shooterTmp.broadcastBreakEvent(handUsed);
            });
            world.addFreshEntity(projectileentity);
            world.playSound((Player) null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, shootSoundPitch);
        }
    }
    public static EntitySmallElectricBall getElectricCharge(Level pLevel, LivingEntity pLivingEntity, ItemStack pCrossbowStack, ItemStack pAmmoStack) {
        ElectricChargeItem arrowitem = (ElectricChargeItem)(pAmmoStack.getItem() instanceof ElectricChargeItem ? pAmmoStack.getItem() : HoleItems.ELECTRIC_CHARGE);
        EntitySmallElectricBall abstractarrow = arrowitem.createDart(pLevel, pLivingEntity);
        if (pLivingEntity instanceof Player) {
            //abstractarrow.setCritArrow(true);
        }

        abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        //abstractarrow.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, pCrossbowStack);
        if (i > 0) {
            //abstractarrow.setPierceLevel((byte)i);
        }

        return abstractarrow;
    }
    public default int modifiedGetChargeDuration(ItemStack crossbow) {
        int i = crossbow.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
        return i == 0 ? this.getMaxChargeTime() : this.getMaxChargeTime() - this.getChargeTimeReductionPerQuickChargeLevel() * i;
    }

    default int getMaxChargeTime() {
        return 25;
    }

    default int getChargeTimeReductionPerQuickChargeLevel() {
        return this.getMaxChargeTime() / 5;
    }

    default float getProjectileSpeedModifier() {
        return 1.0F;
    }
}
