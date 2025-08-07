package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.enchantments.OPEnchantmentLogic;
import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
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
import net.minecraft.world.item.enchantment.Enchantment;
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

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(stack.getItem()) && enchantment != Enchantments.BINDING_CURSE && enchantment != Enchantments.PIERCING;
    }

    public TeslaBowItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (isCharged(itemstack)) {
            shootCharge(pLevel, pPlayer, pHand, itemstack, 1.0F);
            setCharged(itemstack, false);

            OPEnchantmentLogic.kickbackLogic(pPlayer, itemstack);

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

    @Override
    public void releaseUsing(ItemStack weaponItem, Level world, LivingEntity shooter, int useDuration) {
        int i = this.getUseDuration(weaponItem) - useDuration;
        float f = CrossbowItem.getPowerForTime(i, weaponItem);
        if (f >= 1.0F && !CrossbowItem.isCharged(weaponItem) && this.tryLoadProjectiles(shooter, weaponItem)) {
            CrossbowItem.setCharged(weaponItem, true);
            SoundSource soundcategory = shooter instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_CHARGED.get(), soundcategory, 1.0F, 1.0F / (shooter.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    protected boolean tryLoadProjectiles(LivingEntity shooter, ItemStack crossbow) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, crossbow);
        int j = i == 0 ? 1 : 3;
        boolean flag = shooter instanceof Player && ((Player)shooter).getAbilities().instabuild;
        ItemStack itemstack = shooter.getProjectile(crossbow);
        ItemStack itemstack1 = itemstack.copy();
        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(OPItems.ELECTRIC_CHARGE.get(), 1);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(shooter, crossbow, itemstack, k > 0, flag)) {
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

    public float getMultiShotAngle() {
        return 22.5F;
    }

    private void shootCharge(Level world, LivingEntity shooter, InteractionHand handUsed, ItemStack crossbow, float divergence) {
        List<ItemStack> list = CrossbowItem.getChargedProjectiles(crossbow);
        final int msLevel = (list.size() - 1) / 2;

        if (list.isEmpty()) {
            list.add(new ItemStack(OPItems.ELECTRIC_CHARGE.get(), 1));
        }

        if (!world.isClientSide()) {
            if (msLevel <= 0) {
                shootElectricity(world, shooter, handUsed, crossbow, list.get(0), 1.0F, divergence, 0.0F);
            } else {
                float[] afloat = getShotPitches(shooter.getRandom());

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
        }
        CrossbowItem.onCrossbowShot(world, shooter, crossbow);
    }

    private void shootElectricity(Level world, LivingEntity shooter, InteractionHand handUsed, ItemStack crossbow, ItemStack projectileStack, float shootSoundPitch, float divergence, float simulated) {

        RandomSource random = shooter.level.getRandom();

        Projectile projectileentity = getCharge(world, shooter, projectileStack, crossbow, false);
        Projectile bigProjectileentity = getCharge(world, shooter, projectileStack, crossbow, true);

        boolean largeBall = crossbow.getEnchantmentLevel(OPEnchantments.CAPACITANCE.get()) > 0;
        boolean rainbow = crossbow.getEnchantmentLevel(OPEnchantments.QUASAR.get()) > 0;

        Vec3 vec31 = shooter.getUpVector(1.0F);
        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(simulated * ((float) Math.PI / 180F), vec31.x, vec31.y, vec31.z);
        Vec3 vec3 = shooter.getViewVector(1.0F);
        Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);

        if (largeBall) {
            bigProjectileentity.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 0.8F, divergence);
            crossbow.hurtAndBreak(1, shooter, (shooterTmp) -> shooterTmp.broadcastBreakEvent(handUsed));
            shooter.level().addFreshEntity(bigProjectileentity);
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_SHOOT.get(), SoundSource.PLAYERS, 1.25F, 0.8F * (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
        if (!largeBall) {
            projectileentity.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 1.25F, divergence);
            crossbow.hurtAndBreak(1, shooter, (shooterTmp) -> shooterTmp.broadcastBreakEvent(handUsed));
            shooter.level().addFreshEntity(projectileentity);
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_SHOOT.get(), SoundSource.PLAYERS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
        if (rainbow) {
            projectileentity.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 0.6F, divergence);
            crossbow.hurtAndBreak(1, shooter, (shooterTmp) -> shooterTmp.broadcastBreakEvent(handUsed));
            shooter.level().addFreshEntity(projectileentity);
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_SHOOT.get(), SoundSource.PLAYERS, 1.1F, 0.6F * (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    static ElectricCharge getCharge(Level pLevel, LivingEntity pLivingEntity, ItemStack pAmmoStack, ItemStack crossbow, boolean bigCharge) {

        boolean bouncy = crossbow.getEnchantmentLevel(OPEnchantments.REBOUND.get()) > 0;
        boolean rainbow = crossbow.getEnchantmentLevel(OPEnchantments.QUASAR.get()) > 0;
        int bounces = EnchantmentHelper.getItemEnchantmentLevel(OPEnchantments.REBOUND.get(), crossbow);

        int chargeSize = EnchantmentHelper.getItemEnchantmentLevel(OPEnchantments.CAPACITANCE.get(), crossbow);

        ElectricChargeItem arrowitem = (ElectricChargeItem)(pAmmoStack.getItem() instanceof ElectricChargeItem ? pAmmoStack.getItem() : OPItems.ELECTRIC_CHARGE);
        ElectricCharge electricCharge = arrowitem.shootCharge(pLevel, pLivingEntity);
        electricCharge.setSoundEvent(SoundEvents.CROSSBOW_HIT);

        if (bigCharge) {
            electricCharge.setChargeScale(electricCharge.getChargeScale() + ((float) chargeSize - 0.5F));
        }

        if (bouncy) {
            electricCharge.setMaxBounces(1 + bounces);
            electricCharge.setBouncy(true);
        }

        if (rainbow) {
            electricCharge.setChargeScale(electricCharge.getChargeScale() + 2.0F);
            electricCharge.setQuasar(true);
        }
        return electricCharge;
    }
}
