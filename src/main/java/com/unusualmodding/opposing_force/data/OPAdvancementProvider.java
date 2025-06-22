package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
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

        Advancement defeatDicer = Advancement.Builder.advancement()
                .parent(root)
                .display(OPItems.DICER_SPAWN_EGG.get(),
                        Component.translatable("advancement.opposing_force.defeat_dicer"),
                        Component.translatable("advancement.opposing_force.defeat_dicer.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("defeat_dicer", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(OPEntities.DICER.get())))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "defeat_dicer"), helper);

        Advancement defeatFrowzy = Advancement.Builder.advancement()
                .parent(root)
                .display(Items.BEETROOT,
                        Component.translatable("advancement.opposing_force.defeat_frowzy"),
                        Component.translatable("advancement.opposing_force.defeat_frowzy.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("defeat_frowzy", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(OPEntities.FROWZY.get())))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "defeat_frowzy"), helper);

        Advancement defeatGuzzler = Advancement.Builder.advancement()
                .parent(root)
                .display(OPItems.GUZZLER_SPAWN_EGG.get(),
                        Component.translatable("advancement.opposing_force.defeat_guzzler"),
                        Component.translatable("advancement.opposing_force.defeat_guzzler.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("defeat_guzzler", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(OPEntities.GUZZLER.get())))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "defeat_guzzler"), helper);

        Advancement defeatFireSlime = Advancement.Builder.advancement()
                .parent(defeatGuzzler)
                .display(OPItems.FIRE_SLIME_SPAWN_EGG.get(),
                        Component.translatable("advancement.opposing_force.defeat_fire_slime"),
                        Component.translatable("advancement.opposing_force.defeat_fire_slime.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("defeat_fire_slime", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(OPEntities.FIRE_SLIME.get())))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "defeat_fire_slime"), helper);

        Advancement defeatTembler = Advancement.Builder.advancement()
                .parent(root)
                .display(OPBlocks.TREMBLER_SHELL.get(),
                        Component.translatable("advancement.opposing_force.defeat_trembler"),
                        Component.translatable("advancement.opposing_force.defeat_trembler.desc"),
                        null,
                        FrameType.TASK, true, true, false)
                .addCriterion("defeat_trembler", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(OPEntities.TREMBLER.get())))
                .save(consumer, new ResourceLocation(OpposingForce.MOD_ID, "defeat_trembler"), helper);
    }
}
