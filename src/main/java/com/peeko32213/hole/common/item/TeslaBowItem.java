package com.peeko32213.hole.common.item;

import com.peeko32213.hole.core.registry.HoleItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class TeslaBowItem extends CrossbowItem implements Vanishable, ModifiedCrossbowMethod {
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;
    protected static final Predicate<ItemStack> PREDICATE_BOLTS_ONLY = (itemstack) -> {
        return itemstack.getItem() instanceof ElectricChargeItem;
    };

    protected static final Predicate<ItemStack> PREDICATE_BOLTS_ONLY_NO_EXPLOSIVE = (itemstack) -> {
        return itemstack.getItem() instanceof ElectricChargeItem;
    };

    //This only checks the items in off and main hand
    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return PREDICATE_BOLTS_ONLY_NO_EXPLOSIVE;
    }

    //Checks for all projectiles in the inventory
    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return PREDICATE_BOLTS_ONLY_NO_EXPLOSIVE;
    }

    public TeslaBowItem(Properties properties) {
        super(properties);
    }

    //DONE: Mixin interface to CrossbowItem that has methods for the below stuff
    //DONE: Add mixin / ASM that changes CrossbowItem.performShooting() to call a method from the item
    //DONE: Add mixin / ASM that changes CrossbowItem.getChargeDuration() to call a method from the item
    //DONE: Move CrossbowItem.shootProjectile() to Item

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (isCharged(itemstack)) {
            modifiedPerformShooting(pLevel, pPlayer, pHand, itemstack, getShootingPower(itemstack), 1.0F);
            setCharged(itemstack, false);
            Vec3 lookVec = pPlayer.getLookAngle().multiply(-1,-1,-1);
            pPlayer.setDeltaMovement(new Vec3(1.0,1.0,1.0).multiply(lookVec));
            pPlayer.hurtMarked = true;
            return InteractionResultHolder.consume(itemstack);
        } else if (!pPlayer.getProjectile(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                pPlayer.startUsingItem(pHand);
            }

            return InteractionResultHolder.consume(itemstack);
        } else {
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
                pLevel.playSound((Player)null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                pLevel.playSound((Player)null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
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
            world.playSound((Player) null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (shooter.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);

        }
    }

    // Attention: this actually differs from vanilla!
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
                itemstack = new ItemStack(HoleItems.ELECTRIC_CHARGE.get(), 1);
                itemStackForAdditionalProjectiles = itemstack.copy();
            }

            if (!this.loadProjectile(shooter, weaponItem, itemstack, i > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    protected boolean loadProjectile(LivingEntity shooter, ItemStack weaponItem, ItemStack projectileItem, boolean forceCopyProjectileItem, boolean forceArrowSubTypeFlag) {
        //!!This is needed to avoid the arrows glitching in !!
        if(!(this.getAllSupportedProjectiles().test(projectileItem) || this.getSupportedHeldProjectiles().test(projectileItem))) {
            projectileItem = new ItemStack(HoleItems.ELECTRIC_CHARGE.get(), 1);
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


    //Override charging duration
    @Override
    public int getMaxChargeTime() {
        return 25;
    }

    @Override
    public int getDefaultProjectileRange() {
        return super.getDefaultProjectileRange();
    }

    @Override
    public float getProjectileSpeedModifier() {
        return 1.0F;
    }

    public int getUseDuration(ItemStack pStack) {
        return modifiedGetChargeDuration(pStack) + 3;
    }


    protected boolean parentClassIsEnchantable(ItemStack stack) {
        return super.isEnchantable(stack);
    }

    protected boolean parentClassIsBookEnchantable(ItemStack stack, ItemStack book) {
        return super.isEnchantable(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    private static float getShootingPower(ItemStack pCrossbowStack) {
        return containsChargedProjectile(pCrossbowStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }

    private SoundEvent getStartSound(int pEnchantmentLevel) {
        switch (pEnchantmentLevel) {
            case 1:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default:
                return SoundEvents.CROSSBOW_LOADING_START;
        }
    }
}
