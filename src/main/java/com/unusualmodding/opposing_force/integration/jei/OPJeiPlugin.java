package com.unusualmodding.opposing_force.integration.jei;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.recipes.BlasterColoring;
import com.unusualmodding.opposing_force.recipes.LaserBladeColoring;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.tags.OPItemTags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@JeiPlugin
public class OPJeiPlugin implements IModPlugin {

    private static List<Item> getLaserBlades() {
        return ForgeRegistries.ITEMS.tags().getTag(OPItemTags.LASER_BLADES).stream().filter(item -> item != OPItems.RAINBOW_LASER_BLADE.get()).toList();
    }

    private static List<Item> getBlasters() {
        return ForgeRegistries.ITEMS.tags().getTag(OPItemTags.BLASTERS).stream().toList();
    }

    private static final Map<DyeColor, List<Item>> DYES_BY_COLOR = BuiltInRegistries.ITEM.stream()
        .filter(item -> item instanceof DyeItem)
        .map(item -> (DyeItem) item)
        .collect(Collectors.groupingBy(
            DyeItem::getDyeColor,
            () -> new EnumMap<>(DyeColor.class),
            Collectors.mapping(dyeItem -> (Item) dyeItem, Collectors.toList())
        ));

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        RecipeManager recipeManager = mc.level.getRecipeManager();

        List<CraftingRecipe> extraRecipes = new ArrayList<>();

        extraRecipes.addAll(generateDyeRecipes(recipeManager, LaserBladeColoring.class, LaserBladeColoring::getLaserBladeByColor, getLaserBlades()));

        extraRecipes.addAll(generateDyeRecipes(recipeManager, BlasterColoring.class, BlasterColoring::getLaserBladeByColor, getBlasters()));

        registration.addRecipes(RecipeTypes.CRAFTING, extraRecipes);
    }

    private List<CraftingRecipe> generateDyeRecipes(RecipeManager recipeManager, Class<? extends CustomRecipe> recipeClass, Function<DyeColor, Item> resultResolver, List<Item> baseItems) {
        boolean hasRecipe = recipeManager.getAllRecipesFor(RecipeType.CRAFTING).stream().anyMatch(recipeClass::isInstance);

        if (!hasRecipe) return List.of();

        List<CraftingRecipe> extraRecipes = new ArrayList<>();

        for (DyeColor dyeColor : DyeColor.values()) {
            Item resultItem = resultResolver.apply(dyeColor);
            List<Item> dyeItems = DYES_BY_COLOR.get(dyeColor);

            if (resultItem == null || dyeItems == null || dyeItems.isEmpty()) continue;

            List<Item> items = baseItems.stream().filter(item -> item != resultItem).toList();

            if (items.isEmpty()) continue;

            Ingredient ingredient = Ingredient.of(items.stream().map(ItemStack::new).toArray(ItemStack[]::new));
            Ingredient dyeChoices = Ingredient.of(dyeItems.stream().map(ItemStack::new).toArray(ItemStack[]::new));

            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, ingredient, dyeChoices);
            ItemStack output = new ItemStack(resultItem);

            ResourceLocation baseId = ForgeRegistries.ITEMS.getKey(resultItem);
            ResourceLocation displayId = new ResourceLocation(baseId.getNamespace(), baseId.getPath());

            ShapelessRecipe recipe = new ShapelessRecipe(displayId, "", CraftingBookCategory.MISC, output, inputs);

            extraRecipes.add(recipe);
        }
        return extraRecipes;
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return OpposingForce.modPrefix("jei_plugin");
    }
}