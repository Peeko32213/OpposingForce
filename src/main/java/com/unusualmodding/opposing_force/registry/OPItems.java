package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.items.*;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OPItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OpposingForce.MOD_ID);
    public static List<RegistryObject<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

    public static final RegistryObject<Item> OPPOSING_FORCE =  registerItem("opposing_force", () -> new Item(new Item.Properties()));

    // Dicer
    public static final RegistryObject<Item> DICER_SPAWN_EGG = registerSpawnEggItem("dicer", OPEntities.DICER , 0x1c0d1c, 0x3850f9);
    public static final RegistryObject<Item> DICER_LENS = registerItem("dicer_lens", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLASTER = registerItem("blaster", () -> new BlasterItem(new Item.Properties().stacksTo(1).durability(651)));
    public static final RegistryObject<Item> DICER_HEAD = registerItemNoLang("dicer_head", () -> new MobHeadItem(OPBlocks.DICER_HEAD.get(), OPBlocks.DICER_WALL_HEAD.get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // Fire Slime
    public static final RegistryObject<Item> FIRE_SLIME_SPAWN_EGG = registerSpawnEggItem("fire_slime", OPEntities.FIRE_SLIME , 0xdb3709, 0xfee44e);
    public static final RegistryObject<Item> FIRE_GEL = registerItem("fire_gel", () -> new Item(foodItem(OPFoodValues.FIRE_GEL)));

    // Frowzy
    public static final RegistryObject<Item> FROWZY_SPAWN_EGG = registerSpawnEggItem("frowzy", OPEntities.FROWZY , 0x35313b, 0x3f759f);
    public static final RegistryObject<Item> FROWZY_HEAD = registerItemNoLang("frowzy_head", () -> new MobHeadItem(OPBlocks.FROWZY_HEAD.get(), OPBlocks.FROWZY_WALL_HEAD.get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // Guzzler
    public static final RegistryObject<Item> GUZZLER_SPAWN_EGG = registerSpawnEggItem("guzzler", OPEntities.GUZZLER , 0x06030b, 0x59316a);
    public static final RegistryObject<Item> GUZZLER_SCALES = registerItem("guzzler_scales", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFERNO_PIE_SLICE = registerItemNoLang("inferno_pie_slice", () -> new Item(foodItem(OPFoodValues.INFERNO_PIE)));
    public static final RegistryObject<Item> INFERNO_STAFF = registerItem("inferno_staff", () -> new InfernoStaffItem(new Item.Properties().stacksTo(1).defaultDurability(236)));
    public static final RegistryObject<Item> FIRE_BOMB = registerItem("fire_bomb", () -> new FireBombItem(new Item.Properties().stacksTo(16)));

    // Hanging Spider
    public static final RegistryObject<Item> HANGING_SPIDER_SPAWN_EGG = registerSpawnEggItem("hanging_spider", OPEntities.HANGING_SPIDER, 0x2c231e, 0xf5e83b);

    // Nymph
    public static final RegistryObject<Item> NYMPH_SPAWN_EGG = registerSpawnEggItem("nymph", OPEntities.NYMPH , 0x074230, 0xff0000);


    // Rambler
    public static final RegistryObject<Item> RAMBLER_SPAWN_EGG = registerSpawnEggItem("rambler", OPEntities.RAMBLER, 0xb0ac8f, 0x998d6d);
    public static final RegistryObject<Item> RAMBLE_SKULL = registerItemNoLang("ramble_skull", () -> new MobHeadItem(OPBlocks.RAMBLE_SKULL.get(), OPBlocks.RAMBLE_WALL_SKULL.get(), (new Item.Properties()).rarity(Rarity.UNCOMMON), Direction.DOWN));

    // Slug
    public static final RegistryObject<Item> SLUG_SPAWN_EGG = registerSpawnEggItem("slug", OPEntities.SLUG , 0x7a7139, 0x2e2411);
    public static final RegistryObject<Item> VILE_BOULDER = registerItem("vile_boulder", () -> new VileBoulderItem(new Item.Properties()));
    public static final RegistryObject<Item> VILE_STAFF = registerItem("vile_staff", () -> new VileStaffItem(new Item.Properties().stacksTo(1).defaultDurability(224)));

    // Terror
    public static final RegistryObject<Item> TERROR_SPAWN_EGG = registerSpawnEggItem("terror", OPEntities.TERROR , 0x074230, 0xff0000);

    // Trembler
    public static final RegistryObject<Item> TREMBLER_SPAWN_EGG = registerSpawnEggItem("trembler", OPEntities.TREMBLER , 0x273030, 0x6d9288);

    // Umber Spider
    public static final RegistryObject<Item> UMBER_SPIDER_SPAWN_EGG = registerSpawnEggItem("umber_spider", OPEntities.UMBER_SPIDER , 0x241d2c, 0x44a9f6);
    public static final RegistryObject<Item> DEEP_SILK = registerItem("deep_silk", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEEPWOVEN_HAT = registerItem("deepwoven_hat", ()-> new DeepwovenArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> DEEPWOVEN_TUNIC = registerItem("deepwoven_tunic", ()-> new DeepwovenArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> DEEPWOVEN_PANTS = registerItem("deepwoven_pants", ()-> new DeepwovenArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> DEEPWOVEN_BOOTS = registerItem("deepwoven_boots", ()-> new DeepwovenArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> UMBER_FANG = registerItem("umber_fang", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UMBER_DAGGER = registerItem("umber_dagger", () -> new UmberDaggerItem(new Item.Properties().stacksTo(1)));

    // Volt
    public static final RegistryObject<Item> VOLT_SPAWN_EGG = registerSpawnEggItem("volt", OPEntities.VOLT , 0x14152b, 0x61e7e3);
    public static final RegistryObject<Item> ELECTRIC_CHARGE = registerItem("electric_charge", () -> new ElectricChargeItem(OPEntities.ELECTRIC_CHARGE, (new Item.Properties())));
    public static final RegistryObject<Item> TESLA_CANNON = registerItem("tesla_cannon", () -> new TeslaCannonItem(new Item.Properties().stacksTo(1).durability(465)));
    public static final RegistryObject<Item> SPARK_BLADE = registerItem("spark_blade", () -> new SparkBladeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LIGHTNING_BOMB = registerItem("lightning_bomb", () -> new LightningBombItem(new Item.Properties().stacksTo(16)));

    // Whizz
    public static final RegistryObject<Item> WHIZZ_SPAWN_EGG = registerSpawnEggItem("whizz", OPEntities.WHIZZ, 0x8a6ce0, 0xffe7f8);
    public static final RegistryObject<Item> CAPTURED_WHIZZ = registerItem("captured_whizz", () -> new MobItem(OPEntities.WHIZZ::get, SoundEvents.AMETHYST_BLOCK_RESONATE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WHIZZ_BOMB = registerItem("whizz_bomb", () -> new WhizzBombItem(new Item.Properties().stacksTo(16)));

    // emerald
    public static final RegistryObject<Item> EMERALD_MASK = registerItem("emerald_mask", ()-> new EmeraldArmorItem(ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = registerItem("emerald_chestplate", ()-> new EmeraldArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_LEGGINGS = registerItem("emerald_leggings", ()-> new EmeraldArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_BOOTS = registerItem("emerald_boots", ()-> new EmeraldArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_SWORD = registerItem("emerald_sword", ()-> new EmeraldSwordItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_PICKAXE = registerItem("emerald_pickaxe", ()-> new EmeraldPickaxeItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_AXE = registerItem("emerald_axe", ()-> new EmeraldAxeItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_SHOVEL = registerItem("emerald_shovel", ()-> new EmeraldShovelItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EMERALD_HOE = registerItem("emerald_hoe", ()-> new EmeraldHoeItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    // wooden
    public static final RegistryObject<Item> WOODEN_MASK = registerItem("wooden_mask", ()-> new WoodenArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_CHESTPLATE = registerItem("wooden_chestplate", ()-> new WoodenArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_COVER = registerItem("wooden_cover", ()-> new WoodenArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_BOOTS = registerItem("wooden_boots", ()-> new WoodenArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    // stone
    public static final RegistryObject<Item> STONE_HELMET = registerItem("stone_helmet", ()-> new StoneArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> STONE_CHESTPLATE = registerItem("stone_chestplate", ()-> new StoneArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> STONE_LEGGINGS = registerItem("stone_leggings", ()-> new StoneArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> STONE_BOOTS = registerItem("stone_boots", ()-> new StoneArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    // treasure
    public static final RegistryObject<Item> MOON_SHOES = registerItem("moon_shoes", ()-> new MoonShoesItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BLADE_OF_THE_MOUNTAIN = registerItem("blade_of_the_mountain", () -> new BladeOfTheMountainItem(new Item.Properties().rarity(Rarity.RARE)));

    // misc
    public static final RegistryObject<Item> TOMAHAWK =  registerItem("tomahawk", () -> new TomahawkItem(new Item.Properties().stacksTo(1).durability(196)));
    public static final RegistryObject<Item> KINETIC_BOMB = registerItem("kinetic_bomb", () -> new KineticBombItem(new Item.Properties().stacksTo(16)));

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        ITEM_TRANSLATIONS.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        return item;
    }

    private static RegistryObject<Item> registerSpawnEggItem(String name, RegistryObject<? extends EntityType<? extends Mob>> type, int baseColor, int spotColor) {
        return registerItem(name + "_spawn_egg", () -> new ForgeSpawnEggItem(type, baseColor, spotColor, new Item.Properties()));
    }

    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }
}
