package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.*;
import com.unusualmodding.opposing_force.entity.projectile.DicerLaser;
import com.unusualmodding.opposing_force.entity.projectile.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OpposingForce.MOD_ID);

    public static final RegistryObject<EntityType<Dicer>> DICER = ENTITY_TYPES.register(
            "dicer", () ->
            EntityType.Builder.of(Dicer::new, MobCategory.MONSTER)
                    .sized(0.7F, 2.25F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "dicer").toString())
    );

    public static final RegistryObject<EntityType<Emeraldfish>> EMERALDFISH = ENTITY_TYPES.register(
            "emeraldfish", () ->
            EntityType.Builder.of(Emeraldfish::new, MobCategory.MONSTER)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "emeraldfish").toString())
    );

    public static final RegistryObject<EntityType<FireSlime>> FIRE_SLIME = ENTITY_TYPES.register(
            "fire_slime", () ->
            EntityType.Builder.of(FireSlime::new, MobCategory.MONSTER)
                    .sized(0.6875F, 0.6875F)
                    .clientTrackingRange(10)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "fire_slime").toString())
    );

    public static final RegistryObject<EntityType<Frowzy>> FROWZY = ENTITY_TYPES.register(
            "frowzy", () ->
            EntityType.Builder.of(Frowzy::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.9F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "frowzy").toString())
    );

    public static final RegistryObject<EntityType<Guzzler>> GUZZLER = ENTITY_TYPES.register(
            "guzzler", () ->
            EntityType.Builder.of(Guzzler::new, MobCategory.MONSTER)
                    .sized(2.5F, 2.75F)
                    .clientTrackingRange(10)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "guzzler").toString())
    );

    public static final RegistryObject<EntityType<HauntedTool>> HAUNTED_TOOL = ENTITY_TYPES.register(
            "haunted_tool", () ->
            EntityType.Builder.of(HauntedTool::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "haunted_tool").toString())
    );

    public static final RegistryObject<EntityType<PaleSpider>> PALE_SPIDER = ENTITY_TYPES.register(
            "pale_spider", () ->
            EntityType.Builder.of(PaleSpider::new, MobCategory.MONSTER)
                    .sized(1.0F, 0.5F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "pale_spider").toString())
    );

    public static final RegistryObject<EntityType<Ramble>> RAMBLE = ENTITY_TYPES.register(
            "ramble", () ->
            EntityType.Builder.of(Ramble::new, MobCategory.MONSTER)
                    .sized(1.35F, 1.98F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "ramble").toString())
    );

    public static final RegistryObject<EntityType<Slug>> SLUG = ENTITY_TYPES.register(
            "slug", () ->
            EntityType.Builder.of(Slug::new, MobCategory.CREATURE)
                    .sized(1.0F, 0.8F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "slug").toString())
    );

    public static final RegistryObject<EntityType<Terror>> TERROR = ENTITY_TYPES.register(
            "terror", () ->
            EntityType.Builder.of(Terror::new, MobCategory.MONSTER)
                    .sized(1.5F, 0.9F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "terror").toString())
    );

    public static final RegistryObject<EntityType<Trembler>> TREMBLER = ENTITY_TYPES.register(
            "trembler", () ->
            EntityType.Builder.of(Trembler::new, MobCategory.MONSTER)
                    .sized(0.9F, 1.0F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "trembler").toString())
    );

    public static final RegistryObject<EntityType<UmberSpider>> UMBER_SPIDER = ENTITY_TYPES.register(
            "umber_spider", () ->
            EntityType.Builder.of(UmberSpider::new, MobCategory.MONSTER)
                    .sized(1.4F, 0.9F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "umber_spider").toString())
    );

    public static final RegistryObject<EntityType<Volt>> VOLT = ENTITY_TYPES.register(
            "volt", () ->
            EntityType.Builder.of(Volt::new, MobCategory.MONSTER)
                    .sized(1.1F, 1.8F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "volt").toString())
    );

    public static final RegistryObject<EntityType<Whizz>> WHIZZ = ENTITY_TYPES.register(
            "whizz", () ->
            EntityType.Builder.of(Whizz::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "whizz").toString())
    );

    public static final RegistryObject<EntityType<DicerLaser>> DICER_LASER = ENTITY_TYPES.register(
            "dicer_laser", () ->
            EntityType.Builder.<DicerLaser>of(DicerLaser::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f)
                    .setUpdateInterval(1)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "dicer_laser").toString())
    );

    public static final RegistryObject<EntityType<ElectricCharge>> ELECTRIC_CHARGE = ENTITY_TYPES.register(
            "electric_charge", () ->
            EntityType.Builder.<ElectricCharge>of(ElectricCharge::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f)
                    .clientTrackingRange(4)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "electric_charge").toString())
    );

    public static final RegistryObject<EntityType<SlugEggs>> SLUG_EGGS = ENTITY_TYPES.register(
            "slug_eggs", () ->
            EntityType.Builder.<SlugEggs>of(SlugEggs::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "slug_eggs").toString())
    );

    public static final RegistryObject<EntityType<Tomahawk>> TOMAHAWK = ENTITY_TYPES.register(
            "tomahawk", () ->
            EntityType.Builder.<Tomahawk>of(Tomahawk::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "tomahawk").toString())
    );

    public static final RegistryObject<EntityType<TremblerShell>> TREMBLER_SHELL = ENTITY_TYPES.register(
            "trembler_shell", () ->
            EntityType.Builder.<TremblerShell>of(TremblerShell::new, MobCategory.MISC)
                    .sized(0.75F, 0.75F)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "trembler_shell").toString())
    );
}
