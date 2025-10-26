package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.network.ElectricChargeSyncS2CPacket;
import com.unusualmodding.opposing_force.registry.OPEffects;
import com.unusualmodding.opposing_force.registry.OPNetwork;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.enums.OPItemTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SparkBladeItem extends SwordItem {

    public SparkBladeItem(Properties properties) {
        super(OPItemTiers.ELECTRIC, 3, -2.4F, properties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        RandomSource random = attacker.getRandom();
        if (super.hurtEnemy(stack, target, attacker)) {
            if (!target.level().isClientSide) {
                for (int i = 0; i < 12; i++) {
                    ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                            .pos(target.getX(), target.getY() + target.getBbHeight() * 0.5F, target.getZ())
                            .range(3 + random.nextInt(2))
                            .size(0.08F)
                            .color(0.3F + (random.nextFloat() / 8), 0.5F + (random.nextFloat() / 8), 0.8F + (random.nextFloat() / 8), 1F)
                            .build();
                    OPNetwork.sendToClients(packet);
                }
            }
            target.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 60, 0));
            target.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 1.0F, 1.0F / (target.level().getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int useTime) {
        int i = this.getUseDuration(stack) - useTime;
        if (i >= 10) {
            if (entity instanceof Player player) {
                player.getCooldowns().addCooldown(this, 60);
                Vec3 teleportPos = findTeleportLocation(level, player, 12);
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    stack.hurtAndBreak(2, player, (player1) -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }

                player.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
                for (int i1 = 0; i1 < 12; i1++) {
                    ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                            .pos(player.getX(), player.getY() + player.getBbHeight() * 0.5F, player.getZ())
                            .range(2 + player.getRandom().nextInt(2))
                            .size(0.08F)
                            .color(0.3F + (player.getRandom().nextFloat() / 8), 0.5F + (player.getRandom().nextFloat() / 8), 0.8F + (player.getRandom().nextFloat() / 8), 1F)
                            .build();
                    OPNetwork.sendToClients(packet);
                }

                player.teleportTo(teleportPos.x, teleportPos.y, teleportPos.z);

                player.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
                for (int i1 = 0; i1 < 12; i1++) {
                    ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                            .pos(player.getX(), player.getY() + player.getBbHeight() * 0.5F, player.getZ())
                            .range(2 + player.getRandom().nextInt(2))
                            .size(0.08F)
                            .color(0.3F + (player.getRandom().nextFloat() / 8), 0.5F + (player.getRandom().nextFloat() / 8), 0.8F + (player.getRandom().nextFloat() / 8), 1F)
                            .build();
                    OPNetwork.sendToClients(packet);
                }
            }
        }

        super.releaseUsing(stack, level, entity, useTime);
    }

    public static Vec3 findTeleportLocation(Level level, LivingEntity entity, float maxDistance) {
        var blockHitResult = getTargetBlock(level, entity, ClipContext.Fluid.NONE, maxDistance);
        var pos = blockHitResult.getBlockPos();

        Vec3 bbOffset = entity.getForward().normalize().multiply(entity.getBbWidth() / 3, 0, entity.getBbHeight() / 3);
        Vec3 bbImpact = blockHitResult.getLocation().subtract(bbOffset);
        int ledgeY = (int) level.clip(new ClipContext(Vec3.atBottomCenterOf(pos).add(0, 3, 0), Vec3.atBottomCenterOf(pos), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)).getLocation().y;
        boolean isAir = level.getBlockState(new BlockPos(new Vec3i(pos.getX(), ledgeY, pos.getZ()))).isAir();
        boolean los = level.clip(new ClipContext(bbImpact, bbImpact.add(0, ledgeY - pos.getY(), 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS;

        if (isAir && los && Math.abs(ledgeY - pos.getY()) <= 3) {
            Vec3 correctedPos = new Vec3(pos.getX(), ledgeY, pos.getZ());
            return correctedPos.add(0.5, 0.076, 0.5);
        } else {
            return level.clip(new ClipContext(bbImpact, bbImpact.add(0, -entity.getBbHeight(), 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getLocation().add(0, 0.076, 0);
        }
    }

    public static BlockHitResult getTargetBlock(Level level, LivingEntity entity, ClipContext.Fluid clipContext, double reach) {
        var rotation = entity.getLookAngle().normalize().scale(reach);
        var pos = entity.getEyePosition();
        var dest = rotation.add(pos);
        return level.clip(new ClipContext(pos, dest, ClipContext.Block.COLLIDER, clipContext, entity));
    }
}
