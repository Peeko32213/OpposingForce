package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

public class OPRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public OPRecipeProvider(PackOutput pGenerator) {
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
        wrap(builder, OpposingForce.MOD_ID, name, consumer, conds);
    }

    protected static String getEntityName(EntityType<?> pItemLike) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(pItemLike).getPath();
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(OpposingForce.MOD_ID, name);
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


    public void makeDye(Consumer<FinishedRecipe> c, Item plankOut, Block logIn) {

        ResourceLocation rl = key(plankOut);
        ResourceLocation rl2 = key(logIn);

        String name = rl.getPath() + "_from_" + rl2.getPath();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, plankOut, 4)
                .requires(logIn)
                .unlockedBy("has_flower", has(logIn))
                .save(c, modPrefix(name));
    }

    public ResourceLocation key(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
    public ResourceLocation key(Block item) {
        return BuiltInRegistries.BLOCK.getKey(item);
    }
}

