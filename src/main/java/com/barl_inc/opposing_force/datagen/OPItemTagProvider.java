package com.barl_inc.opposing_force.datagen;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.registry.OPBlocks;
import com.barl_inc.opposing_force.registry.tags.OPItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barl_inc.opposing_force.registry.OPItems.*;

public class OPItemTagProvider extends ItemTagsProvider {

    public OPItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(output, provider, lookup, OpposingForce.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(OPItemTags.KNIVES).add(
                UMBER_DAGGER.get()
        ).addOptionalTag(new ResourceLocation("farmersdelight", "tools/knives"));

        this.tag(OPItemTags.RAMBLER_SKULLS).add(
                ANGRY_RAMBLER_SKULL.get(),
                CLASSIC_RAMBLER_SKULL.get(),
                EVIL_RAMBLER_SKULL.get(),
                GRINNING_RAMBLER_SKULL.get(),
                SKELETAL_RAMBLER_SKULL.get(),
                SMILING_RAMBLER_SKULL.get(),
                STRANGE_RAMBLER_SKULL.get(),
                CRUNDLY_RAMBLER_SKULL.get(),
                DWARVEN_RAMBLER_SKULL.get(),
                IMPRISONED_RAMBLER_SKULL.get(),
                INDOMITABLE_RAMBLER_SKULL.get(),
                LEERING_RAMBLER_SKULL.get(),
                MAGMATIC_RAMBLER_SKULL.get(),
                MUSICAL_RAMBLER_SKULL.get(),
                NOSY_RAMBLER_SKULL.get(),
                VALIANT_RAMBLER_SKULL.get()
        );

        this.tag(OPItemTags.PREVENT_CAPE_RENDERING).add(
                RECON_KNIGHT_CHESTPLATE.get()
        );

        this.tag(OPItemTags.LASER_BLADES).add(
                LASER_BLADE.get(),
                WHITE_LASER_BLADE.get(),
                LIGHT_GRAY_LASER_BLADE.get(),
                GRAY_LASER_BLADE.get(),
                BLACK_LASER_BLADE.get(),
                BROWN_LASER_BLADE.get(),
                RED_LASER_BLADE.get(),
                ORANGE_LASER_BLADE.get(),
                YELLOW_LASER_BLADE.get(),
                LIME_LASER_BLADE.get(),
                GREEN_LASER_BLADE.get(),
                CYAN_LASER_BLADE.get(),
                LIGHT_BLUE_LASER_BLADE.get(),
                BLUE_LASER_BLADE.get(),
                PURPLE_LASER_BLADE.get(),
                MAGENTA_LASER_BLADE.get(),
                PINK_LASER_BLADE.get(),
                RAINBOW_LASER_BLADE.get()
        );

        this.tag(OPItemTags.BLASTERS).add(
                BLASTER.get(),
                WHITE_BLASTER.get(),
                LIGHT_GRAY_BLASTER.get(),
                GRAY_BLASTER.get(),
                BLACK_BLASTER.get(),
                BROWN_BLASTER.get(),
                RED_BLASTER.get(),
                ORANGE_BLASTER.get(),
                YELLOW_BLASTER.get(),
                LIME_BLASTER.get(),
                GREEN_BLASTER.get(),
                CYAN_BLASTER.get(),
                LIGHT_BLUE_BLASTER.get(),
                BLUE_BLASTER.get(),
                PURPLE_BLASTER.get(),
                MAGENTA_BLASTER.get(),
                PINK_BLASTER.get()
        );

        this.tag(OPItemTags.BLASTER_AMMO).add(
                Items.REDSTONE
        );

        this.tag(OPItemTags.BERRIES).add(
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(OPItemTags.FRUITS).add(Items.APPLE, Items.MELON, Items.CHORUS_FRUIT).addTag(OPItemTags.BERRIES);

        this.tag(OPItemTags.VEGETABLES).add(
                Items.CARROT,
                Items.POTATO,
                Items.BEETROOT
        );

        this.tag(OPItemTags.RAW_VEGETABLES).addTag(OPItemTags.FRUITS).addTag(OPItemTags.VEGETABLES);

        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

        this.tag(ItemTags.MUSIC_DISCS).add(
                WALTZ_OF_THE_SLUG_DISC.get(),
                SLAYSER_DISC.get()
        );

        this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(
                DEEPWOVEN_HAT.get(),
                DEEPWOVEN_TUNIC.get(),
                DEEPWOVEN_PANTS.get(),
                DEEPWOVEN_BOOTS.get()
        );

        this.tag(ItemTags.SWORDS).add(
                BONE_SWORD.get(),
                EMERALD_SWORD.get(),
                LAPIS_SWORD.get()
        );

        this.tag(ItemTags.PICKAXES).add(
                BONE_PICKAXE.get(),
                EMERALD_PICKAXE.get(),
                LAPIS_PICKAXE.get()
        );

        this.tag(ItemTags.AXES).add(
                BONE_AXE.get(),
                EMERALD_AXE.get(),
                LAPIS_AXE.get()
        );

        this.tag(ItemTags.SHOVELS).add(
                BONE_SHOVEL.get(),
                EMERALD_SHOVEL.get(),
                LAPIS_SHOVEL.get()
        );

        this.tag(ItemTags.HOES).add(
                BONE_HOE.get(),
                EMERALD_HOE.get(),
                LAPIS_HOE.get()
        );

        this.tag(Tags.Items.ARMORS).add(
                BONE_HELMET.get(),
                BONE_CHESTPLATE.get(),
                BONE_LEGGINGS.get(),
                BONE_BOOTS.get(),
                DEEPWOVEN_HAT.get(),
                DEEPWOVEN_TUNIC.get(),
                DEEPWOVEN_PANTS.get(),
                DEEPWOVEN_BOOTS.get(),
                EMERALD_MASK.get(),
                EMERALD_CHESTPLATE.get(),
                EMERALD_LEGGINGS.get(),
                EMERALD_BOOTS.get(),
                MOON_SHOES.get(),
                RECON_KNIGHT_HELMET.get(),
                RECON_KNIGHT_CHESTPLATE.get(),
                RECON_KNIGHT_LEGGINGS.get(),
                RECON_KNIGHT_BOOTS.get(),
                SLUG_BARON_HELMET.get(),
                SLUG_BARON_CHESTPLATE.get(),
                SLUG_BARON_LEGGINGS.get(),
                SLUG_BARON_BOOTS.get(),
                STONE_HELMET.get(),
                STONE_CHESTPLATE.get(),
                STONE_LEGGINGS.get(),
                STONE_BOOTS.get(),
                WOODEN_MASK.get(),
                WOODEN_CHESTPLATE.get(),
                WOODEN_COVER.get(),
                WOODEN_BOOTS.get()
        );

        this.tag(Tags.Items.ARMORS_HELMETS).add(
                BONE_HELMET.get(),
                DEEPWOVEN_HAT.get(),
                EMERALD_MASK.get(),
                RECON_KNIGHT_HELMET.get(),
                SLUG_BARON_HELMET.get(),
                STONE_HELMET.get(),
                WOODEN_MASK.get()
        );

        this.tag(Tags.Items.ARMORS_CHESTPLATES).add(
                BONE_CHESTPLATE.get(),
                DEEPWOVEN_TUNIC.get(),
                EMERALD_CHESTPLATE.get(),
                RECON_KNIGHT_CHESTPLATE.get(),
                SLUG_BARON_CHESTPLATE.get(),
                STONE_CHESTPLATE.get(),
                WOODEN_CHESTPLATE.get()
        );

        this.tag(Tags.Items.ARMORS_LEGGINGS).add(
                BONE_LEGGINGS.get(),
                DEEPWOVEN_PANTS.get(),
                EMERALD_LEGGINGS.get(),
                RECON_KNIGHT_LEGGINGS.get(),
                SLUG_BARON_LEGGINGS.get(),
                STONE_LEGGINGS.get(),
                WOODEN_COVER.get()
        );

        this.tag(Tags.Items.ARMORS_BOOTS).add(
                BONE_BOOTS.get(),
                DEEPWOVEN_BOOTS.get(),
                EMERALD_BOOTS.get(),
                MOON_SHOES.get(),
                RECON_KNIGHT_BOOTS.get(),
                SLUG_BARON_BOOTS.get(),
                STONE_BOOTS.get(),
                WOODEN_BOOTS.get()
        );

        this.tag(Tags.Items.STRING).add(DEEP_SILK.get());

        this.tag(Tags.Items.EGGS).add(OPBlocks.SLUG_EGGS.get().asItem());
    }
}
