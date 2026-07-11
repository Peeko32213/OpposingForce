package com.barl_inc.opposing_force.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.barl_inc.opposing_force.registry.OPItems;
import com.barl_inc.opposing_force.registry.OPRecipeSerializers;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MobHeadFireworkStarRecipe extends CustomRecipe {

    private static final Ingredient SHAPE_INGREDIENT = Ingredient.of(
            OPItems.DICER_HEAD.get(),
            OPItems.FROWZY_HEAD.get(),
            OPItems.ANGRY_RAMBLER_SKULL.get(),
            OPItems.CLASSIC_RAMBLER_SKULL.get(),
            OPItems.EVIL_RAMBLER_SKULL.get(),
            OPItems.GRINNING_RAMBLER_SKULL.get(),
            OPItems.SKELETAL_RAMBLER_SKULL.get(),
            OPItems.SMILING_RAMBLER_SKULL.get(),
            OPItems.STRANGE_RAMBLER_SKULL.get(),
            OPItems.CRUNDLY_RAMBLER_SKULL.get(),
            OPItems.DWARVEN_RAMBLER_SKULL.get(),
            OPItems.IMPRISONED_RAMBLER_SKULL.get(),
            OPItems.INDOMITABLE_RAMBLER_SKULL.get(),
            OPItems.LEERING_RAMBLER_SKULL.get(),
            OPItems.MAGMATIC_RAMBLER_SKULL.get(),
            OPItems.MUSICAL_RAMBLER_SKULL.get(),
            OPItems.NOSY_RAMBLER_SKULL.get(),
            OPItems.VALIANT_RAMBLER_SKULL.get(),
            OPItems.SKYVERN_HEAD.get(),
            OPItems.TART_HEAD.get()
    );

    private static final java.util.Map<Item, FireworkRocketItem.Shape> SHAPE_BY_ITEM = Util.make(Maps.newHashMap(), (map) -> {
        map.put(OPItems.DICER_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.FROWZY_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.ANGRY_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.CLASSIC_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.EVIL_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.GRINNING_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.SKELETAL_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.SMILING_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.STRANGE_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.CRUNDLY_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.DWARVEN_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.IMPRISONED_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.INDOMITABLE_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.LEERING_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.MAGMATIC_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.MUSICAL_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.NOSY_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.VALIANT_RAMBLER_SKULL.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.SKYVERN_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
        map.put(OPItems.TART_HEAD.get(), FireworkRocketItem.Shape.CREEPER);
    });

    private static final Ingredient TRAIL_INGREDIENT = Ingredient.of(Items.DIAMOND);
    private static final Ingredient FLICKER_INGREDIENT = Ingredient.of(Items.GLOWSTONE_DUST);
    private static final Ingredient GUNPOWDER_INGREDIENT = Ingredient.of(Items.GUNPOWDER);

    public MobHeadFireworkStarRecipe(ResourceLocation name, CraftingBookCategory category) {
        super(name, category);
    }

    @Override
    public boolean matches(CraftingContainer container, @NotNull Level level) {
        boolean gunpowder = false;
        boolean dye = false;
        boolean shape = false;
        boolean trail = false;
        boolean flicker = false;

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                if (SHAPE_INGREDIENT.test(itemstack)) {
                    if (shape) {
                        return false;
                    }

                    shape = true;
                } else if (FLICKER_INGREDIENT.test(itemstack)) {
                    if (flicker) {
                        return false;
                    }
                    flicker = true;
                } else if (TRAIL_INGREDIENT.test(itemstack)) {
                    if (trail) {
                        return false;
                    }
                    trail = true;
                } else if (GUNPOWDER_INGREDIENT.test(itemstack)) {
                    if (gunpowder) {
                        return false;
                    }
                    gunpowder = true;
                } else {
                    if (!(itemstack.getItem() instanceof DyeItem)) {
                        return false;
                    }
                    dye = true;
                }
            }
        }
        return gunpowder && dye;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container, @NotNull RegistryAccess registryAccess) {
        ItemStack itemstack = new ItemStack(Items.FIREWORK_STAR);
        CompoundTag compoundtag = itemstack.getOrCreateTagElement("Explosion");
        FireworkRocketItem.Shape fireworkrocketitem$shape = FireworkRocketItem.Shape.SMALL_BALL;
        List<Integer> list = Lists.newArrayList();

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack1 = container.getItem(i);
            if (!itemstack1.isEmpty()) {
                if (SHAPE_INGREDIENT.test(itemstack1)) {
                    FireworkRocketItem.Shape shape = SHAPE_BY_ITEM.get(itemstack1.getItem());
                    if (shape != null) fireworkrocketitem$shape = shape;
                } else if (FLICKER_INGREDIENT.test(itemstack1)) {
                    compoundtag.putBoolean("Flicker", true);
                } else if (TRAIL_INGREDIENT.test(itemstack1)) {
                    compoundtag.putBoolean("Trail", true);
                } else if (itemstack1.getItem() instanceof DyeItem) {
                    list.add(((DyeItem)itemstack1.getItem()).getDyeColor().getFireworkColor());
                }
            }
        }

        compoundtag.putIntArray("Colors", list);
        fireworkrocketitem$shape.save(compoundtag);
        return itemstack;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return new ItemStack(Items.FIREWORK_STAR);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return OPRecipeSerializers.MOB_HEAD_FIREWORK_STAR.get();
    }
}
