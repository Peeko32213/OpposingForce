package com.peeko32213.hole.core.registry;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.item.ElectricChargeItem;
import com.peeko32213.hole.common.item.TeslaBowItem;
import com.peeko32213.hole.common.item.TomahawkItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class HoleItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            Hole.MODID);

    public static final RegistryObject<ForgeSpawnEggItem> PALE_SPIDER_SPAWN_EGG = registerSpawnEggs("pale_spider_spawn_egg",
            HoleEntities.PALE_SPIDER , 0xc4ac7f, 0xffd731);

    public static final RegistryObject<ForgeSpawnEggItem> UMBER_SPIDER_SPAWN_EGG = registerSpawnEggs("umber_spider_spawn_egg",
            HoleEntities.UMBER_SPIDER , 0x0e0909, 0x44a9f6);

    public static final RegistryObject<ForgeSpawnEggItem> RAMBLE_SPAWN_EGG = registerSpawnEggs("ramble_spawn_egg",
            HoleEntities.RAMBLE , 0x131313, 0xffffff);

    public static final RegistryObject<ForgeSpawnEggItem> DICER_SPAWN_EGG = registerSpawnEggs("dicer_spawn_egg",
            HoleEntities.DICER , 0xbb0000, 0xd354c7);

    public static final RegistryObject<ForgeSpawnEggItem> TREMBLER_SPAWN_EGG = registerSpawnEggs("trembler_spawn_egg",
            HoleEntities.TREMBLER , 0x20281e, 0x86b5b4);

    public static final RegistryObject<ForgeSpawnEggItem> TERROR_SPAWN_EGG = registerSpawnEggs("terror_spawn_egg",
            HoleEntities.TERROR , 0x070508, 0x54174c);

    public static final RegistryObject<ForgeSpawnEggItem> VOLT_SPAWN_EGG = registerSpawnEggs("volt_spawn_egg",
            HoleEntities.VOLT , 0x171b2d, 0x78f4d9);

    public static final RegistryObject<ForgeSpawnEggItem> WIZZ_SPAWN_EGG = registerSpawnEggs("wizz_spawn_egg",
            HoleEntities.WIZZ , 0x6656bb, 0xffe7f8);

    public static final RegistryObject<ForgeSpawnEggItem> HOPPER_SPAWN_EGG = registerSpawnEggs("hopper_spawn_egg",
            HoleEntities.HOPPER , 0x0f0c18, 0xedb436);

    public static final RegistryObject<ForgeSpawnEggItem> FROWZY_SPAWN_EGG = registerSpawnEggs("frowzy_spawn_egg",
            HoleEntities.FROWZY , 0x35313b, 0x3f759f);
    public static final RegistryObject<Item> DEEP_SILK = ITEMS.register("deep_silk",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELECTRIC_CHARGE = ITEMS.register("electric_charge",
            () -> new ElectricChargeItem(HoleEntities.SMALL_ELECTRICITY_BALL, new Item.Properties()));

    public static final RegistryObject<Item> TOMAHAWK =  ITEMS.register("tomahawk",
            () -> new TomahawkItem((new Item.Properties()).stacksTo(16)));

    public static final RegistryObject<Item> TESLA_BOW = ITEMS.register("tesla_bow",
            () -> new TeslaBowItem(new Item.Properties()));

    private static RegistryObject<ForgeSpawnEggItem> registerSpawnEggs(String name, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        return ITEMS.register(name, () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor,new Item.Properties()));
    }

}
