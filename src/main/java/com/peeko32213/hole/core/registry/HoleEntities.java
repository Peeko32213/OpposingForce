package com.peeko32213.hole.core.registry;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.*;
import com.peeko32213.hole.common.entity.projectile.EntitySlugEgg;
import com.peeko32213.hole.common.entity.projectile.EntitySmallElectricBall;
import com.peeko32213.hole.common.entity.projectile.Tomahawk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.peeko32213.hole.Hole.prefix;

@Mod.EventBusSubscriber(modid = Hole.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HoleEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            Hole.MODID);

    public static final RegistryObject<EntityType<EntityPaleSpider>> PALE_SPIDER = ENTITIES.register("pale_spider",
            () -> EntityType.Builder.of(EntityPaleSpider::new, MobCategory.MONSTER).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(Hole.MODID, "pale_spider").toString()));

    public static final RegistryObject<EntityType<EntityUmberSpider>> UMBER_SPIDER = ENTITIES.register("umber_spider",
            () -> EntityType.Builder.of(EntityUmberSpider::new, MobCategory.MONSTER).sized(1.5F, 1.0F)
                    .build(new ResourceLocation(Hole.MODID, "umber_spider").toString()));

    public static final RegistryObject<EntityType<EntityRamble>> RAMBLE = ENTITIES.register("ramble",
            () -> EntityType.Builder.of(EntityRamble::new, MobCategory.MONSTER).sized(1.4F, 2.0F)
                    .build(new ResourceLocation(Hole.MODID, "ramble").toString()));

    public static final RegistryObject<EntityType<EntityDicer>> DICER = ENTITIES.register("dicer",
            () -> EntityType.Builder.of(EntityDicer::new, MobCategory.MONSTER).sized(0.7F, 2.0F)
                    .build(new ResourceLocation(Hole.MODID, "dicer").toString()));

    public static final RegistryObject<EntityType<EntityTrembler>> TREMBLER = ENTITIES.register("trembler",
            () -> EntityType.Builder.of(EntityTrembler::new, MobCategory.MONSTER).sized(0.9F, 1.0F)
                    .build(new ResourceLocation(Hole.MODID, "trembler").toString()));

    public static final RegistryObject<EntityType<EntityTerror>> TERROR = ENTITIES.register("terror",
            () -> EntityType.Builder.of(EntityTerror::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(1.5F, 0.9F)
                    .build(new ResourceLocation(Hole.MODID, "terror").toString()));

    public static final RegistryObject<EntityType<EntityVolt>> VOLT = ENTITIES.register("volt",
            () -> EntityType.Builder.of(EntityVolt::new, MobCategory.MONSTER).sized(1.1F, 1.8F)
                    .build(new ResourceLocation(Hole.MODID, "volt").toString()));

    public static final RegistryObject<EntityType<EntityWizz>> WIZZ = ENTITIES.register("wizz",
            () -> EntityType.Builder.of(EntityWizz::new, MobCategory.MONSTER).sized(0.5F, 0.5F)
                    .build(new ResourceLocation(Hole.MODID, "wizz").toString()));

    public static final RegistryObject<EntityType<EntityHopper>> HOPPER = ENTITIES.register("hopper",
            () -> EntityType.Builder.of(EntityHopper::new, MobCategory.MONSTER).sized(1.0F, 0.65F)
                    .build(new ResourceLocation(Hole.MODID, "wizz").toString()));
    public static final RegistryObject<EntityType<EntitySmallElectricBall>> SMALL_ELECTRICITY_BALL = ENTITIES.register(
            "small_electric_ball", () -> EntityType.Builder.<EntitySmallElectricBall>of(EntitySmallElectricBall::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(9).updateInterval(10)
                    .build(new ResourceLocation(Hole.MODID, "small_electric_ball").toString()));

    public static final RegistryObject<EntityType<EntityFrowzy>> FROWZY = ENTITIES.register("frowzy",
            () -> EntityType.Builder.of(EntityFrowzy::new, MobCategory.MONSTER).sized(0.6F, 1.9F)
                    .build(new ResourceLocation(Hole.MODID, "frowzy").toString()));

    public static final RegistryObject<EntityType<EntityFireSlime>> FIRE_SLIME = ENTITIES.register("fireslime",
            () -> EntityType.Builder.of(EntityFireSlime::new, MobCategory.MONSTER).sized(0.6F, 0.6F)
                    .build(new ResourceLocation(Hole.MODID, "fireslime").toString()));

    public static final RegistryObject<EntityType<EntityGuzzler>> GUZZLER = ENTITIES.register("guzzler",
            () -> EntityType.Builder.of(EntityGuzzler::new, MobCategory.MONSTER).sized(2.0F, 2.0F)
                    .build(new ResourceLocation(Hole.MODID, "guzzler").toString()));

    public static final RegistryObject<EntityType<Tomahawk>> TOMAHAWK = ENTITIES.register("tomahawk",
            () ->  EntityType.Builder.<Tomahawk>of(Tomahawk::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(prefix("kunai").toString()));

    public static final RegistryObject<EntityType<EntitySlugEgg>> SLUG_EGG = ENTITIES.register("slug_egg",
            () -> registerEntity(EntityType.Builder.of(EntitySlugEgg::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .setCustomClientFactory(EntitySlugEgg::new)
                    .fireImmune(), "slug_egg"));

    public static final RegistryObject<EntityType<EntitySlug>> SLUG = ENTITIES.register("slug",
            () -> EntityType.Builder.of(EntitySlug::new, MobCategory.MONSTER).sized(0.9F, 0.9F)
                    .build(new ResourceLocation(Hole.MODID, "slug").toString()));

    public static final RegistryObject<EntityType<EntityFetid>> FETID = ENTITIES.register("fetid",
            () -> EntityType.Builder.of(EntityFetid::new, MobCategory.MONSTER).sized(0.8F, 1.9F)
                    .build(new ResourceLocation(Hole.MODID, "fetid").toString()));

    public static final RegistryObject<EntityType<EntitySpindle>> SPINDLE = ENTITIES.register("spindle",
            () -> EntityType.Builder.of(EntitySpindle::new, MobCategory.MONSTER).sized(0.8F, 1.9F)
                    .build(new ResourceLocation(Hole.MODID, "fetid").toString()));

    private static EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return builder.build(entityName);
    }


}
