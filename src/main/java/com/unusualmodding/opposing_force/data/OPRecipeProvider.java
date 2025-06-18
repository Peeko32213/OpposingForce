package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

import static com.unusualmodding.opposing_force.registry.OPItems.*;
import static net.minecraft.data.recipes.RecipeCategory.COMBAT;
import static net.minecraft.data.recipes.RecipeCategory.MISC;

public class OPRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public OPRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(MISC, Items.GUNPOWDER).requires(OPBlocks.POWDER_GNOME.get()).unlockedBy("has_powder_gnome", has(OPBlocks.POWDER_GNOME.get())).save(consumer);

        ShapedRecipeBuilder.shaped(COMBAT, TESLA_BOW.get()).define('#', Tags.Items.INGOTS_GOLD).define('X', DEEP_SILK.get()).define('Y', Tags.Items.RODS_WOODEN).define('Z', Blocks.TRIPWIRE_HOOK).define('A', ELECTRIC_CHARGE.get()).pattern("#A#").pattern("XZX").pattern(" Y ").unlockedBy("has_electric_charge", has(ELECTRIC_CHARGE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, TOMAHAWK.get(), 4).define('#', Tags.Items.INGOTS_IRON).define('X', Tags.Items.NUGGETS_IRON).define('Y', Tags.Items.RODS_WOODEN).pattern("##").pattern("XY").unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).save(consumer);

        ShapedRecipeBuilder.shaped(COMBAT, DEEPWOVEN_HAT.get()).define('#', DEEP_SILK.get()).pattern("###").pattern("# #").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, DEEPWOVEN_TUNIC.get()).define('#', DEEP_SILK.get()).pattern("# #").pattern("###").pattern("###").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, DEEPWOVEN_PANTS.get()).define('#', DEEP_SILK.get()).pattern("###").pattern("# #").pattern("# #").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(COMBAT, DEEPWOVEN_BOOTS.get()).define('#', DEEP_SILK.get()).pattern("# #").pattern("# #").unlockedBy("has_deep_silk", has(DEEP_SILK.get())).save(consumer);

    }

    private void wrap(RecipeBuilder builder, String name, Consumer<FinishedRecipe> consumer, ICondition... conds) {
        wrap(builder, OpposingForce.MOD_ID, name, consumer, conds);
    }

    private void wrap(RecipeBuilder builder, String modid, String name, Consumer<FinishedRecipe> consumer, ICondition... conds) {
        ResourceLocation loc = new ResourceLocation(modid, name);
        ConditionalRecipe.Builder cond;
        if (conds.length > 1) {
            cond = ConditionalRecipe.builder().addCondition(and(conds));
        } else if (conds.length == 1) {
            cond = ConditionalRecipe.builder().addCondition(conds[0]);
        } else {
            cond = ConditionalRecipe.builder();
        }
        FinishedRecipe[] recipe = new FinishedRecipe[1];
        builder.save(f -> recipe[0] = f, loc);
        cond.addRecipe(recipe[0]).generateAdvancement().build(consumer, loc);
    }
}

