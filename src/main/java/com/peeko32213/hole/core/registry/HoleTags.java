package com.peeko32213.hole.core.registry;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.core.registry.util.TagUtilities;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class HoleTags {

    public static final TagKey<Block> HOLE_MUSHROOM_BLOCKS = registerBlockTag("hole_mushroom_blocks");


    public static final TagKey<EntityType<?>> PALE_SPIDER = registerEntityTag("pale_spider");
    public static final TagKey<EntityType<?>> UMBER_SPIDER = registerEntityTag("umber_spider");


    public static final TagKey<Biome> IS_DEEP_UNDERGROUND = registerBiomeTag("is_deep_underground");
    public static final TagKey<Biome> IS_UNDERGROUND = registerBiomeTag("is_underground");

    public static final TagKey<Biome> HAS_DICER = registerBiomeTag("has_monster/dicer");
    public static final TagKey<Biome> HAS_PALE_SPIDER = registerBiomeTag("has_monster/pale_spider");
    public static final TagKey<Biome> HAS_RAMBLE = registerBiomeTag("has_monster/ramble");
    public static final TagKey<Biome> HAS_TREMBLE = registerBiomeTag("has_monster/tremble");
    public static final TagKey<Biome> HAS_UMBER_SPIDER = registerBiomeTag("has_monster/umber_spider");


    public static final TagKey<Biome> WITH_DEFAULT_MONSTER_SPAWNS = TagUtilities.specialBiomeTag("forge", "with_default_monster_spawns");



    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(Hole.MODID, name));
    }

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(Hole.MODID, name));
    }

    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Hole.MODID, name));
    }

    private static TagKey<Instrument> registerInstrument(String name) {
        return TagKey.create(Registries.INSTRUMENT, new ResourceLocation(Hole.MODID, name));
    }

    private static TagKey<Biome> registerBiomeTag(String name){
        return TagKey.create(Registries.BIOME, new ResourceLocation(Hole.MODID, name));

    }
}
