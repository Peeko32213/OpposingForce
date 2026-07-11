package com.barl_inc.opposing_force.recipes;

import com.barl_inc.opposing_force.items.BlasterItem;
import com.barl_inc.opposing_force.registry.OPItems;
import com.barl_inc.opposing_force.registry.OPRecipeSerializers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BlasterColoring extends CustomRecipe {

    public BlasterColoring(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, @NotNull Level level) {
        int laserBlades = 0;
        int dyes = 0;

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof BlasterItem) {
                    laserBlades++;
                } else if (stack.getItem() instanceof DyeItem) {
                    dyes++;
                } else {
                    return false;
                }
                if (dyes > 1 || laserBlades > 1) {
                    return false;
                }
            }
        }

        return laserBlades == 1 && dyes == 1;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container, @NotNull RegistryAccess registryAccess) {
        ItemStack empty = ItemStack.EMPTY;
        DyeItem dye = (DyeItem) Items.WHITE_DYE;

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);

            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item instanceof BlasterItem) {
                    empty = stack;
                } else if (item instanceof DyeItem) {
                    dye = (DyeItem) item;
                }
            }
        }

        ItemStack result = getLaserBladeByColor(dye.getDyeColor()).getDefaultInstance();
        if (
            empty.hasTag()) {
            result.setTag(empty.getTag().copy());
        }

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return OPRecipeSerializers.BLASTER_COLORING.get();
    }

    public static Item getLaserBladeByColor(DyeColor dyeColor) {
        return switch (dyeColor) {
            case WHITE -> OPItems.WHITE_BLASTER.get();
            case LIGHT_GRAY -> OPItems.LIGHT_GRAY_BLASTER.get();
            case GRAY -> OPItems.GRAY_BLASTER.get();
            case BLACK -> OPItems.BLACK_BLASTER.get();
            case BROWN -> OPItems.BROWN_BLASTER.get();
            case RED -> OPItems.RED_BLASTER.get();
            case ORANGE -> OPItems.ORANGE_BLASTER.get();
            case YELLOW -> OPItems.YELLOW_BLASTER.get();
            case LIME -> OPItems.LIME_BLASTER.get();
            case GREEN -> OPItems.GREEN_BLASTER.get();
            case CYAN -> OPItems.CYAN_BLASTER.get();
            case LIGHT_BLUE -> OPItems.LIGHT_BLUE_BLASTER.get();
            case BLUE -> OPItems.BLUE_BLASTER.get();
            case PURPLE -> OPItems.PURPLE_BLASTER.get();
            case MAGENTA -> OPItems.MAGENTA_BLASTER.get();
            case PINK -> OPItems.PINK_BLASTER.get();
            default -> OPItems.BLASTER.get();
        };
    }
}