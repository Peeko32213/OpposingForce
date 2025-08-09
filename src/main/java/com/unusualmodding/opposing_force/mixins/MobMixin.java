package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    protected MobMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo info) {
        if (random.nextFloat() < 0.5F) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack stack = this.getItemBySlot(slot);
                    Item item = opposingForce$replaceIronWithStone(stack);
                    if (item != null) {
                        this.setItemSlot(slot, new ItemStack(item));
                    }
                }
            }
        }
        if (random.nextFloat() < 0.5F) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack stack = this.getItemBySlot(slot);
                    Item item = opposingForce$replaceLeatherWithWood(stack);
                    if (item != null) {
                        this.setItemSlot(slot, new ItemStack(item));
                    }
                }
            }
        }
    }

    @Unique
    private Item opposingForce$replaceIronWithStone(ItemStack stack) {
        if (stack.is(Items.IRON_HELMET)) return OPItems.STONE_HELMET.get();
        if (stack.is(Items.IRON_CHESTPLATE)) return OPItems.STONE_CHESTPLATE.get();
        if (stack.is(Items.IRON_LEGGINGS)) return OPItems.STONE_LEGGINGS.get();
        if (stack.is(Items.IRON_BOOTS)) return OPItems.STONE_BOOTS.get();
        return null;
    }

    @Unique
    private Item opposingForce$replaceLeatherWithWood(ItemStack stack) {
        if (stack.is(Items.LEATHER_HELMET)) return OPItems.WOODEN_MASK.get();
        if (stack.is(Items.LEATHER_CHESTPLATE)) return OPItems.WOODEN_CHESTPLATE.get();
        if (stack.is(Items.LEATHER_LEGGINGS)) return OPItems.WOODEN_COVER.get();
        if (stack.is(Items.LEATHER_BOOTS)) return OPItems.WOODEN_BOOTS.get();
        return null;
    }
}
