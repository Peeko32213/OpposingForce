package com.unusualmodding.opposingforce.core.registry;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.item.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class OPItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            OpposingForce.MODID);

    public static final RegistryObject<ForgeSpawnEggItem> BOUNCER_SPAWN_EGG = registerSpawnEggs("bouncer_spawn_egg", OPEntities.BOUNCER, 0x0f0c18, 0xedb436);
    public static final RegistryObject<ForgeSpawnEggItem> DICER_SPAWN_EGG = registerSpawnEggs("dicer_spawn_egg", OPEntities.DICER , 0x1a1818, 0xf17eeb);
    public static final RegistryObject<ForgeSpawnEggItem> FETID_SPAWN_EGG = registerSpawnEggs("fetid_spawn_egg", OPEntities.FETID , 0x3c3625, 0xafb140);
    public static final RegistryObject<ForgeSpawnEggItem> FIRESLIME_SPAWN_EGG = registerSpawnEggs("fireslime_spawn_egg", OPEntities.FIRE_SLIME , 0xdb3709, 0xffffff);
    public static final RegistryObject<ForgeSpawnEggItem> FROWZY_SPAWN_EGG = registerSpawnEggs("frowzy_spawn_egg", OPEntities.FROWZY , 0x35313b, 0x3f759f);
    public static final RegistryObject<ForgeSpawnEggItem> GUZZLER_SPAWN_EGG = registerSpawnEggs("guzzler_spawn_egg", OPEntities.GUZZLER , 0x59316a, 0xfff067);
    public static final RegistryObject<ForgeSpawnEggItem> PALE_SPIDER_SPAWN_EGG = registerSpawnEggs("pale_spider_spawn_egg", OPEntities.PALE_SPIDER , 0xc4ac7f, 0xffd731);
    public static final RegistryObject<ForgeSpawnEggItem> RAMBLE_SPAWN_EGG = registerSpawnEggs("ramble_spawn_egg", OPEntities.RAMBLE , 0x131313, 0xffffff);
    public static final RegistryObject<ForgeSpawnEggItem> SLUG_SPAWN_EGG = registerSpawnEggs("slug_spawn_egg", OPEntities.SLUG , 0x311d16, 0x7a7139);
    public static final RegistryObject<ForgeSpawnEggItem> SPINDLE_SPAWN_EGG = registerSpawnEggs("spindle_spawn_egg", OPEntities.SPINDLE , 0x452b65, 0x3b6b64);
    public static final RegistryObject<ForgeSpawnEggItem> TERROR_SPAWN_EGG = registerSpawnEggs("terror_spawn_egg", OPEntities.TERROR , 0x070508, 0x54174c);
    public static final RegistryObject<ForgeSpawnEggItem> TREMBLER_SPAWN_EGG = registerSpawnEggs("trembler_spawn_egg", OPEntities.TREMBLER , 0x20281e, 0x86b5b4);
    public static final RegistryObject<ForgeSpawnEggItem> UMBER_SPIDER_SPAWN_EGG = registerSpawnEggs("umber_spider_spawn_egg", OPEntities.UMBER_SPIDER , 0x0e0909, 0x44a9f6);
    public static final RegistryObject<ForgeSpawnEggItem> VOLT_SPAWN_EGG = registerSpawnEggs("volt_spawn_egg", OPEntities.VOLT , 0x171b2d, 0x78f4d9);
    public static final RegistryObject<ForgeSpawnEggItem> WIZZ_SPAWN_EGG = registerSpawnEggs("wizz_spawn_egg", OPEntities.WIZZ , 0x6656bb, 0xffe7f8);

    public static final RegistryObject<Item> DEEP_SILK = ITEMS.register("deep_silk",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELECTRIC_CHARGE = ITEMS.register("electric_charge",
            () -> new ElectricChargeItem(OPEntities.ELECTRICITY_BALL, (new Item.Properties()).stacksTo(32)));

    public static final RegistryObject<Item> TOMAHAWK =  ITEMS.register("tomahawk",
            () -> new TomahawkItem(4, -2.2F, (new Item.Properties()).stacksTo(64)));

    public static final RegistryObject<Item> TESLA_BOW = ITEMS.register("tesla_bow",
            () -> new TeslaBowItem(new Item.Properties().stacksTo(1).durability(465)));

    public static final RegistryObject<Item> SLUG_EGGS = ITEMS.register("slug_eggs",
            () -> new SlugEggItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> VILE_BOULDER = ITEMS.register("vile_boulder",
            () -> new VileBoulderItem(OPTiers.VILE,  8, -2.4F, new Item.Properties()));

    private static RegistryObject<ForgeSpawnEggItem> registerSpawnEggs(String name, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        return ITEMS.register(name, () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor,new Item.Properties()));
    }

}
