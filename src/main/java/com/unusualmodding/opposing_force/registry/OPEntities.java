package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.*;
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

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OpposingForce.MOD_ID);

    // Dicer
    public static final RegistryObject<EntityType<Dicer>> DICER = ENTITY_TYPE.register(
            "dicer", () ->
            EntityType.Builder.of(Dicer::new, MobCategory.MONSTER)
                    .sized(0.7F, 2.8F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "dicer").toString())
    );
    public static final RegistryObject<EntityType<DicerLaser>> DICER_LASER = ENTITY_TYPE.register(
            "dicer_laser", () ->
            EntityType.Builder.<DicerLaser>of(DicerLaser::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .updateInterval(1)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "dicer_laser").toString())
    );

    // Emeraldfish
    public static final RegistryObject<EntityType<Emeraldfish>> EMERALDFISH = ENTITY_TYPE.register(
            "emeraldfish", () ->
            EntityType.Builder.of(Emeraldfish::new, MobCategory.MONSTER)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "emeraldfish").toString())
    );

    // Fire Slime
    public static final RegistryObject<EntityType<FireSlime>> FIRE_SLIME = ENTITY_TYPE.register(
            "fire_slime", () ->
            EntityType.Builder.of(FireSlime::new, MobCategory.MONSTER)
                    .sized(0.6875F, 0.6875F)
                    .clientTrackingRange(10)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "fire_slime").toString())
    );

    // Frowzy
    public static final RegistryObject<EntityType<Frowzy>> FROWZY = ENTITY_TYPE.register(
            "frowzy", () ->
            EntityType.Builder.of(Frowzy::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.9F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "frowzy").toString())
    );

    // Guzzler
    public static final RegistryObject<EntityType<Guzzler>> GUZZLER = ENTITY_TYPE.register(
            "guzzler", () ->
            EntityType.Builder.of(Guzzler::new, MobCategory.MONSTER)
                    .sized(1.98F, 2.5F)
                    .clientTrackingRange(10)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "guzzler").toString())
    );

    // Hanging Spider
    public static final RegistryObject<EntityType<HangingSpider>> HANGING_SPIDER = ENTITY_TYPE.register(
            "hanging_spider", () ->
            EntityType.Builder.of(HangingSpider::new, MobCategory.MONSTER)
                    .sized(0.98F, 0.5F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "hanging_spider").toString())
    );

    // Ladybug
    public static final RegistryObject<EntityType<Ladybug>> LADYBUG = ENTITY_TYPE.register(
            "ladybug", () ->
            EntityType.Builder.of(Ladybug::new, MobCategory.MONSTER)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "ladybug").toString())
    );

//    public static final RegistryObject<EntityType<Nymph>> NYMPH = ENTITY_TYPES.register(
//            "nymph", () ->
//            EntityType.Builder.of(Nymph::new, MobCategory.MONSTER)
//                    .sized(0.8F, 3.8F)
//                    .clientTrackingRange(10)
//                    .build(new ResourceLocation(OpposingForce.MOD_ID, "nymph").toString())
//    );

    public static final RegistryObject<EntityType<Rambler>> RAMBLER = ENTITY_TYPE.register(
            "rambler", () ->
            EntityType.Builder.of(Rambler::new, MobCategory.MONSTER)
                    .sized(1.98F, 2.25F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "rambler").toString())
    );

    // Skyvern
    public static final RegistryObject<EntityType<Skyvern>> SKYVERN = ENTITY_TYPE.register(
            "skyvern", () ->
            EntityType.Builder.of(Skyvern::new, MobCategory.MONSTER)
                    .sized(0.8125F, 0.8125F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "skyvern").toString())
    );
    public static final RegistryObject<EntityType<SkyvernSegment>> SKYVERN_SEGMENT = ENTITY_TYPE.register(
            "skyvern_segment", () ->
            EntityType.Builder.of(SkyvernSegment::new, MobCategory.MONSTER)
                    .sized(0.8125F, 0.8125F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "skyvern_segment").toString())
    );
    public static final RegistryObject<EntityType<StratoArrow>> STRATO_ARROW = ENTITY_TYPE.register(
            "strato_arrow", () ->
            EntityType.Builder.<StratoArrow>of(StratoArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .setTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "strato_arrow").toString())
    );

    // Slug
    public static final RegistryObject<EntityType<Slug>> SLUG = ENTITY_TYPE.register(
            "slug", () ->
            EntityType.Builder.of(Slug::new, MobCategory.MONSTER)
                    .sized(1.1F, 0.9F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "slug").toString())
    );

    // Tart
    public static final RegistryObject<EntityType<Tart>> TART = ENTITY_TYPE.register(
            "tart", () ->
            EntityType.Builder.of(Tart::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "tart").toString())
    );

    // Terror
    public static final RegistryObject<EntityType<Terror>> TERROR = ENTITY_TYPE.register(
            "terror", () ->
            EntityType.Builder.of(Terror::new, MobCategory.MONSTER)
                    .sized(1.3F, 1.1F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "terror").toString())
    );

    // Trembler
    public static final RegistryObject<EntityType<Trembler>> TREMBLER = ENTITY_TYPE.register(
            "trembler", () ->
            EntityType.Builder.of(Trembler::new, MobCategory.MONSTER)
                    .sized(0.9F, 0.98F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "trembler").toString())
    );

    // Umber Spider
    public static final RegistryObject<EntityType<UmberSpider>> UMBER_SPIDER = ENTITY_TYPE.register(
            "umber_spider", () ->
            EntityType.Builder.of(UmberSpider::new, MobCategory.MONSTER)
                    .sized(1.4F, 0.9F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "umber_spider").toString())
    );

    // Volt
    public static final RegistryObject<EntityType<Volt>> VOLT = ENTITY_TYPE.register(
            "volt", () ->
            EntityType.Builder.of(Volt::new, MobCategory.MONSTER)
                    .sized(1.1F, 1.8F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "volt").toString())
    );
    public static final RegistryObject<EntityType<ElectricCharge>> ELECTRIC_CHARGE = ENTITY_TYPE.register(
            "electric_charge", () ->
            EntityType.Builder.<ElectricCharge>of(ElectricCharge::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .setShouldReceiveVelocityUpdates(true)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "electric_charge").toString())
    );

    // Whizz
    public static final RegistryObject<EntityType<Whizz>> WHIZZ = ENTITY_TYPE.register(
            "whizz", () ->
            EntityType.Builder.of(Whizz::new, MobCategory.MONSTER)
                    .sized(0.75F, 0.75F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "whizz").toString())
    );

    public static final RegistryObject<EntityType<LaserBolt>> LASER_BOLT = ENTITY_TYPE.register(
            "laser_bolt", () ->
            EntityType.Builder.<LaserBolt>of(LaserBolt::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(4)
                    .setShouldReceiveVelocityUpdates(true)
                    .updateInterval(10)
                    .fireImmune()
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "laser_bolt").toString())
    );

    public static final RegistryObject<EntityType<Tomahawk>> TOMAHAWK = ENTITY_TYPE.register(
            "tomahawk", () ->
            EntityType.Builder.<Tomahawk>of(Tomahawk::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "tomahawk").toString())
    );

    public static final RegistryObject<EntityType<UmberDagger>> UMBER_DAGGER = ENTITY_TYPE.register(
            "umber_dagger", () ->
            EntityType.Builder.<UmberDagger>of(UmberDagger::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "umber_dagger").toString())
    );

    public static final RegistryObject<EntityType<ThrownLaserBlade>> LASER_BLADE = ENTITY_TYPE.register(
            "laser_blade", () ->
            EntityType.Builder.<ThrownLaserBlade>of(ThrownLaserBlade::new, MobCategory.MISC)
                    .sized(2.0F, 0.5F)
                    .clientTrackingRange(20)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "laser_blade").toString())
    );

    // Bombs
    public static final RegistryObject<EntityType<FireBomb>> FIRE_BOMB = ENTITY_TYPE.register(
            "fire_bomb", () ->
            EntityType.Builder.<FireBomb>of(FireBomb::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "fire_bomb").toString())
    );
    public static final RegistryObject<EntityType<KineticBomb>> KINETIC_BOMB = ENTITY_TYPE.register(
            "kinetic_bomb", () ->
            EntityType.Builder.<KineticBomb>of(KineticBomb::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "kinetic_bomb").toString())
    );
    public static final RegistryObject<EntityType<LightningBomb>> LIGHTNING_BOMB = ENTITY_TYPE.register(
        "lightning_bomb", () ->
            EntityType.Builder.<LightningBomb>of(LightningBomb::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "lightning_bomb").toString())
    );
    public static final RegistryObject<EntityType<WhizzBomb>> WHIZZ_BOMB = ENTITY_TYPE.register(
            "whizz_bomb", () ->
            EntityType.Builder.<WhizzBomb>of(WhizzBomb::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(OpposingForce.MOD_ID, "whizz_bomb").toString())
    );
}
