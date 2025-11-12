package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class OPAdvancementProvider implements AdvancementGenerator {

    public static ForgeAdvancementProvider register(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        return new ForgeAdvancementProvider(output, provider, helper, List.of(new OPAdvancementProvider()));
    }

    @SuppressWarnings("unused")
    @Override
    public void generate(@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper helper) {

        Advancement root = Advancement.Builder.advancement()
                .display(Items.STONE_SWORD,
                        Component.translatable("advancement.opposing_force.root"),
                        Component.translatable("advancement.opposing_force.root.desc"),
                        new ResourceLocation("textures/block/gray_wool.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("root", KilledTrigger.TriggerInstance.playerKilledEntity())
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "root"), helper);

        Advancement miscRoot = Advancement.Builder.advancement()
                .display(Blocks.STONE,
                        Component.translatable("advancement.opposing_force.misc_root"),
                        Component.translatable("advancement.opposing_force.misc_root.desc"), null,
                        FrameType.TASK, false, false, false)
                .addCriterion("misc_root", KilledTrigger.TriggerInstance.playerKilledEntity())
                .parent(root)
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "misc_root"), helper);

        Advancement igniteFireSlime = Advancement.Builder.advancement()
                .parent(miscRoot)
                .display(Items.BLAZE_POWDER,
                        Component.translatable("advancement.opposing_force.ignite_fire_slime"),
                        Component.translatable("advancement.opposing_force.ignite_fire_slime.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("ignite_fire_slime", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(Items.BLAZE_POWDER),
                        EntityPredicate.wrap(EntityPredicate.Builder.entity().of(OPEntities.FIRE_SLIME.get()).build())))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "ignite_fire_slime"), helper);

        Advancement captureWhizz = Advancement.Builder.advancement()
                .parent(miscRoot)
                .display(OPItems.CAPTURED_WHIZZ.get(),
                        Component.translatable("advancement.opposing_force.capture_whizz"),
                        Component.translatable("advancement.opposing_force.capture_whizz.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("capture_whizz", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.CAPTURED_WHIZZ.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "capture_whizz"), helper);

        Advancement deepWovenArmor = Advancement.Builder.advancement()
                .parent(root)
                .display(OPItems.DEEPWOVEN_HAT.get(),
                        Component.translatable("advancement.opposing_force.deepwoven_armor"),
                        Component.translatable("advancement.opposing_force.deepwoven_armor.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR)
                    .addCriterion("deepwoven_hat", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.DEEPWOVEN_HAT.get()))
                    .addCriterion("deepwoven_tunic", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.DEEPWOVEN_TUNIC.get()))
                    .addCriterion("deepwoven_pants", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.DEEPWOVEN_PANTS.get()))
                    .addCriterion("deepwoven_boots", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.DEEPWOVEN_BOOTS.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "deepwoven_armor"), helper);

        Advancement electricCharge = Advancement.Builder.advancement()
                .parent(root)
                .display(OPItems.ELECTRIC_CHARGE.get(),
                        Component.translatable("advancement.opposing_force.electric_charge"),
                        Component.translatable("advancement.opposing_force.electric_charge.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("electric_charge", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.ELECTRIC_CHARGE.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "electric_charge"), helper);

        Advancement teslaIngot = Advancement.Builder.advancement()
                .parent(electricCharge)
                .display(OPItems.ELECTRIC_ALLOY.get(),
                        Component.translatable("advancement.opposing_force.tesla_ingot"),
                        Component.translatable("advancement.opposing_force.tesla_ingot.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("tesla_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.ELECTRIC_ALLOY.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "tesla_ingot"), helper);

        Advancement moonShoes = Advancement.Builder.advancement()
                .parent(miscRoot)
                .display(OPItems.MOON_SHOES.get(),
                        Component.translatable("advancement.opposing_force.moon_shoes"),
                        Component.translatable("advancement.opposing_force.moon_shoes.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("moon_shoes", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.MOON_SHOES.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "moon_shoes"), helper);

        Advancement woodenArmor = Advancement.Builder.advancement()
                .parent(root)
                .display(OPItems.WOODEN_MASK.get(),
                        Component.translatable("advancement.opposing_force.wooden_armor"),
                        Component.translatable("advancement.opposing_force.wooden_armor.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("wooden_mask", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.WOODEN_MASK.get()))
                .addCriterion("wooden_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.WOODEN_CHESTPLATE.get()))
                .addCriterion("wooden_cover", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.WOODEN_COVER.get()))
                .addCriterion("wooden_boots", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.WOODEN_BOOTS.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "wooden_armor"), helper);

        Advancement stoneArmor = Advancement.Builder.advancement()
                .parent(root)
                .display(OPItems.STONE_HELMET.get(),
                        Component.translatable("advancement.opposing_force.stone_armor"),
                        Component.translatable("advancement.opposing_force.stone_armor.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("stone_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.STONE_HELMET.get()))
                .addCriterion("stone_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.STONE_CHESTPLATE.get()))
                .addCriterion("stone_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.STONE_LEGGINGS.get()))
                .addCriterion("stone_boots", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.STONE_BOOTS.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "stone_armor"), helper);

        Advancement slugArmor = Advancement.Builder.advancement()
                .parent(new ResourceLocation(OpposingForce.MOD_ID, "grow_slug"))
                .display(OPItems.SLUG_BARON_HELMET.get(),
                        Component.translatable("advancement.opposing_force.slug_baron_armor"),
                        Component.translatable("advancement.opposing_force.slug_baron_armor.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .requirements(RequirementsStrategy.OR)
                .addCriterion("slug_baron_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.SLUG_BARON_HELMET.get()))
                .addCriterion("slug_baron_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.SLUG_BARON_CHESTPLATE.get()))
                .addCriterion("slug_baron_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.SLUG_BARON_LEGGINGS.get()))
                .addCriterion("slug_baron_boots", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.SLUG_BARON_BOOTS.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "slug_baron_armor"), helper);

        /*Advancement tremblerShell = Advancement.Builder.advancement()
                .parent(root)
                .display(OPBlocks.TREMBLER_SHELL.get(),
                        Component.translatable("advancement.opposing_force.trembler_shell"),
                        Component.translatable("advancement.opposing_force.trembler_shell.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("trembler_shell", InventoryChangeTrigger.TriggerInstance.hasItems(OPBlocks.TREMBLER_SHELL.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "trembler_shell"), helper); */

        /*Advancement dicerLens = Advancement.Builder.advancement()
                .parent(root)
                .display(OPItems.DICER_LENS.get(),
                        Component.translatable("advancement.opposing_force.dicer_lens"),
                        Component.translatable("advancement.opposing_force.dicer_lens.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("has_dicer_lens", InventoryChangeTrigger.TriggerInstance.hasItems(OPItems.DICER_LENS.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "has_dicer_lens"), helper);*/


    }
}
