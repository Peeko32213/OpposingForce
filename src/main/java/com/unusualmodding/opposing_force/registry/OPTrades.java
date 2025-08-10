package com.unusualmodding.opposing_force.registry;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public final class OPTrades {

    public static class SingleInputTrade implements VillagerTrades.ItemListing {

        private final ItemStack sellingItem;
        private final int emeraldCount;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public SingleInputTrade(Block sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUses, xpValue);
        }

        public SingleInputTrade(Item sellingItem, int emeraldCount, int sellingItemCount, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, 12, xpValue);
        }

        public SingleInputTrade(Item sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUses, xpValue);
        }

        public SingleInputTrade(ItemStack sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(sellingItem, emeraldCount, sellingItemCount, maxUses, xpValue, 0.05F);
        }

        public SingleInputTrade(ItemStack sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingItem = sellingItem;
            this.emeraldCount = emeraldCount;
            this.sellingItemCount = sellingItemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public static class MultipleInputsTrade implements VillagerTrades.ItemListing {

        private final ItemStack inputItem;
        private final int inputCount;
        private final int emeraldCost;
        private final ItemStack ouputItem;
        private final int outputCount;
        private final int maxTrades;
        private final int experience;
        private final float priceMultiplier;

        public MultipleInputsTrade(ItemLike inputItem, int inputCount, int emeraldCost, Item ouputItem, int outputCount, int maxTrades, int experience) {
            this.inputItem = new ItemStack(inputItem);
            this.inputCount = inputCount;
            this.emeraldCost = emeraldCost;
            this.ouputItem = new ItemStack(ouputItem);
            this.outputCount = outputCount;
            this.maxTrades = maxTrades;
            this.experience = experience;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(Entity entity, RandomSource source) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.inputItem.getItem(), this.inputCount), new ItemStack(this.ouputItem.getItem(), this.outputCount), this.maxTrades, this.experience, this.priceMultiplier);
        }
    }

    public static class SellItemTrade implements VillagerTrades.ItemListing {
        private final Item tradeItem;
        private final int count;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public SellItemTrade(ItemLike item, int count, int maxUses, int xpValue) {
            this.tradeItem = item.asItem();
            this.count = count;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity entity, RandomSource source) {
            ItemStack stack = new ItemStack(this.tradeItem, 1);
            return new MerchantOffer(stack, new ItemStack(Items.EMERALD, this.count), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }
}
