package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.tags.OPItemTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

import static com.unusualmodding.opposing_force.registry.OPItems.*;
import static net.minecraft.data.recipes.RecipeCategory.*;

public class OPRecipeProvider extends RecipeProvider {

    public OPRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(COMBAT, BLASTER.get()).define('I', Tags.Items.INGOTS_IRON).define('R', Tags.Items.DUSTS_REDSTONE).define('D', DICER_LENS.get()).define('A', Tags.Items.GEMS_AMETHYST).pattern("RDA").pattern("I  ").unlockedBy("has_dicer_lens", has(DICER_LENS.get())).save(consumer);

        ShapedRecipeBuilder.shaped(COMBAT, TESLA_CANNON.get()).define('#', Tags.Items.INGOTS_GOLD).define('X', DEEP_SILK.get()).define('Y', Tags.Items.RODS_WOODEN).define('Z', Blocks.TRIPWIRE_HOOK).define('A', ELECTRIC_CHARGE.get()).pattern("#A#").pattern("XZX").pattern(" Y ").unlockedBy("has_electric_charge", has(ELECTRIC_CHARGE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, TOMAHAWK.get()).define('I', Tags.Items.INGOTS_IRON).define('W', Tags.Items.RODS_WOODEN).pattern("II").pattern("IW").unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, UMBER_DAGGER.get()).define('F', UMBER_FANG.get()).define('W', Tags.Items.RODS_WOODEN).pattern("F").pattern("W").unlockedBy("has_umber_fang", has(UMBER_FANG.get())).save(consumer);

        ShapedRecipeBuilder.shaped(COMBAT, DEEPWOVEN_HAT.get()).define('#', DEEP_SILK.get()).pattern("###").pattern("# #").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, DEEPWOVEN_TUNIC.get()).define('#', DEEP_SILK.get()).pattern("# #").pattern("###").pattern("###").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, DEEPWOVEN_PANTS.get()).define('#', DEEP_SILK.get()).pattern("###").pattern("# #").pattern("# #").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, DEEPWOVEN_BOOTS.get()).define('#', DEEP_SILK.get()).pattern("# #").pattern("# #").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);

        ShapedRecipeBuilder.shaped(COMBAT, WOODEN_MASK.get()).define('#', ItemTags.LOGS_THAT_BURN).pattern("###").pattern("# #").unlockedBy("has_burnable_wood", has(ItemTags.LOGS_THAT_BURN)).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, WOODEN_CHESTPLATE.get()).define('#', ItemTags.LOGS_THAT_BURN).pattern("# #").pattern("###").pattern("###").unlockedBy("has_burnable_wood", has(ItemTags.LOGS_THAT_BURN)).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, WOODEN_COVER.get()).define('#', ItemTags.LOGS_THAT_BURN).pattern("###").pattern("# #").pattern("# #").unlockedBy("has_burnable_wood", has(ItemTags.LOGS_THAT_BURN)).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, WOODEN_BOOTS.get()).define('#', ItemTags.LOGS_THAT_BURN).pattern("# #").pattern("# #").unlockedBy("has_burnable_wood", has(ItemTags.LOGS_THAT_BURN)).save(consumer);

        ShapedRecipeBuilder.shaped(COMBAT, STONE_HELMET.get()).define('#', ItemTags.STONE_TOOL_MATERIALS).pattern("###").pattern("# #").unlockedBy("has_cobbled_stone", has(ItemTags.STONE_TOOL_MATERIALS)).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, STONE_CHESTPLATE.get()).define('#', ItemTags.STONE_TOOL_MATERIALS).pattern("# #").pattern("###").pattern("###").unlockedBy("has_cobbled_stone", has(ItemTags.STONE_TOOL_MATERIALS)).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, STONE_LEGGINGS.get()).define('#', ItemTags.STONE_TOOL_MATERIALS).pattern("###").pattern("# #").pattern("# #").unlockedBy("has_cobbled_stone", has(ItemTags.STONE_TOOL_MATERIALS)).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, STONE_BOOTS.get()).define('#', ItemTags.STONE_TOOL_MATERIALS).pattern("# #").pattern("# #").unlockedBy("has_cobbled_stone", has(ItemTags.STONE_TOOL_MATERIALS)).save(consumer);

        ShapedRecipeBuilder.shaped(FOOD, Items.ENCHANTED_GOLDEN_APPLE).define('G', Tags.Items.STORAGE_BLOCKS_GOLD).define('A', Items.APPLE).pattern("GGG").pattern("GAG").pattern("GGG").unlockedBy("has_gold", has(Tags.Items.INGOTS_GOLD)).save(consumer);

        ShapedRecipeBuilder.shaped(FOOD, OPBlocks.INFERNO_PIE.get()).define('G', GUZZLER_SCALES.get()).define('F', FIRE_GEL.get()).define('S', Items.SUGAR).define('E', Tags.Items.EGGS).pattern("FFF").pattern("SES").pattern("GGG").unlockedBy("has_fire_gel", has(FIRE_GEL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(FOOD, OPBlocks.INFERNO_PIE.get()).define('I', INFERNO_PIE_SLICE.get()).pattern("II").pattern("II").unlockedBy("has_fire_gel", has(FIRE_GEL.get())).save(consumer, getSaveLocation("inferno_pie_from_slices"));

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.TREMBLING_BLOCK.get(), 16).define('T', OPBlocks.TREMBLER_SHELL.get()).pattern("TT").pattern("TT").unlockedBy("has_trembler_shell", has(OPBlocks.TREMBLER_SHELL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.TREMBLING_SHINGLES.get(), 4).define('T', OPBlocks.TREMBLING_BLOCK.get()).pattern("TT").pattern("TT").unlockedBy("has_trembler_shell", has(OPBlocks.TREMBLER_SHELL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.TREMBLING_SHINGLE_STAIRS.get(), 4).define('T', OPBlocks.TREMBLING_SHINGLES.get()).pattern("T  ").pattern("TT ").pattern("TTT").unlockedBy("has_trembler_shell", has(OPBlocks.TREMBLER_SHELL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.TREMBLING_SHINGLE_SLAB.get(), 3).define('T', OPBlocks.TREMBLING_SHINGLES.get()).pattern("TTT").unlockedBy("has_trembler_shell", has(OPBlocks.TREMBLER_SHELL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.DEEP_SILK_BLOCK.get(), 1).define('S', DEEP_SILK.get()).pattern("SSS").pattern("SSS").pattern("SSS").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(MISC, DEEP_SILK.get(), 9).requires(OPBlocks.DEEP_SILK_BLOCK.get()).unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_STONE.get(), 8).define('E', OPBlocks.SLUG_EGGS.get()).define('S', Blocks.STONE).pattern("SSS").pattern("SES").pattern("SSS").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_STONE_SLAB.get(), 3).define('V', OPBlocks.VILE_STONE.get()).pattern("VVV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_STONE_STAIRS.get(), 4).define('V', OPBlocks.VILE_STONE.get()).pattern("V  ").pattern("VV ").pattern("VVV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_COBBLESTONE.get(), 8).define('E', OPBlocks.SLUG_EGGS.get()).define('S', Blocks.COBBLESTONE).pattern("SSS").pattern("SES").pattern("SSS").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_COBBLESTONE_SLAB.get(), 3).define('V', OPBlocks.VILE_COBBLESTONE.get()).pattern("VVV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_COBBLESTONE_STAIRS.get(), 4).define('V', OPBlocks.VILE_COBBLESTONE.get()).pattern("V  ").pattern("VV ").pattern("VVV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_COBBLESTONE_WALL.get(), 6).define('V', OPBlocks.VILE_COBBLESTONE.get()).pattern("VVV").pattern("VVV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_STONE_BRICKS.get(), 4).define('V', OPBlocks.VILE_STONE.get()).pattern("VV").pattern("VV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_STONE_BRICK_SLAB.get(), 3).define('V', OPBlocks.VILE_STONE_BRICKS.get()).pattern("VVV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_STONE_BRICK_STAIRS.get(), 4).define('V', OPBlocks.VILE_STONE_BRICKS.get()).pattern("V  ").pattern("VV ").pattern("VVV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.VILE_STONE_BRICK_WALL.get(), 6).define('V', OPBlocks.VILE_STONE_BRICKS.get()).pattern("VVV").pattern("VVV").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, OPBlocks.CHISELED_VILE_STONE_BRICKS.get(), 2).define('V', OPBlocks.VILE_STONE_BRICK_SLAB.get()).pattern("V").pattern("V").unlockedBy("has_slug_eggs", has(OPBlocks.SLUG_EGGS.get())).save(consumer);

        stonecutting(OPBlocks.VILE_STONE.get(), OPBlocks.VILE_STONE_STAIRS.get(), 1, consumer);
        stonecutting(OPBlocks.VILE_STONE.get(), OPBlocks.VILE_STONE_SLAB.get(), 2, consumer);

        stonecutting(OPBlocks.VILE_COBBLESTONE.get(), OPBlocks.VILE_COBBLESTONE_STAIRS.get(), 1, consumer);
        stonecutting(OPBlocks.VILE_COBBLESTONE.get(), OPBlocks.VILE_COBBLESTONE_SLAB.get(), 2, consumer);
        stonecutting(OPBlocks.VILE_COBBLESTONE.get(), OPBlocks.VILE_COBBLESTONE_WALL.get(), 1, consumer);

        stonecutting(OPBlocks.VILE_STONE.get(), OPBlocks.VILE_STONE_BRICKS.get(), 1, consumer);
        stonecutting(OPBlocks.VILE_STONE.get(), OPBlocks.VILE_STONE_BRICK_STAIRS.get(), 1, consumer);
        stonecutting(OPBlocks.VILE_STONE.get(), OPBlocks.VILE_STONE_BRICK_SLAB.get(), 2, consumer);
        stonecutting(OPBlocks.VILE_STONE.get(), OPBlocks.VILE_STONE_BRICK_WALL.get(), 1, consumer);
        stonecutting(OPBlocks.VILE_STONE.get(), OPBlocks.CHISELED_VILE_STONE_BRICKS.get(), 1, consumer);
        stonecutting(OPBlocks.VILE_STONE_BRICKS.get(), OPBlocks.VILE_STONE_BRICK_STAIRS.get(), 1, consumer);
        stonecutting(OPBlocks.VILE_STONE_BRICKS.get(), OPBlocks.VILE_STONE_BRICK_SLAB.get(), 2, consumer);
        stonecutting(OPBlocks.VILE_STONE_BRICKS.get(), OPBlocks.VILE_STONE_BRICK_WALL.get(), 1, consumer);
        stonecutting(OPBlocks.VILE_STONE_BRICKS.get(), OPBlocks.CHISELED_VILE_STONE_BRICKS.get(), 1, consumer);
    }

    private static void conditionalRecipe(RecipeBuilder recipe, ICondition condition, Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath())).build(consumer, id);
    }

    private static void stonecutting(ItemLike ingredient, ItemLike result, int amount, Consumer<FinishedRecipe> consumer) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), BUILDING_BLOCKS, result, amount).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, getSaveLocation(getName(ingredient) + "_from_" + getName(result) + "_stonecutting"));
    }

    private static String getName(ItemLike object) {
        return ForgeRegistries.ITEMS.getKey(object.asItem()).getPath();
    }

    private static ResourceLocation getSaveLocation(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem());
    }

    private static ResourceLocation getSaveLocation(String name) {
        return OpposingForce.modPrefix(name);
    }
}

