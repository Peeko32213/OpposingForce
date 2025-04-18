package com.unusualmodding.opposingforce.core.datagen.server;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.core.datagen.util.OPRecipeProvider;
import com.unusualmodding.opposingforce.core.registry.OPBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;


public class RecipeGenerator extends OPRecipeProvider implements IConditionBuilder {
    public RecipeGenerator(PackOutput pGenerator) {
        super(pGenerator);
    }

    public static final int FAST_COOKING = 100;        // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;        // 20 seconds

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        makeDye(consumer, Items.PINK_DYE, OPBlocks.PRINCESS_JELLY.get());
        makeDye(consumer, Items.YELLOW_DYE, OPBlocks.CHICKEN_OF_THE_CAVES.get());
        makeDye(consumer, Items.BLUE_DYE, OPBlocks.BLUE_TRUMPET.get());

    }


    private void smeltingRecipes(Consumer<FinishedRecipe> consumer) {

    }

    //Wrappers for conditionals
    private void wrap(RecipeBuilder builder, String name, Consumer<FinishedRecipe> consumer, ICondition... conds) {
        wrap(builder, OpposingForce.MODID, name, consumer, conds);
    }

    protected static String getEntityName(EntityType<?> pItemLike) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(pItemLike).getPath();
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(OpposingForce.MODID, name);
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
        cond.addRecipe(recipe[0])
                .generateAdvancement()
                .build(consumer, loc);
    }


}

