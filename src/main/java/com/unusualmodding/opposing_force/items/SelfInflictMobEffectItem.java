package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.UmberDagger;
import com.unusualmodding.opposing_force.registry.OPMobEffects;
import com.unusualmodding.opposing_force.registry.enums.OPTiers.OPItemTiers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SelfInflictMobEffectItem extends Item {

    public Supplier<MobEffect> effectSupplier;
    public SelfInflictMobEffectItem(Properties properties, Supplier<MobEffect> mobEffect) {
        super(properties);
        this.effectSupplier = mobEffect;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(this, 10);
        if (!level.isClientSide() && effectSupplier.get() != null) {
            player.addEffect(new MobEffectInstance(effectSupplier.get(), 10, 1));
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
