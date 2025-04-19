package com.unusualmodding.opposingforce.common.item;

import com.unusualmodding.opposingforce.common.entity.custom.projectile.ElectricBall;
import com.unusualmodding.opposingforce.core.registry.OPItems;
import com.unusualmodding.opposingforce.core.registry.OPSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Predicate;

public class TeslaBowItem extends CrossbowItem implements Vanishable {
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    protected static final Predicate<ItemStack> ELECTRIC_BALL = (itemstack) -> itemstack.getItem() instanceof ElectricChargeItem;

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return ELECTRIC_BALL;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ELECTRIC_BALL;
    }

    public TeslaBowItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (isCharged(itemstack)) {
            performShootElectricity(pLevel, pPlayer, pHand, itemstack, 1.0F);
            setCharged(itemstack, false);
            Vec3 lookVec = pPlayer.getLookAngle().multiply(-1,-1,-1);
            pPlayer.setDeltaMovement(new Vec3(1.0,1.0,1.0).multiply(lookVec));
            pPlayer.hurtMarked = true;
            return InteractionResultHolder.consume(itemstack);
        }
        else if (!pPlayer.getProjectile(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                pPlayer.startUsingItem(pHand);
            }
            return InteractionResultHolder.consume(itemstack);
        }
        else {
            return InteractionResultHolder.fail(itemstack);
        }
    }
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pCount) {
        if (!pLevel.isClientSide) {
            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, pStack);
            SoundEvent soundevent = this.getStartSound(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float f = (float)(pStack.getUseDuration() - pCount) / (float)modifiedGetChargeDuration(pStack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }
            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }

    }
    @Override
    public void releaseUsing(ItemStack weaponItem, Level world, LivingEntity shooter, int useDuration) {
        int i = this.getUseDuration(weaponItem) - useDuration;
        float f = CrossbowItem.getPowerForTime(i, weaponItem);
        if (f >= 1.0F && !CrossbowItem.isCharged(weaponItem) && this.tryLoadProjectiles(shooter, weaponItem)) {
            CrossbowItem.setCharged(weaponItem, true);
            SoundSource soundcategory = shooter instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSounds.TESLA_BOW_CHARGED.get(), soundcategory, 1.0F, 1.0F / (shooter.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    protected boolean tryLoadProjectiles(LivingEntity shooter, ItemStack weaponItem) {
        int multishotEnchantLevel = weaponItem.getEnchantmentLevel(Enchantments.MULTISHOT);
        int actualShotCount = 1 + (2 * multishotEnchantLevel);
        boolean flag = shooter instanceof Player && (((Player) shooter).getAbilities().instabuild || weaponItem.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0);
        ItemStack itemstack = shooter.getProjectile(weaponItem);
        ItemStack itemStackForAdditionalProjectiles = itemstack.copy();

        for (int i = 0; i < actualShotCount; ++i) {
            if (i > 0) {
                itemstack = itemStackForAdditionalProjectiles.copy();
            }
            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(OPItems.ELECTRIC_CHARGE.get(), 1);
                itemStackForAdditionalProjectiles = itemstack.copy();
            }
            if (!this.loadProjectile(shooter, weaponItem, itemstack, i > 0, flag)) {
                return false;
            }
        }
        return true;
    }

    protected boolean loadProjectile(LivingEntity shooter, ItemStack weaponItem, ItemStack projectileItem, boolean forceCopyProjectileItem, boolean forceArrowSubTypeFlag) {
        if(!(this.getAllSupportedProjectiles().test(projectileItem) || this.getSupportedHeldProjectiles().test(projectileItem))) {
            projectileItem = new ItemStack(OPItems.ELECTRIC_CHARGE.get(), 1);
        }
        if (projectileItem.isEmpty()) {
            return false;
        } else {
            boolean flag = forceArrowSubTypeFlag && projectileItem.getItem() instanceof ElectricChargeItem;
            ItemStack itemstack;
            if (!flag && !forceArrowSubTypeFlag && !forceCopyProjectileItem) {
                itemstack = projectileItem.split(1);
                if (projectileItem.isEmpty() && (shooter instanceof Player)) {
                    ((Player) shooter).getInventory().removeItem(projectileItem);
                }
            } else {
                itemstack = projectileItem.copy();
            }
            CrossbowItem.addChargedProjectile(weaponItem, itemstack);
            return true;
        }
    }

    public int getMaxChargeTime() {
        return 80;
    }

    public int getUseDuration(ItemStack pStack) {
        return modifiedGetChargeDuration(pStack) + 3;
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    private SoundEvent getStartSound(int pEnchantmentLevel) {
        return switch (pEnchantmentLevel) {
            case 1 -> SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2 -> SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3 -> SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default -> SoundEvents.CROSSBOW_LOADING_START;
        };
    }

    public float getMultiShotAngle() {
        return 22.5F;
    }

    public float[] modifiedGetShotPitches(RandomSource randomSource, final int multiShotLevel) {
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

    private void performShootElectricity(Level world, LivingEntity shooter, InteractionHand handUsed, ItemStack crossbow, float divergence) {
        List<ItemStack> list = CrossbowItem.getChargedProjectiles(crossbow);
        final int msLevel = (list.size() - 1) / 2;

        if (list.isEmpty()) {
            list.add(new ItemStack(OPItems.ELECTRIC_CHARGE.get(), 1));
        }
        if (msLevel <= 0) {
            shootElectricity(world, shooter, handUsed, crossbow, list.get(0), 1.0F, divergence, 0.0F);
        }
        else {
            float[] afloat = this.modifiedGetShotPitches(shooter.getRandom(), msLevel);

            final float anglePerIteration = this.getMultiShotAngle() / ((float) msLevel);
            float currentAngle = -this.getMultiShotAngle();

            for (int i = 0; i < list.size(); ++i) {
                ItemStack itemstack = list.get(i);
                if (!itemstack.isEmpty()) {
                    shootElectricity(world, shooter, handUsed, crossbow, itemstack, afloat[i], divergence, currentAngle);
                }
                currentAngle += anglePerIteration;
            }
        }
        CrossbowItem.onCrossbowShot(world, shooter, crossbow);
    }

    private void shootElectricity(Level world, LivingEntity shooter, InteractionHand handUsed, ItemStack crossbow, ItemStack projectileStack, float shootSoundPitch, float divergence, float simulated) {
        if (!world.isClientSide) {
            Projectile projectileentity = getElectricCharge(world, shooter, projectileStack);

            Vec3 vec31 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(simulated * ((float)Math.PI / 180F), vec31.x, vec31.y, vec31.z);
            Vec3 vec3 = shooter.getViewVector(1.0F);
            Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
            projectileentity.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 1.5F, divergence);

            crossbow.hurtAndBreak(1, shooter, (shooterTmp) -> shooterTmp.broadcastBreakEvent(handUsed));
            shooter.level().addFreshEntity(projectileentity);
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSounds.TESLA_BOW_SHOOT.get(), SoundSource.PLAYERS, 1.0F, shootSoundPitch * 1.2F);
        }
    }

    static ElectricBall getElectricCharge(Level pLevel, LivingEntity pLivingEntity, ItemStack pAmmoStack) {
        ElectricChargeItem arrowitem = (ElectricChargeItem)(pAmmoStack.getItem() instanceof ElectricChargeItem ? pAmmoStack.getItem() : OPItems.ELECTRIC_CHARGE);
        ElectricBall electricBall = arrowitem.shootCharge(pLevel, pLivingEntity);
        electricBall.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        return electricBall;
    }

    public int modifiedGetChargeDuration(ItemStack crossbow) {
        int i = crossbow.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
        return i == 0 ? this.getMaxChargeTime() : this.getMaxChargeTime() - this.getChargeTimeReductionPerQuickChargeLevel() * i;
    }

    public int getChargeTimeReductionPerQuickChargeLevel() {
        return this.getMaxChargeTime() / 5;
    }
}
