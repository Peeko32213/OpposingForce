package com.unusualmodding.opposing_force.items;

import com.google.common.collect.Lists;
import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class TeslaCannonItem extends ProjectileWeaponItem implements Vanishable {

    protected static final Predicate<ItemStack> ELECTRIC_CHARGE = (itemstack) -> itemstack.getItem() instanceof ElectricChargeItem;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return ELECTRIC_CHARGE;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ELECTRIC_CHARGE;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return (enchantment.category.canEnchant(stack.getItem()) || enchantment == Enchantments.QUICK_CHARGE || enchantment == Enchantments.MULTISHOT) && enchantment != Enchantments.BINDING_CURSE && enchantment != Enchantments.PIERCING;
    }

    public TeslaCannonItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        int kickback = EnchantmentHelper.getTagEnchantmentLevel(OPEnchantments.KICKBACK.get(), itemstack);
        if (isCharged(itemstack)) {
            shootCharge(level, player, hand, itemstack, 1.0F);
            setCharged(itemstack, false);
            Vec3 lookVec = player.getLookAngle().multiply(-1,-1,-1);
            if (kickback > 0) {
                player.setDeltaMovement(new Vec3(0.5 + (kickback * 0.5), player.getDeltaMovement().y + (kickback * 0.25), 0.5 + (kickback * 0.5)).multiply(lookVec));
            } else {
                player.setDeltaMovement(new Vec3(0.5, player.getDeltaMovement().y + 0.25, 0.5).multiply(lookVec));
            }
            return InteractionResultHolder.consume(itemstack);
        } else if (!player.getProjectile(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(hand);
            }
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack weaponItem, Level world, LivingEntity shooter, int useDuration) {
        int i = this.getUseDuration(weaponItem) - useDuration;
        float f = getPowerForTime(i, weaponItem);
        if (f >= 1.0F && !isCharged(weaponItem) && this.tryLoadProjectiles(shooter, weaponItem)) {
            setCharged(weaponItem, true);
            SoundSource soundcategory = shooter instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_CHARGED.get(), soundcategory, 1.0F, 1.0F / (shooter.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int tickCount) {
        if (!level.isClientSide) {
            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
            SoundEvent soundevent = this.getStartSound(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float f = (float) (stack.getUseDuration() - tickCount) / (float) getChargeDuration(stack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }
            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }
    }

    protected boolean tryLoadProjectiles(LivingEntity shooter, ItemStack stack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);
        int j = i == 0 ? 1 : 3;
        boolean flag = shooter instanceof Player && ((Player)shooter).getAbilities().instabuild;
        ItemStack itemstack = shooter.getProjectile(stack);
        ItemStack itemstack1 = itemstack.copy();
        for (int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }
            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(OPItems.ELECTRIC_CHARGE.get(), 1);
                itemstack1 = itemstack.copy();
            }
            if (!loadProjectile(shooter, stack, itemstack, k > 0, flag)) {
                return false;
            }
        }
        return true;
    }

    protected boolean loadProjectile(LivingEntity shooter, ItemStack weaponItem, ItemStack projectileItem, boolean forceCopyProjectileItem, boolean forceArrowSubTypeFlag) {
        if (!(this.getAllSupportedProjectiles().test(projectileItem) || this.getSupportedHeldProjectiles().test(projectileItem))) {
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
            addChargedProjectile(weaponItem, itemstack);
            return true;
        }
    }

    public float getMultiShotAngle() {
        return 22.5F;
    }

    private void shootCharge(Level world, LivingEntity shooter, InteractionHand handUsed, ItemStack stack, float divergence) {
        List<ItemStack> list = getChargedProjectiles(stack);
        final int msLevel = (list.size() - 1) / 2;

        if (list.isEmpty()) {
            list.add(new ItemStack(OPItems.ELECTRIC_CHARGE.get(), 1));
        }

        if (!world.isClientSide()) {
            if (msLevel <= 0) {
                shootElectricity(world, shooter, handUsed, stack, list.get(0), 1.0F, divergence, 0.0F);
            } else {
                float[] afloat = getShotPitches(shooter.getRandom());

                final float anglePerIteration = this.getMultiShotAngle() / ((float) msLevel);
                float currentAngle = -this.getMultiShotAngle();

                for (int i = 0; i < list.size(); ++i) {
                    ItemStack itemstack = list.get(i);
                    if (!itemstack.isEmpty()) {
                        shootElectricity(world, shooter, handUsed, stack, itemstack, afloat[i], divergence, currentAngle);
                    }
                    currentAngle += anglePerIteration;
                }
            }
        }
        onTeslaCannonShot(world, shooter, stack);
    }

    private void shootElectricity(Level level, LivingEntity shooter, InteractionHand handUsed, ItemStack cannon, ItemStack projectileStack, float shootSoundPitch, float divergence, float simulated) {

        RandomSource random = shooter.level.getRandom();

        ElectricCharge electricCharge = getCharge(level, shooter, projectileStack, cannon);

        boolean capacitance = cannon.getEnchantmentLevel(OPEnchantments.CAPACITANCE.get()) > 0;
        boolean quasar = cannon.getEnchantmentLevel(OPEnchantments.QUASAR.get()) > 0;
        boolean attraction = cannon.getEnchantmentLevel(OPEnchantments.STATIC_ATTRACTION.get()) > 0;

        Vec3 vec31 = shooter.getUpVector(1.0F);
        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(simulated * ((float) Math.PI / 180F), vec31.x, vec31.y, vec31.z);
        Vec3 vec3 = shooter.getViewVector(1.0F);
        Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);

        if (capacitance) {
            electricCharge.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 0.5F, divergence);
            cannon.hurtAndBreak(1, shooter, (shooterTmp) -> shooterTmp.broadcastBreakEvent(handUsed));
            shooter.level().addFreshEntity(electricCharge);
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 0.6F * (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (quasar) {
            electricCharge.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 0.6F, divergence);
            cannon.hurtAndBreak(1, shooter, (shooterTmp) -> shooterTmp.broadcastBreakEvent(handUsed));
            shooter.level().addFreshEntity(electricCharge);
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 0.8F * (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (attraction) {
            electricCharge.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 0.7F, divergence);
            cannon.hurtAndBreak(1, shooter, (shooterTmp) -> shooterTmp.broadcastBreakEvent(handUsed));
            shooter.level().addFreshEntity(electricCharge);
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 0.9F * (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else {
            electricCharge.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 1.0F, divergence);
            cannon.hurtAndBreak(1, shooter, (shooterTmp) -> shooterTmp.broadcastBreakEvent(handUsed));
            shooter.level().addFreshEntity(electricCharge);
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), OPSoundEvents.TESLA_BOW_SHOOT.get(), SoundSource.PLAYERS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
        onCannonShot(level, shooter, cannon);
    }

    public static ElectricCharge getCharge(Level level, LivingEntity entity, ItemStack ammo, ItemStack stack) {

        boolean rebound = stack.getEnchantmentLevel(OPEnchantments.REBOUND.get()) > 0;
        boolean quasar = stack.getEnchantmentLevel(OPEnchantments.QUASAR.get()) > 0;
        boolean attraction = stack.getEnchantmentLevel(OPEnchantments.STATIC_ATTRACTION.get()) > 0;

        int bounces = EnchantmentHelper.getItemEnchantmentLevel(OPEnchantments.REBOUND.get(), stack);
        int capacitance = EnchantmentHelper.getItemEnchantmentLevel(OPEnchantments.CAPACITANCE.get(), stack);

        ElectricChargeItem chargeItem = (ElectricChargeItem)(ammo.getItem() instanceof ElectricChargeItem ? ammo.getItem() : OPItems.ELECTRIC_CHARGE);
        ElectricCharge electricCharge = chargeItem.shootCharge(level, entity);
        electricCharge.setChargeDamage(4.0F);

        if (capacitance > 0) {
            electricCharge.setChargeScale(electricCharge.getChargeScale() + ((float) capacitance));
            electricCharge.setChargeDamage(4.0F + capacitance);
        }

        if (rebound) {
            electricCharge.setMaxBounces(1 + bounces);
            electricCharge.setBouncy(true);
        }

        if (quasar) {
            electricCharge.setChargeScale(electricCharge.getChargeScale() + 2.0F);
            electricCharge.setQuasar(true);
            electricCharge.setChargeDamage(3.0F);
        }

        if (attraction) {
            electricCharge.setChargeScale(electricCharge.getChargeScale());
            electricCharge.setStaticAttraction(true);
        }
        return electricCharge;
    }

    public static boolean isCharged(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && compoundtag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        compoundtag.putBoolean("Charged", charged);
    }

    public static void addChargedProjectile(ItemStack stack, ItemStack itemStack) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        ListTag listtag;
        if (compoundtag.contains("ChargedProjectiles", 9)) {
            listtag = compoundtag.getList("ChargedProjectiles", 10);
        } else {
            listtag = new ListTag();
        }
        CompoundTag compoundtag1 = new CompoundTag();
        itemStack.save(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put("ChargedProjectiles", listtag);
    }

    public static List<ItemStack> getChargedProjectiles(ItemStack stack) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundTag compoundtag = stack.getTag();
        if (compoundtag != null && compoundtag.contains("ChargedProjectiles", 9)) {
            ListTag listtag = compoundtag.getList("ChargedProjectiles", 10);
            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag1 = listtag.getCompound(i);
                list.add(ItemStack.of(compoundtag1));
            }
        }
        return list;
    }

    private static void clearChargedProjectiles(ItemStack p_40944_) {
        CompoundTag compoundtag = p_40944_.getTag();
        if (compoundtag != null) {
            ListTag listtag = compoundtag.getList("ChargedProjectiles", 9);
            listtag.clear();
            compoundtag.put("ChargedProjectiles", listtag);
        }
    }

    public static float[] getShotPitches(RandomSource random) {
        boolean flag = random.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag, random), getRandomShotPitch(!flag, random)};
    }

    private static float getRandomShotPitch(boolean flag, RandomSource random) {
        float f = flag ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    public static void onCannonShot(Level level, LivingEntity entity, ItemStack stack) {
        if (entity instanceof ServerPlayer serverplayer) {
            if (!level.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayer, stack);
            }
            serverplayer.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
        clearChargedProjectiles(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    private SoundEvent getStartSound(int sound) {
        return switch (sound) {
            case 1 -> SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2 -> SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3 -> SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default -> SoundEvents.CROSSBOW_LOADING_START;
        };
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return getChargeDuration(stack) + 3;
    }

    public static int getChargeDuration(ItemStack stack) {
        int i = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return i == 0 ? 50 : 50 - 5 * i;
    }

    public static float getPowerForTime(int time, ItemStack stack) {
        float f = (float) time / (float) getChargeDuration(stack);
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        List<ItemStack> list = getChargedProjectiles(stack);
        if (isCharged(stack) && !list.isEmpty()) {
            ItemStack itemstack = list.get(0);
            components.add(Component.translatable("item.minecraft.crossbow.projectile").append(CommonComponents.SPACE).append(itemstack.getDisplayName()));
        }
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return stack.is(this);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 8;
    }

    public static void onTeslaCannonShot(Level level, LivingEntity entity, ItemStack stack) {
        if (entity instanceof ServerPlayer serverplayer) {
            if (!level.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayer, stack);
            }
            serverplayer.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
        clearChargedProjectiles(stack);
    }
}
