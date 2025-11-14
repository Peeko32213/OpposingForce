package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.data.MobHeadFireworkStarRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OPRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, OpposingForce.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> MOB_HEAD_FIREWORK_STAR = RECIPE_SERIALIZERS.register("opposing_force_mob_head_firework_star.json", () -> new SimpleCraftingRecipeSerializer<>(MobHeadFireworkStarRecipe::new));
}
