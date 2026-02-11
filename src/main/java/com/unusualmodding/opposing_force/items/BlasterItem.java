package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.LaserBolt;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.tags.OPItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class BlasterItem extends Item implements Vanishable {

    public static final Predicate<ItemStack> AMMO = (stack) -> stack.is(OPItemTags.BLASTER_AMMO);
    private final int laserColor;

    public BlasterItem(int laserColor) {
        super(new Properties().stacksTo(1).durability(651));
        this.laserColor = laserColor;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(stack.getItem()) && enchantment != Enchantments.BINDING_CURSE;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
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

    public int getLaserColor() {
        return this.laserColor;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ItemStack ammoStack = getAmmo(player);

        if (ammoStack.isEmpty() && !player.isCreative()) {
            player.getCooldowns().addCooldown(itemStack.getItem(), 10);
            return InteractionResultHolder.success(itemStack);
        }
        if (shouldSwap(player, itemStack, hand, stack -> stack.getItem() instanceof BlasterItem)) return InteractionResultHolder.fail(itemStack);

        Vec3 vector3d = player.getViewVector(1.0F);
        Vec3 vec3 = vector3d.normalize();
        float yRot = (float) (Mth.atan2(vec3.z, vec3.x) * (180F / Math.PI)) + 90F;
        float xRot = (float) -(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180F / Math.PI));
        double inaccuracy = 0.015D;
        double xOffset = level.getRandom().nextGaussian() * inaccuracy;
        double yOffset = level.getRandom().nextGaussian() * inaccuracy;
        double zOffset = level.getRandom().nextGaussian() * inaccuracy;

        LaserBolt laserBolt = new LaserBolt(player, level, player.position().x(), player.getEyePosition().y(), player.position().z());
        Vec3 barrelPos = getBarrelVec(player, hand == InteractionHand.MAIN_HAND, new Vec3(0.55F, -0.45F, 1.15F));
        Vec3 correction = getBarrelVec(player, hand == InteractionHand.MAIN_HAND, new Vec3(-0.035F, 0, 0)).subtract(player.position().add(0, player.getEyeHeight(), 0));
        Vec3 lookVec = player.getLookAngle().add(xOffset, yOffset, zOffset).normalize();
        Vec3 motion = lookVec.add(correction).normalize().scale(1.75F);

        laserBolt.setData(player, hand, barrelPos, motion, xRot, yRot);
        level.addFreshEntity(laserBolt);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), OPSoundEvents.BLASTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F + level.getRandom().nextFloat() * 0.25F);

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

        applyCooldown(player, hand, stack -> stack.getItem() instanceof BlasterItem, 10);
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !(oldStack.getItem() instanceof BlasterItem) || !(newStack.getItem() instanceof BlasterItem);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player) {
        return false;
    }

    private static boolean shouldSwap(Player player, ItemStack item, InteractionHand hand, Predicate<ItemStack> predicate) {
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

    private static void applyCooldown(Player player, InteractionHand hand, Predicate<ItemStack> predicate, int cooldown) {
        if (cooldown <= 0) return;
        boolean offhandBlaster = predicate.test(player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND));
        int i = offhandBlaster ? (int) (cooldown * 0.75F) : cooldown;
        for (Item blaster : BuiltInRegistries.ITEM) {
            if (blaster.builtInRegistryHolder().is(OPItemTags.BLASTERS)) {
                player.getCooldowns().addCooldown(blaster, i);
            }
        }
    }

    private static Vec3 getBarrelVec(Player player, boolean mainHand, Vec3 rightHandForward) {
        Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
        float yaw = (float) ((player.getYRot()) / -180 * Math.PI);
        float pitch = (float) ((player.getXRot()) / -180 * Math.PI);
        int flip = mainHand == (player.getMainArm() == HumanoidArm.RIGHT) ? -1 : 1;
        Vec3 barrelPosNoTransform = new Vec3(flip * rightHandForward.x, rightHandForward.y, rightHandForward.z);
        return start.add(barrelPosNoTransform.xRot(pitch).yRot(yaw));
    }
}
