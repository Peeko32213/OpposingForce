package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.items.*;
import com.unusualmodding.opposing_force.registry.enums.OPArmorMaterials;
import com.unusualmodding.opposing_force.registry.enums.OPItemTiers;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OPItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OpposingForce.MOD_ID);
    public static List<RegistryObject<? extends Item>> AUTO_TRANSLATE = new ArrayList<>();

    public static final RegistryObject<Item> DICER_SPAWN_EGG = registerSpawnEggItem("dicer", OPEntities.DICER , 0x1a1818, 0xf17eeb);
    public static final RegistryObject<Item> FIRE_SLIME_SPAWN_EGG = registerSpawnEggItem("fire_slime", OPEntities.FIRE_SLIME , 0xdb3709, 0xfee44e);
    public static final RegistryObject<Item> FROWZY_SPAWN_EGG = registerSpawnEggItem("frowzy", OPEntities.FROWZY , 0x35313b, 0x3f759f);
    public static final RegistryObject<Item> GUZZLER_SPAWN_EGG = registerSpawnEggItem("guzzler", OPEntities.GUZZLER , 0x06030b, 0x59316a);
    public static final RegistryObject<Item> PALE_SPIDER_SPAWN_EGG = registerSpawnEggItem("pale_spider", OPEntities.PALE_SPIDER , 0xbb9c75, 0xffd731);
    public static final RegistryObject<Item> RAMBLE_SPAWN_EGG = registerSpawnEggItem("ramble", OPEntities.RAMBLE , 0x131313, 0xffffff);
    public static final RegistryObject<Item> SLUG_SPAWN_EGG = registerSpawnEggItem("slug", OPEntities.SLUG , 0x311d16, 0x7a7139);
    public static final RegistryObject<Item> TERROR_SPAWN_EGG = registerSpawnEggItem("terror", OPEntities.TERROR , 0x070508, 0x54174c);
    public static final RegistryObject<Item> TREMBLER_SPAWN_EGG = registerSpawnEggItem("trembler", OPEntities.TREMBLER , 0x273030, 0x6d9288);
    public static final RegistryObject<Item> UMBER_SPIDER_SPAWN_EGG = registerSpawnEggItem("umber_spider", OPEntities.UMBER_SPIDER , 0x241d2c, 0x44a9f6);
    public static final RegistryObject<Item> VOLT_SPAWN_EGG = registerSpawnEggItem("volt", OPEntities.VOLT , 0x14152b, 0x61e7e3);
    public static final RegistryObject<Item> WHIZZ_SPAWN_EGG = registerSpawnEggItem("whizz", OPEntities.WHIZZ, 0x8a6ce0, 0xffe7f8);

    public static final RegistryObject<Item> DEEP_SILK = registerItem("deep_silk", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ELECTRIC_CHARGE = registerItem("electric_charge", () -> new ElectricChargeItem(OPEntities.ELECTRIC_CHARGE, (new Item.Properties())));
    public static final RegistryObject<Item> TOMAHAWK =  registerItem("tomahawk", () -> new TomahawkItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> TESLA_BOW = registerItem("tesla_bow", () -> new TeslaBowItem(new Item.Properties().stacksTo(1).durability(465)));
    public static final RegistryObject<Item> SLUG_EGGS = registerItem("slug_eggs", () -> new SlugEggItem(new Item.Properties()));
    public static final RegistryObject<Item> VILE_BOULDER = registerItem("vile_boulder", () -> new VileBoulderItem(OPItemTiers.VILE,  7, -3.1F, new Item.Properties()));

    public static final RegistryObject<Item> DEEPWOVEN_HELMET = registerItemNoLang("deepwoven_helmet", ()-> new DeepwovenArmorItem(OPArmorMaterials.DEEPWOVEN, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> DEEPWOVEN_CHESTPLATE = registerItemNoLang("deepwoven_chestplate", ()-> new DeepwovenArmorItem(OPArmorMaterials.DEEPWOVEN, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> DEEPWOVEN_LEGGINGS = registerItemNoLang("deepwoven_leggings", ()-> new DeepwovenArmorItem(OPArmorMaterials.DEEPWOVEN, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> DEEPWOVEN_BOOTS = registerItemNoLang("deepwoven_boots", ()-> new DeepwovenArmorItem(OPArmorMaterials.DEEPWOVEN, ArmorItem.Type.BOOTS, new Item.Properties()));

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        AUTO_TRANSLATE.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        return item;
    }

    private static RegistryObject<Item> registerSpawnEggItem(String name, RegistryObject type, int baseColor, int spotColor) {
        return registerItem(name + "_spawn_egg", () -> new ForgeSpawnEggItem(type, baseColor, spotColor, new Item.Properties()));
    }

}
