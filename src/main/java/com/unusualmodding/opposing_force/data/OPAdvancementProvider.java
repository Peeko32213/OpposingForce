package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
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
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class OPAdvancementProvider implements AdvancementGenerator {

    public static ForgeAdvancementProvider register(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        return new ForgeAdvancementProvider(output, provider, helper, List.of(new OPAdvancementProvider()));
    }

    @SuppressWarnings("unused")
    @Override
    public void generate(Provider provider, Consumer<Advancement> consumer, ExistingFileHelper helper) {

        Advancement root = Advancement.Builder.advancement()
                .display(OPBlocks.CAP_OF_EYE.get(),
                        Component.translatable("advancement.opposing_force.root"),
                        Component.translatable("advancement.opposing_force.root.desc"),
                        new ResourceLocation("textures/block/deepslate.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("root", KilledTrigger.TriggerInstance.playerKilledEntity())
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "root"), helper);

        Advancement igniteFireSlime = Advancement.Builder.advancement()
                .parent(root)
                .display(Items.MAGMA_CREAM,
                        Component.translatable("advancement.opposing_force.ignite_fire_slime"),
                        Component.translatable("advancement.opposing_force.ignite_fire_slime.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("ignite_fire_slime", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(Items.MAGMA_CREAM),
                        EntityPredicate.wrap(EntityPredicate.Builder.entity().of(OPEntities.FIRE_SLIME.get()).build())))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "ignite_fire_slime"), helper);

        Advancement tameSlug = Advancement.Builder.advancement()
                .parent(root)
                .display(OPItems.SLUG_EGGS.get(),
                        Component.translatable("advancement.opposing_force.tame_slug"),
                        Component.translatable("advancement.opposing_force.tame_slug.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("tame_slug", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(OPEntities.SLUG.get()).build()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "tame_slug"), helper);

        Advancement growSlug = Advancement.Builder.advancement()
                .parent(tameSlug)
                .display(Items.SLIME_BLOCK,
                        Component.translatable("advancement.opposing_force.grow_slug"),
                        Component.translatable("advancement.opposing_force.grow_slug.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("grow_slug", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                        ItemPredicate.Builder.item().of(Items.SLIME_BLOCK),
                        EntityPredicate.wrap(EntityPredicate.Builder.entity().of(OPEntities.SLUG.get()).build())))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "grow_slug"), helper);

        Advancement captureWhizz = Advancement.Builder.advancement()
                .parent(root)
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

        Advancement tremblerShell = Advancement.Builder.advancement()
                .parent(root)
                .display(OPBlocks.TREMBLER_SHELL.get(),
                        Component.translatable("advancement.opposing_force.trembler_shell"),
                        Component.translatable("advancement.opposing_force.trembler_shell.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("trembler_shell", InventoryChangeTrigger.TriggerInstance.hasItems(OPBlocks.TREMBLER_SHELL.get()))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "trembler_shell"), helper);
    }
}
