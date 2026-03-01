package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.items.*;
import com.unusualmodding.opposing_force.items.armor.*;
import com.unusualmodding.opposing_force.items.tools.*;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OPItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OpposingForce.MOD_ID);
    public static List<RegistryObject<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

    public static final RegistryObject<Item> OPPOSING_FORCE =  registerItem("opposing_force", () -> new Item(new Item.Properties()));

    // Dicer
    public static final RegistryObject<Item> DICER_SPAWN_EGG = registerSpawnEggItem("dicer", OPEntities.DICER , 0x1c0d1c, 0x3850f9);
    public static final RegistryObject<Item> DICER_LENS = registerItem("dicer_lens", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LASER_CORE = registerItem("laser_core", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLASTER = registerItem("blaster", () -> new BlasterItem(0xff38ff));
    public static final RegistryObject<Item> WHITE_BLASTER = registerItem("white_blaster", () -> new BlasterItem(0xffffff));
    public static final RegistryObject<Item> LIGHT_GRAY_BLASTER = registerItem("light_gray_blaster", () -> new BlasterItem(0xcfcfcf));
    public static final RegistryObject<Item> GRAY_BLASTER = registerItem("gray_blaster", () -> new BlasterItem(0xa99f9f));
    public static final RegistryObject<Item> BLACK_BLASTER = registerItem("black_blaster", () -> new BlasterItem(0x494848));
    public static final RegistryObject<Item> BROWN_BLASTER = registerItem("brown_blaster", () -> new BlasterItem(0xb17041));
    public static final RegistryObject<Item> RED_BLASTER = registerItem("red_blaster", () -> new BlasterItem(0xff7971));
    public static final RegistryObject<Item> ORANGE_BLASTER = registerItem("orange_blaster", () -> new BlasterItem(0xff9847));
    public static final RegistryObject<Item> YELLOW_BLASTER = registerItem("yellow_blaster", () -> new BlasterItem(0xe7e72a));
    public static final RegistryObject<Item> LIME_BLASTER = registerItem("lime_blaster", () -> new BlasterItem(0x8cd829));
    public static final RegistryObject<Item> GREEN_BLASTER = registerItem("green_blaster", () -> new BlasterItem(0x3cc751));
    public static final RegistryObject<Item> CYAN_BLASTER = registerItem("cyan_blaster", () -> new BlasterItem(0x55cfe5));
    public static final RegistryObject<Item> LIGHT_BLUE_BLASTER = registerItem("light_blue_blaster", () -> new BlasterItem(0xb2cbff));
    public static final RegistryObject<Item> BLUE_BLASTER = registerItem("blue_blaster", () -> new BlasterItem(0x6795f4));
    public static final RegistryObject<Item> PURPLE_BLASTER = registerItem("purple_blaster", () -> new BlasterItem(0xbc65f2));
    public static final RegistryObject<Item> MAGENTA_BLASTER = registerItem("magenta_blaster", () -> new BlasterItem(0xff6ef3));
    public static final RegistryObject<Item> PINK_BLASTER = registerItem("pink_blaster", () -> new BlasterItem(0xff38ff));

    public static final RegistryObject<Item> LASER_BLADE = registerItem("laser_blade", () -> new LaserBladeItem(OPParticles.LASER_SWEEP.get()));
    public static final RegistryObject<Item> WHITE_LASER_BLADE = registerItem("white_laser_blade", () -> new LaserBladeItem(OPParticles.WHITE_LASER_SWEEP.get()));
    public static final RegistryObject<Item> LIGHT_GRAY_LASER_BLADE = registerItem("light_gray_laser_blade", () -> new LaserBladeItem(OPParticles.LIGHT_GRAY_LASER_SWEEP.get()));
    public static final RegistryObject<Item> GRAY_LASER_BLADE = registerItem("gray_laser_blade", () -> new LaserBladeItem(OPParticles.GRAY_LASER_SWEEP.get()));
    public static final RegistryObject<Item> BLACK_LASER_BLADE = registerItem("black_laser_blade", () -> new LaserBladeItem(OPParticles.BLACK_LASER_SWEEP.get()));
    public static final RegistryObject<Item> BROWN_LASER_BLADE = registerItem("brown_laser_blade", () -> new LaserBladeItem(OPParticles.BROWN_LASER_SWEEP.get()));
    public static final RegistryObject<Item> RED_LASER_BLADE = registerItem("red_laser_blade", () -> new LaserBladeItem(OPParticles.RED_LASER_SWEEP.get()));
    public static final RegistryObject<Item> ORANGE_LASER_BLADE = registerItem("orange_laser_blade", () -> new LaserBladeItem(OPParticles.ORANGE_LASER_SWEEP.get()));
    public static final RegistryObject<Item> YELLOW_LASER_BLADE = registerItem("yellow_laser_blade", () -> new LaserBladeItem(OPParticles.YELLOW_LASER_SWEEP.get()));
    public static final RegistryObject<Item> LIME_LASER_BLADE = registerItem("lime_laser_blade", () -> new LaserBladeItem(OPParticles.LIME_LASER_SWEEP.get()));
    public static final RegistryObject<Item> GREEN_LASER_BLADE = registerItem("green_laser_blade", () -> new LaserBladeItem(OPParticles.GREEN_LASER_SWEEP.get()));
    public static final RegistryObject<Item> CYAN_LASER_BLADE = registerItem("cyan_laser_blade", () -> new LaserBladeItem(OPParticles.CYAN_LASER_SWEEP.get()));
    public static final RegistryObject<Item> LIGHT_BLUE_LASER_BLADE = registerItem("light_blue_laser_blade", () -> new LaserBladeItem(OPParticles.LIGHT_BLUE_LASER_SWEEP.get()));
    public static final RegistryObject<Item> BLUE_LASER_BLADE = registerItem("blue_laser_blade", () -> new LaserBladeItem(OPParticles.BLUE_LASER_SWEEP.get()));
    public static final RegistryObject<Item> PURPLE_LASER_BLADE = registerItem("purple_laser_blade", () -> new LaserBladeItem(OPParticles.PURPLE_LASER_SWEEP.get()));
    public static final RegistryObject<Item> MAGENTA_LASER_BLADE = registerItem("magenta_laser_blade", () -> new LaserBladeItem(OPParticles.MAGENTA_LASER_SWEEP.get()));
    public static final RegistryObject<Item> PINK_LASER_BLADE = registerItem("pink_laser_blade", () -> new LaserBladeItem(OPParticles.PINK_LASER_SWEEP.get()));
    public static final RegistryObject<Item> RAINBOW_LASER_BLADE = registerItem("rainbow_laser_blade", () -> new RainbowLaserBladeItem(OPParticles.LASER_SWEEP.get()));

    public static final RegistryObject<Item> DICER_HEAD = registerItemNoLang("dicer_head", () -> new MobHeadItem(OPBlocks.DICER_HEAD.getFirst().get(), OPBlocks.DICER_HEAD.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // Fire Slime
    public static final RegistryObject<Item> FIRE_SLIME_SPAWN_EGG = registerSpawnEggItem("fire_slime", OPEntities.FIRE_SLIME , 0xfb921b, 0xdb3709);
    public static final RegistryObject<Item> FIRE_GEL = registerItem("fire_gel", () -> new Item(foodItem(OPFoodValues.FIRE_GEL)));

    // Frowzy
    public static final RegistryObject<Item> FROWZY_SPAWN_EGG = registerSpawnEggItem("frowzy", OPEntities.FROWZY , 0x35313b, 0x3f759f);
    public static final RegistryObject<Item> FROWZY_HEAD = registerItemNoLang("frowzy_head", () -> new MobHeadItem(OPBlocks.FROWZY_HEAD.getFirst().get(), OPBlocks.FROWZY_HEAD.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // Guzzler
    public static final RegistryObject<Item> GUZZLER_SPAWN_EGG = registerSpawnEggItem("guzzler", OPEntities.GUZZLER , 0x160e2c, 0x8956c2);
    public static final RegistryObject<Item> GUZZLER_SCALES = registerItem("guzzler_scales", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFERNO_PIE_SLICE = registerItemNoLang("inferno_pie_slice", () -> new Item(foodItem(OPFoodValues.INFERNO_PIE)));
    public static final RegistryObject<Item> INFERNO_STAFF = registerItem("inferno_staff", () -> new InfernoStaffItem(new Item.Properties().stacksTo(1).defaultDurability(236)));
    public static final RegistryObject<Item> FIRE_BOMB = registerItem("fire_bomb", () -> new FireBombItem(new Item.Properties().stacksTo(16)));

    // Hanging Spider
    public static final RegistryObject<Item> HANGING_SPIDER_SPAWN_EGG = registerSpawnEggItem("hanging_spider", OPEntities.HANGING_SPIDER, 0x2c231e, 0xf5e83b);

    // Ladybug
//    public static final RegistryObject<Item> LADYBUG_SPAWN_EGG = registerSpawnEggItem("ladybug", OPEntities.LADYBUG, 0xda3131, 0x2a1d28);
    public static final RegistryObject<Item> LADYBUG_HUSK = registerItem("ladybug_husk", () -> new Item(new Item.Properties()));

    // Nymph
//    public static final RegistryObject<Item> NYMPH_SPAWN_EGG = registerSpawnEggItem("nymph", OPEntities.NYMPH , 0x271c15, 0x678349);

    // Rambler
    public static final RegistryObject<Item> RAMBLER_SPAWN_EGG = registerSpawnEggItem("rambler", OPEntities.RAMBLER, 0xededcf, 0x685944);
    public static final RegistryObject<Item> HEAVY_BONE = registerItem("heavy_bone", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BONE_SWORD = registerItem("bone_sword", ()-> new BoneSwordItem(new Item.Properties()));
    public static final RegistryObject<Item> BONE_PICKAXE = registerItem("bone_pickaxe", ()-> new BonePickaxeItem(new Item.Properties()));
    public static final RegistryObject<Item> BONE_AXE = registerItem("bone_axe", ()-> new BoneAxeItem(new Item.Properties()));
    public static final RegistryObject<Item> BONE_SHOVEL = registerItem("bone_shovel", ()-> new BoneShovelItem(new Item.Properties()));
    public static final RegistryObject<Item> BONE_HOE = registerItem("bone_hoe", ()-> new BoneHoeItem(new Item.Properties()));
    public static final RegistryObject<Item> BONE_HELMET = registerItem("bone_helmet", ()-> new ConfigurableArmorItem(ArmorItem.Type.HELMET, new Item.Properties(), OPArmorDefinitions.BONE));
    public static final RegistryObject<Item> BONE_CHESTPLATE = registerItem("bone_chestplate", ()-> new ConfigurableArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties(), OPArmorDefinitions.BONE));
    public static final RegistryObject<Item> BONE_LEGGINGS = registerItem("bone_leggings", ()-> new ConfigurableArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties(), OPArmorDefinitions.BONE));
    public static final RegistryObject<Item> BONE_BOOTS = registerItem("bone_boots", ()-> new ConfigurableArmorItem(ArmorItem.Type.BOOTS, new Item.Properties(), OPArmorDefinitions.BONE));
    public static final RegistryObject<Item> ANGRY_RAMBLER_SKULL = registerItemNoLang("angry_rambler_skull", () -> new RamblerSkullItem(OPBlocks.ANGRY_RAMBLER_SKULL.getFirst().get(), OPBlocks.ANGRY_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> CLASSIC_RAMBLER_SKULL = registerItemNoLang("classic_rambler_skull", () -> new RamblerSkullItem(OPBlocks.CLASSIC_RAMBLER_SKULL.getFirst().get(), OPBlocks.CLASSIC_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> EVIL_RAMBLER_SKULL = registerItemNoLang("evil_rambler_skull", () -> new RamblerSkullItem(OPBlocks.EVIL_RAMBLER_SKULL.getFirst().get(), OPBlocks.EVIL_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> GRINNING_RAMBLER_SKULL = registerItemNoLang("grinning_rambler_skull", () -> new RamblerSkullItem(OPBlocks.GRINNING_RAMBLER_SKULL.getFirst().get(), OPBlocks.GRINNING_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> SKELETAL_RAMBLER_SKULL = registerItemNoLang("skeletal_rambler_skull", () -> new RamblerSkullItem(OPBlocks.SKELETAL_RAMBLER_SKULL.getFirst().get(), OPBlocks.SKELETAL_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> SMILING_RAMBLER_SKULL = registerItemNoLang("smiling_rambler_skull", () -> new RamblerSkullItem(OPBlocks.SMILING_RAMBLER_SKULL.getFirst().get(), OPBlocks.SMILING_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> STRANGE_RAMBLER_SKULL = registerItemNoLang("strange_rambler_skull", () -> new RamblerSkullItem(OPBlocks.STRANGE_RAMBLER_SKULL.getFirst().get(), OPBlocks.STRANGE_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> CRUNDLY_RAMBLER_SKULL = registerItemNoLang("crundly_rambler_skull", () -> new RamblerSkullItem(OPBlocks.CRUNDLY_RAMBLER_SKULL.getFirst().get(), OPBlocks.CRUNDLY_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> DWARVEN_RAMBLER_SKULL = registerItemNoLang("dwarven_rambler_skull", () -> new RamblerSkullItem(OPBlocks.DWARVEN_RAMBLER_SKULL.getFirst().get(), OPBlocks.DWARVEN_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> IMPRISONED_RAMBLER_SKULL = registerItemNoLang("imprisoned_rambler_skull", () -> new RamblerSkullItem(OPBlocks.IMPRISONED_RAMBLER_SKULL.getFirst().get(), OPBlocks.IMPRISONED_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> INDOMITABLE_RAMBLER_SKULL = registerItemNoLang("indomitable_rambler_skull", () -> new RamblerSkullItem(OPBlocks.INDOMITABLE_RAMBLER_SKULL.getFirst().get(), OPBlocks.INDOMITABLE_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> LEERING_RAMBLER_SKULL = registerItemNoLang("leering_rambler_skull", () -> new RamblerSkullItem(OPBlocks.LEERING_RAMBLER_SKULL.getFirst().get(), OPBlocks.LEERING_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> MAGMATIC_RAMBLER_SKULL = registerItemNoLang("magmatic_rambler_skull", () -> new RamblerSkullItem(OPBlocks.MAGMATIC_RAMBLER_SKULL.getFirst().get(), OPBlocks.MAGMATIC_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> MUSICAL_RAMBLER_SKULL = registerItemNoLang("musical_rambler_skull", () -> new RamblerSkullItem(OPBlocks.MUSICAL_RAMBLER_SKULL.getFirst().get(), OPBlocks.MUSICAL_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> NOSY_RAMBLER_SKULL = registerItemNoLang("nosy_rambler_skull", () -> new RamblerSkullItem(OPBlocks.NOSY_RAMBLER_SKULL.getFirst().get(), OPBlocks.NOSY_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));
    public static final RegistryObject<Item> VALIANT_RAMBLER_SKULL = registerItemNoLang("valiant_rambler_skull", () -> new RamblerSkullItem(OPBlocks.VALIANT_RAMBLER_SKULL.getFirst().get(), OPBlocks.VALIANT_RAMBLER_SKULL.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // Skyvern
    public static final RegistryObject<Item> SKYVERN_SPAWN_EGG = registerSpawnEggItem("skyvern", OPEntities.SKYVERN, 0xf0e2e7, 0x124077);
    public static final RegistryObject<Item> SKYVERN_CLAW = registerItem("skyvern_claw", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SKYVERN_EGG = registerItem("skyvern_egg", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STRATO_BOW = registerItem("strato_bow", () -> new StratoBowItem(new Item.Properties().stacksTo(1).defaultDurability(1341)));
    public static final RegistryObject<Item> SKYVERN_HEAD = registerItemNoLang("skyvern_head", () -> new MobHeadItem(OPBlocks.SKYVERN_HEAD.getFirst().get(), OPBlocks.SKYVERN_HEAD.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // Slug
    public static final RegistryObject<Item> SLUG_SPAWN_EGG = registerSpawnEggItem("slug", OPEntities.SLUG , 0x7a7139, 0x2e2411);
    public static final RegistryObject<Item> VILE_BOULDER = registerItem("vile_boulder", () -> new VileBoulderItem(new Item.Properties()));
    public static final RegistryObject<Item> VILE_STAFF = registerItem("vile_staff", () -> new VileStaffItem(new Item.Properties().stacksTo(1).defaultDurability(224)));
    public static final RegistryObject<Item> SLUG_BARON_HELMET = registerItem("slug_baron_helmet", ()-> new SlugBaronArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SLUG_BARON_CHESTPLATE = registerItem("slug_baron_chestplate", ()-> new SlugBaronArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SLUG_BARON_LEGGINGS = registerItem("slug_baron_leggings", ()-> new SlugBaronArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SLUG_BARON_BOOTS = registerItem("slug_baron_boots", ()-> new SlugBaronArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    // Tart
    public static final RegistryObject<Item> TART_SPAWN_EGG = registerSpawnEggItem("tart", OPEntities.TART , 0xb31e1e, 0x2f6e19);
    public static final RegistryObject<Item> RAW_TART = registerItemNoLang("tart", () -> new Item(foodItem(OPFoodValues.RAW_TART)));
    public static final RegistryObject<Item> COOKED_TART = registerItem("cooked_tart", () -> new Item(foodItem(OPFoodValues.COOKED_TART)));
    public static final RegistryObject<Item> TART_HEAD = registerItemNoLang("tart_head", () -> new MobHeadItem(OPBlocks.TART_HEAD.getFirst().get(), OPBlocks.TART_HEAD.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // Terror
    public static final RegistryObject<Item> TERROR_SPAWN_EGG = registerSpawnEggItem("terror", OPEntities.TERROR , 0x074230, 0xff0000);
    public static final RegistryObject<Item> TERROR_SAW = registerItem("terror_saw", () -> new TerrorSawItem(new Item.Properties()));
    public static final RegistryObject<Item> SAWBLADE = registerItem("sawblade", () -> new SawbladeItem(new Item.Properties()));

    public static final RegistryObject<Item> TERROR_LEG = registerItemNoLang("terror_leg", () -> new Item(foodItem(OPFoodValues.RAW_TERROR_LEG)));
    public static final RegistryObject<Item> FRIED_TERROR_LEG = registerItem("fried_terror_leg", () -> new Item(foodItem(OPFoodValues.FRIED_TERROR_LEG)));
    public static final RegistryObject<Item> SPICY_TERROR_LEG = registerItem("spicy_terror_leg", () -> new Item(foodItem(OPFoodValues.SPICY_TERROR_LEG)));

    // Trembler
    public static final RegistryObject<Item> TREMBLER_SPAWN_EGG = registerSpawnEggItem("trembler", OPEntities.TREMBLER , 0x465641, 0x0d0e0d);
    public static final RegistryObject<Item> TREMBLING_SLAMMER = registerItem("trembling_slammer", () -> new TremblingSlammer(new Item.Properties()));

    // Umber Spider
    public static final RegistryObject<Item> UMBER_SPIDER_SPAWN_EGG = registerSpawnEggItem("umber_spider", OPEntities.UMBER_SPIDER , 0x241d2c, 0x44a9f6);
    public static final RegistryObject<Item> DEEP_SILK = registerItem("deep_silk", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEEPWOVEN_HAT = registerItem("deepwoven_hat", ()-> new ConfigurableArmorItem(ArmorItem.Type.HELMET, new Item.Properties(), OPArmorDefinitions.DEEPWOVEN));
    public static final RegistryObject<Item> DEEPWOVEN_TUNIC = registerItem("deepwoven_tunic", ()-> new ConfigurableArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties(), OPArmorDefinitions.DEEPWOVEN));
    public static final RegistryObject<Item> DEEPWOVEN_PANTS = registerItem("deepwoven_pants", ()-> new ConfigurableArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties(), OPArmorDefinitions.DEEPWOVEN));
    public static final RegistryObject<Item> DEEPWOVEN_BOOTS = registerItem("deepwoven_boots", ()-> new ConfigurableArmorItem(ArmorItem.Type.BOOTS, new Item.Properties(), OPArmorDefinitions.DEEPWOVEN));
    public static final RegistryObject<Item> UMBER_FANG = registerItem("umber_fang", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UMBER_DAGGER = registerItem("umber_dagger", () -> new UmberDaggerItem(new Item.Properties().stacksTo(1)));

    // Volt
    public static final RegistryObject<Item> VOLT_SPAWN_EGG = registerSpawnEggItem("volt", OPEntities.VOLT , 0x2c1538, 0x00bfff);
    public static final RegistryObject<Item> ELECTRIC_CHARGE = registerItem("electric_charge", () -> new ElectricChargeItem(OPEntities.ELECTRIC_CHARGE, (new Item.Properties())));
    public static final RegistryObject<Item> ELECTRIC_ALLOY = registerItem("electric_alloy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TESLA_CANNON = registerItem("tesla_cannon", () -> new TeslaCannonItem(new Item.Properties().stacksTo(1).durability(465)));
    public static final RegistryObject<Item> SPARK_BLADE = registerItem("spark_blade", () -> new SparkBladeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LIGHTNING_BOMB = registerItem("lightning_bomb", () -> new LightningBombItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> RECON_KNIGHT_HELMET = registerItem("recon_knight_helmet", ()-> new ConfigurableArmorItem(ArmorItem.Type.HELMET, new Item.Properties(), OPArmorDefinitions.RECON_KNIGHT));
    public static final RegistryObject<Item> RECON_KNIGHT_CHESTPLATE = registerItem("recon_knight_chestplate", ()-> new ConfigurableArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties(), OPArmorDefinitions.RECON_KNIGHT));
    public static final RegistryObject<Item> RECON_KNIGHT_LEGGINGS = registerItem("recon_knight_leggings", ()-> new ConfigurableArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties(), OPArmorDefinitions.RECON_KNIGHT));
    public static final RegistryObject<Item> RECON_KNIGHT_BOOTS = registerItem("recon_knight_boots", ()-> new ConfigurableArmorItem(ArmorItem.Type.BOOTS, new Item.Properties(), OPArmorDefinitions.RECON_KNIGHT));

    // Whizz
    public static final RegistryObject<Item> WHIZZ_SPAWN_EGG = registerSpawnEggItem("whizz", OPEntities.WHIZZ, 0x8a6ce0, 0xffe7f8);
    public static final RegistryObject<Item> CAPTURED_WHIZZ = registerItem("captured_whizz", () -> new MobItem(OPEntities.WHIZZ::get, SoundEvents.AMETHYST_BLOCK_RESONATE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WHIZZ_BOMB = registerItem("whizz_bomb", () -> new WhizzBombItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> WHIZZ_HEAD = registerItemNoLang("whizz_head", () -> new MobHeadItem(OPBlocks.WHIZZ_HEAD.getFirst().get(), OPBlocks.WHIZZ_HEAD.getSecond().get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // emerald
    public static final RegistryObject<Item> EMERALD_MASK = registerItem("emerald_mask", ()-> new ConfigurableArmorItem(ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON), OPArmorDefinitions.EMERALD));
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = registerItem("emerald_chestplate", ()-> new ConfigurableArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON), OPArmorDefinitions.EMERALD));
    public static final RegistryObject<Item> EMERALD_LEGGINGS = registerItem("emerald_leggings", ()-> new ConfigurableArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON), OPArmorDefinitions.EMERALD));
    public static final RegistryObject<Item> EMERALD_BOOTS = registerItem("emerald_boots", ()-> new ConfigurableArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON), OPArmorDefinitions.EMERALD));
    public static final RegistryObject<Item> EMERALD_SWORD = registerItem("emerald_sword", ()-> new ConfigurableSwordItem(OPToolDefinitions.EMERALD, 3, -2.4F, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_PICKAXE = registerItem("emerald_pickaxe", ()-> new ConfigurablePickaxetem(OPToolDefinitions.EMERALD, 1, -2.8F, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_AXE = registerItem("emerald_axe", ()-> new ConfigurableAxeItem(OPToolDefinitions.EMERALD, 5, -3.0F, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_SHOVEL = registerItem("emerald_shovel", ()-> new ConfigurableShovelItem(OPToolDefinitions.EMERALD, 1.5F, -3.0F, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_HOE = registerItem("emerald_hoe", ()-> new ConfigurableHoeItem(OPToolDefinitions.EMERALD, -3, 0.0F, new Item.Properties().rarity(Rarity.UNCOMMON)));

    // Lapis
    public static final RegistryObject<Item> LAPIS_SWORD = registerItemNoLang("lapis_sword", ()-> new LapisSwordItem(new Item.Properties()));
    public static final RegistryObject<Item> LAPIS_PICKAXE = registerItemNoLang("lapis_pickaxe", ()-> new LapisPickaxeItem(new Item.Properties()));
    public static final RegistryObject<Item> LAPIS_AXE = registerItemNoLang("lapis_axe", ()-> new LapisAxeItem(new Item.Properties()));
    public static final RegistryObject<Item> LAPIS_SHOVEL = registerItemNoLang("lapis_shovel", ()-> new LapisShovelItem(new Item.Properties()));
    public static final RegistryObject<Item> LAPIS_HOE = registerItemNoLang("lapis_hoe", ()-> new LapisHoeItem(new Item.Properties()));

    // wooden
    public static final RegistryObject<Item> WOODEN_MASK = registerItem("wooden_mask", ()-> new WoodenArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_CHESTPLATE = registerItem("wooden_chestplate", ()-> new WoodenArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_COVER = registerItem("wooden_cover", ()-> new WoodenArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_BOOTS = registerItem("wooden_boots", ()-> new WoodenArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    // stone
    public static final RegistryObject<Item> STONE_HELMET = registerItem("stone_helmet", ()-> new ConfigurableArmorItem(ArmorItem.Type.HELMET, new Item.Properties(), OPArmorDefinitions.STONE));
    public static final RegistryObject<Item> STONE_CHESTPLATE = registerItem("stone_chestplate", ()-> new ConfigurableArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties(), OPArmorDefinitions.STONE));
    public static final RegistryObject<Item> STONE_LEGGINGS = registerItem("stone_leggings", ()-> new ConfigurableArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties(), OPArmorDefinitions.STONE));
    public static final RegistryObject<Item> STONE_BOOTS = registerItem("stone_boots", ()-> new ConfigurableArmorItem(ArmorItem.Type.BOOTS, new Item.Properties(), OPArmorDefinitions.STONE));

    // treasure
    public static final RegistryObject<Item> MOON_SHOES = registerItem("moon_shoes", ()-> new MoonShoesItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BLADE_OF_THE_MOUNTAIN = registerItem("blade_of_the_mountain", () -> new BladeOfTheMountainItem(new Item.Properties().rarity(Rarity.RARE)));

    // misc
    public static final RegistryObject<Item> TOMAHAWK =  registerItem("tomahawk", () -> new TomahawkItem(new Item.Properties().stacksTo(1).durability(196)));
    public static final RegistryObject<Item> KINETIC_BOMB = registerItem("kinetic_bomb", () -> new KineticBombItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> DONUT =  registerItem("donut", () -> new DonutItem(new Item.Properties()));

    // discs
    public static final RegistryObject<Item> WALTZ_OF_THE_SLUG_DISC = registerItemNoLang("waltz_of_the_slug_disc", () -> new RecordItem(15, OPSoundEvents.WALTZ_OF_THE_SLUG_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3440));
    public static final RegistryObject<Item> SLAYSER_DISC = registerItemNoLang("slayser_disc", () -> new RecordItem(15, OPSoundEvents.SLAYSER_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2300));

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        ITEM_TRANSLATIONS.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        return ITEMS.register(name, supplier);
    }

    private static RegistryObject<Item> registerSpawnEggItem(String name, RegistryObject<? extends EntityType<? extends Mob>> type, int baseColor, int spotColor) {
        return registerItem(name + "_spawn_egg", () -> new ForgeSpawnEggItem(type, baseColor, spotColor, new Item.Properties()));
    }

    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }
}
