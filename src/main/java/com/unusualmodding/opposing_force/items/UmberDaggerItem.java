package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.UmberDagger;
import com.unusualmodding.opposing_force.registry.OPMobEffects;
import com.unusualmodding.opposing_force.registry.enums.OPTiers.OPItemTiers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class UmberDaggerItem extends ThrowableWeaponItem {

    public UmberDaggerItem(Properties properties) {
        super(OPItemTiers.UMBER, 1, -2, properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 10);
        if (!level.isClientSide()) {
            UmberDagger umberDagger = new UmberDagger(level, player);
            umberDagger.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.75F);
            level.addFreshEntity(umberDagger);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.hurtAndBreak(2, player, (player1) -> player1.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (super.hurtEnemy(stack, target, attacker)) {
            target.addEffect(new MobEffectInstance(OPMobEffects.GLOOM_TOXIN.get(), 100, 0));
            return true;
        }
        return false;
    }
}
