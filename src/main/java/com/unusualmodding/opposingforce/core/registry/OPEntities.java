package com.unusualmodding.opposingforce.core.registry;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.entity.custom.monster.*;
import com.unusualmodding.opposingforce.common.entity.custom.projectile.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.unusualmodding.opposingforce.OpposingForce.prefix;

@Mod.EventBusSubscriber(modid = OpposingForce.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            OpposingForce.MODID);

    public static final RegistryObject<EntityType<PaleSpiderEntity>> PALE_SPIDER = ENTITIES.register("pale_spider",
            () -> EntityType.Builder.of(PaleSpiderEntity::new, MobCategory.MONSTER).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(OpposingForce.MODID, "pale_spider").toString()));

    public static final RegistryObject<EntityType<UmberSpiderEntity>> UMBER_SPIDER = ENTITIES.register("umber_spider",
            () -> EntityType.Builder.of(UmberSpiderEntity::new, MobCategory.MONSTER).sized(1.5F, 0.88F)
                    .build(new ResourceLocation(OpposingForce.MODID, "umber_spider").toString()));

    public static final RegistryObject<EntityType<RambleEntity>> RAMBLE = ENTITIES.register("ramble",
            () -> EntityType.Builder.of(RambleEntity::new, MobCategory.MONSTER).sized(1.35F, 1.98F)
                    .build(new ResourceLocation(OpposingForce.MODID, "ramble").toString()));

    public static final RegistryObject<EntityType<DicerEntity>> DICER = ENTITIES.register("dicer",
            () -> EntityType.Builder.of(DicerEntity::new, MobCategory.MONSTER).sized(0.7F, 2.0F)
                    .build(new ResourceLocation(OpposingForce.MODID, "dicer").toString()));

    public static final RegistryObject<EntityType<TremblerEntity>> TREMBLER = ENTITIES.register("trembler",
            () -> EntityType.Builder.of(TremblerEntity::new, MobCategory.MONSTER).sized(0.9F, 1.0F)
                    .build(new ResourceLocation(OpposingForce.MODID, "trembler").toString()));

    public static final RegistryObject<EntityType<TerrorEntity>> TERROR = ENTITIES.register("terror",
            () -> EntityType.Builder.of(TerrorEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(1.5F, 0.9F)
                    .build(new ResourceLocation(OpposingForce.MODID, "terror").toString()));

    public static final RegistryObject<EntityType<VoltEntity>> VOLT = ENTITIES.register("volt",
            () -> EntityType.Builder.of(VoltEntity::new, MobCategory.MONSTER).sized(1.1F, 1.8F)
                    .build(new ResourceLocation(OpposingForce.MODID, "volt").toString()));

    public static final RegistryObject<EntityType<WhizzEntity>> WHIZZ = ENTITIES.register("whizz",
            () -> EntityType.Builder.of(WhizzEntity::new, MobCategory.MONSTER).sized(0.5F, 0.5F)
                    .build(new ResourceLocation(OpposingForce.MODID, "whizz").toString()));

    public static final RegistryObject<EntityType<HopperEntity>> BOUNCER = ENTITIES.register("bouncer",
            () -> EntityType.Builder.of(HopperEntity::new, MobCategory.MONSTER).sized(1.0F, 0.65F)
                    .build(new ResourceLocation(OpposingForce.MODID, "bouncer").toString()));

    public static final RegistryObject<EntityType<FrowzyEntity>> FROWZY = ENTITIES.register("frowzy",
            () -> EntityType.Builder.of(FrowzyEntity::new, MobCategory.MONSTER).sized(0.6F, 1.9F)
                    .build(new ResourceLocation(OpposingForce.MODID, "frowzy").toString()));

    public static final RegistryObject<EntityType<FireSlimeEntity>> FIRE_SLIME = ENTITIES.register("fireslime",
            () -> EntityType.Builder.of(FireSlimeEntity::new, MobCategory.MONSTER).sized(0.6F, 0.6F)
                    .build(new ResourceLocation(OpposingForce.MODID, "fireslime").toString()));

    public static final RegistryObject<EntityType<GuzzlerEntity>> GUZZLER = ENTITIES.register("guzzler",
            () -> EntityType.Builder.of(GuzzlerEntity::new, MobCategory.MONSTER).sized(2.0F, 2.0F)
                    .build(new ResourceLocation(OpposingForce.MODID, "guzzler").toString()));

    public static final RegistryObject<EntityType<ElectricBall>> ELECTRICITY_BALL = ENTITIES.register("electric_ball",
            () -> EntityType.Builder.<ElectricBall>of(ElectricBall::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .build("electric_ball"));

    public static final RegistryObject<EntityType<Tomahawk>> TOMAHAWK = ENTITIES.register("tomahawk",
            () ->  EntityType.Builder.<Tomahawk>of(Tomahawk::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(prefix("tomahawk").toString()));

    public static final RegistryObject<EntityType<SlugEgg>> SLUG_EGG = ENTITIES.register("slug_egg",
            () -> registerEntity(EntityType.Builder.of(SlugEgg::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .setCustomClientFactory(SlugEgg::new)
                    .fireImmune(), "slug_egg"));

    public static final RegistryObject<EntityType<SlugEntity>> SLUG = ENTITIES.register("slug",
            () -> EntityType.Builder.of(SlugEntity::new, MobCategory.MONSTER).sized(0.9F, 0.9F)
                    .build(new ResourceLocation(OpposingForce.MODID, "slug").toString()));

    // Future content
//    public static final RegistryObject<EntityType<FetidEntity>> FETID = ENTITIES.register("fetid",
//            () -> EntityType.Builder.of(FetidEntity::new, MobCategory.MONSTER).sized(0.8F, 1.9F)
//                    .build(new ResourceLocation(OpposingForce.MODID, "fetid").toString()));
//
//    public static final RegistryObject<EntityType<SpindleEntity>> SPINDLE = ENTITIES.register("spindle",
//            () -> EntityType.Builder.of(SpindleEntity::new, MobCategory.MONSTER).sized(0.9F, 5.05F)
//                    .build(new ResourceLocation(OpposingForce.MODID, "spindle").toString()));

    private static EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return builder.build(entityName);
    }
}
