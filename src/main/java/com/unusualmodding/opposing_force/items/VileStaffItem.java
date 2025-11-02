package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.entity.Slug;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class VileStaffItem extends Item implements Vanishable {

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public VileStaffItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 4.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.0F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), OPSoundEvents.SLUG_ATTACK.get(), SoundSource.PLAYERS, 0.5F, 0.9F / (level.random.nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 50);
        if (!level.isClientSide()) {
            summonSlug(player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void summonSlug(Player summoner) {
        Slug slug = OPEntities.SLUG.get().create(summoner.level());
        if (slug != null) {
            float yRot = summoner.getViewYRot(0.0F);
            slug.shootFromRotation(summoner, summoner.getXRot(), summoner.getYRot(), 0.0F, 1.5F, 0.0F);
            slug.setPos(summoner.getEyePosition().add(slug.getDeltaMovement().normalize()));
            slug.yBodyRot = yRot;
            slug.setYHeadRot(yRot);
            slug.tame(summoner);
            slug.setFromSummon(true);
            slug.setLaunched(true);
            slug.setPose(OPPoses.LAUNCHED.get());
            slug.finalizeSpawn((ServerLevel) summoner.level(), summoner.level().getCurrentDifficultyAt(summoner.blockPosition()), MobSpawnType.TRIGGERED, null, null);
            summoner.level().addFreshEntity(slug);
            slug.copyTarget(summoner);
        }
    }
}
