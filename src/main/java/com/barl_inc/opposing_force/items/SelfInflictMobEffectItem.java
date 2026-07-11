package com.barl_inc.opposing_force.items;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SelfInflictMobEffectItem extends Item {

    private final Supplier<MobEffect> effectSupplier;
    private final int duration;
    private final int amplifier;

    public SelfInflictMobEffectItem(Properties properties, Supplier<MobEffect> mobEffect, int duration, int amplifier) {
        super(properties);
        this.effectSupplier = mobEffect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(this, duration / 2);
        if (!level.isClientSide() && effectSupplier.get() != null) {
            player.addEffect(new MobEffectInstance(effectSupplier.get(), duration, amplifier));
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
