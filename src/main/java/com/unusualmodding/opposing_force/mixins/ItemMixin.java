package com.unusualmodding.opposing_force.mixins;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

@Mixin(Item.class)
public abstract class ItemMixin {

//    @Shadow
//    @Final
//    @Nullable
//    private FoodProperties foodProperties;
//
//    @Inject(method = "isEdible",at = @At("RETURN"),cancellable = true)
//    public void edible(CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(true);
//    }
//
//    @Inject(method = "getFoodProperties",at = @At("RETURN"),cancellable = true)
//    public void fakeProperties(CallbackInfoReturnable<FoodProperties> cir) {
//        if((this.foodProperties==null)){
//            FoodProperties.Builder builder = new FoodProperties.Builder().nutrition(1).saturationMod(1);
//            cir.setReturnValue(builder.build());
//        }
//    }
}
