package com.barl_inc.opposing_force.recipes;

import com.barl_inc.opposing_force.items.LaserBladeItem;
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

public class LaserBladeColoring extends CustomRecipe {

    public LaserBladeColoring(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, @NotNull Level level) {
        int laserBlades = 0;
        int dyes = 0;

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof LaserBladeItem) {
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
                if (item instanceof LaserBladeItem) {
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
        return OPRecipeSerializers.LASER_BLADE_COLORING.get();
    }

    public static Item getLaserBladeByColor(DyeColor dyeColor) {
        return switch (dyeColor) {
            case WHITE -> OPItems.WHITE_LASER_BLADE.get();
            case LIGHT_GRAY -> OPItems.LIGHT_GRAY_LASER_BLADE.get();
            case GRAY -> OPItems.GRAY_LASER_BLADE.get();
            case BLACK -> OPItems.BLACK_LASER_BLADE.get();
            case BROWN -> OPItems.BROWN_LASER_BLADE.get();
            case RED -> OPItems.RED_LASER_BLADE.get();
            case ORANGE -> OPItems.ORANGE_LASER_BLADE.get();
            case YELLOW -> OPItems.YELLOW_LASER_BLADE.get();
            case LIME -> OPItems.LIME_LASER_BLADE.get();
            case GREEN -> OPItems.GREEN_LASER_BLADE.get();
            case CYAN -> OPItems.CYAN_LASER_BLADE.get();
            case LIGHT_BLUE -> OPItems.LIGHT_BLUE_LASER_BLADE.get();
            case BLUE -> OPItems.BLUE_LASER_BLADE.get();
            case PURPLE -> OPItems.PURPLE_LASER_BLADE.get();
            case MAGENTA -> OPItems.MAGENTA_LASER_BLADE.get();
            case PINK -> OPItems.PINK_LASER_BLADE.get();
            default -> OPItems.LASER_BLADE.get();
        };
    }
}