package com.barl_inc.opposing_force.items.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LapisHoeItem extends ConfigurableHoeItem {

    public LapisHoeItem(Properties properties) {
        super(OPToolDefinitions.LAPIS, -2, -1.0F, properties);
    }

    @Override
    public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        int level = super.getEnchantmentLevel(stack, enchantment);
        if (level > 0 && enchantment.getMaxLevel() > 1) {
            return level + 1;
        }
        return level;
    }

    @Override
    public Map<Enchantment, Integer> getAllEnchantments(ItemStack stack) {
        Map<Enchantment, Integer> allEnchantments = super.getAllEnchantments(stack);
        Map<Enchantment, Integer> enchantMap = new HashMap<>();
        for (Map.Entry<Enchantment, Integer> entry : allEnchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();
            if (enchantment.getMaxLevel() > 1) {
                enchantMap.put(enchantment, level + 1);
            } else {
                enchantMap.put(enchantment, level);
            }
        }
        return enchantMap;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        stack.hideTooltipPart(ItemStack.TooltipPart.ENCHANTMENTS);
        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int enchantLevel = entry.getValue();
            MutableComponent mutableComponent = (MutableComponent) enchantment.getFullname(enchantLevel);
            if (enchantment.getMaxLevel() > 1) {
                MutableComponent levelAddition = Component.literal(" (+I)").withStyle(ChatFormatting.GRAY);
                mutableComponent.append(levelAddition);
            }
            tooltip.add(mutableComponent);
        }
    }
}
