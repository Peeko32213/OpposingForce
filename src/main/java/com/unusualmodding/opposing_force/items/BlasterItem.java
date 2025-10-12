package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.LaserBolt;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.tags.OPItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class BlasterItem extends Item {

    public static final Predicate<ItemStack> AMMO = (stack) -> stack.is(OPItemTags.BLASTER_AMMO);

    public BlasterItem(Properties properties) {
        super(properties);
    }

    public ItemStack getAmmo(Player player) {
        if (player.isCreative()) {
            return ItemStack.EMPTY;
        }
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (AMMO.test(itemstack)) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ItemStack ammoStack = getAmmo(player);

        if (ammoStack.isEmpty() && !player.isCreative()) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (shouldSwap(player, itemStack, hand, stack -> stack.getItem() instanceof BlasterItem)) {
            return InteractionResultHolder.fail(itemStack);
        }

        Vec3 vector3d = player.getViewVector(1F);
        Vec3 vec3 = vector3d.normalize();
        float yRot = (float) (Mth.atan2(vec3.z, vec3.x) * (180F / Math.PI)) + 90F;
        float xRot = (float) -(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180F / Math.PI));
        double xOffset = level.getRandom().nextGaussian() * 0.03D;
        double yOffset = level.getRandom().nextGaussian() * 0.03D;
        double zOffset = level.getRandom().nextGaussian() * 0.03D;

        LaserBolt laserBolt = new LaserBolt(player, level, player.position().x(), player.getEyePosition().y(), player.position().z());
        Vec3 barrelPos = getBarrelVec(player, hand == InteractionHand.MAIN_HAND, new Vec3(0.55F, -0.45F, 1.15F));
        Vec3 correction = getBarrelVec(player, hand == InteractionHand.MAIN_HAND, new Vec3(-0.035F, 0, 0)).subtract(player.position().add(0, player.getEyeHeight(), 0));
        Vec3 lookVec = player.getLookAngle().add(xOffset, yOffset, zOffset).normalize();
        Vec3 motion = lookVec.add(correction).normalize().scale(2.0F);

        laserBolt.setPos(barrelPos.x, barrelPos.y, barrelPos.z);
        laserBolt.setDeltaMovement(motion);
        laserBolt.setYRot(yRot);
        laserBolt.setXRot(xRot);
        laserBolt.setOwner(player);
        if (itemStack.getEnchantmentLevel(OPEnchantments.SPLITTING.get()) > 0) {
            laserBolt.setDisruptor(true);
            laserBolt.setDisruptorLevel(itemStack.getEnchantmentLevel(OPEnchantments.SPLITTING.get()));
        }
        if (itemStack.getEnchantmentLevel(OPEnchantments.RAPID_FIRE.get()) > 0) {
            laserBolt.setRapidFire(true);
        }
        if (itemStack.getEnchantmentLevel(OPEnchantments.FREEZE_RAY.get()) > 0) {
            laserBolt.setFreezing(true);
        }
        level.addFreshEntity(laserBolt);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), OPSoundEvents.BLASTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, (level.getRandom().nextFloat() * 0.5F + (itemStack.getEnchantmentLevel(OPEnchantments.RAPID_FIRE.get()) > 0 ? 1F : 0.8F)));

        if (!player.isCreative()) {
            itemStack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
            if (itemStack.getEnchantmentLevel(OPEnchantments.POWER_SUPPLY.get()) > 0) {
                if (level.getRandom().nextInt(itemStack.getEnchantmentLevel(OPEnchantments.POWER_SUPPLY.get())) == 0) {
                    ammoStack.shrink(1);
                    if (ammoStack.isEmpty()) {
                        player.getInventory().removeItem(ammoStack);
                    }
                }
            } else {
                ammoStack.shrink(1);
                if (ammoStack.isEmpty()) {
                    player.getInventory().removeItem(ammoStack);
                }
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        applyCooldown(player, itemStack, hand, stack -> stack.getItem() instanceof BlasterItem, 15 - (itemStack.getEnchantmentLevel(OPEnchantments.RAPID_FIRE.get()) * 3));
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(OPItems.BLASTER.get()) || !newStack.is(OPItems.BLASTER.get());
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player player) {
        return false;
    }

    public static boolean shouldSwap(Player player, ItemStack item, InteractionHand hand, Predicate<ItemStack> predicate) {
        boolean isSwap = item.getTag().contains("Swapped");
        boolean mainHand = hand == InteractionHand.MAIN_HAND;
        boolean offhandBlaster = predicate.test(player.getItemInHand(mainHand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND));

        if (mainHand && isSwap && offhandBlaster) return true;
        if (mainHand && !isSwap && offhandBlaster) item.getTag().putBoolean("Swapped", true);
        if (!mainHand && isSwap) item.getTag().remove("Swapped");
        if (!mainHand && offhandBlaster) player.getItemInHand(InteractionHand.MAIN_HAND).getTag().remove("Swapped");
        player.startUsingItem(hand);
        return false;
    }

    public static void applyCooldown(Player player, ItemStack item, InteractionHand hand, Predicate<ItemStack> predicate, int cooldown) {
        if (cooldown <= 0) return;
        boolean offhandBlaster = predicate.test(player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND));
        player.getCooldowns().addCooldown(item.getItem(), offhandBlaster ? cooldown * 3 / 4 : cooldown);
    }

    public static Vec3 getBarrelVec(Player player, boolean mainHand, Vec3 rightHandForward) {
        Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
        float yaw = (float) ((player.getYRot()) / -180 * Math.PI);
        float pitch = (float) ((player.getXRot()) / -180 * Math.PI);
        int flip = mainHand == (player.getMainArm() == HumanoidArm.RIGHT) ? -1 : 1;
        Vec3 barrelPosNoTransform = new Vec3(flip * rightHandForward.x, rightHandForward.y, rightHandForward.z);
        return start.add(barrelPosNoTransform.xRot(pitch).yRot(yaw));
    }
}
