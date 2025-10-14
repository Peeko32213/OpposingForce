package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.*;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.unusualmodding.opposing_force.registry.OPEntities.*;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(DICER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dicer::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(FROWZY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frowzy::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(GUZZLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guzzler::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(HANGING_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HangingSpider::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(RAMBLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ramble::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(SLUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Slug::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(TERROR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Terror::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(TREMBLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Trembler::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(UMBER_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UmberSpider::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(VOLT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Volt::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(DICER.get(), Dicer.createAttributes().build());
        event.put(EMERALDFISH.get(), Emeraldfish.createAttributes().build());
        event.put(FIRE_SLIME.get(), FireSlime.createAttributes().build());
        event.put(FROWZY.get(), Frowzy.createAttributes().build());
        event.put(GUZZLER.get(), Guzzler.createAttributes().build());
        event.put(HAUNTED_TOOL.get(), HauntedTool.createAttributes().build());
        event.put(HANGING_SPIDER.get(), HangingSpider.createAttributes().build());
        event.put(RAMBLE.get(), Ramble.createAttributes().build());
        event.put(SLUG.get(), Slug.createAttributes().build());
        event.put(TERROR.get(), Terror.createAttributes().build());
        event.put(TREMBLER.get(), Trembler.createAttributes().build());
        event.put(UMBER_SPIDER.get(), UmberSpider.createAttributes().build());
        event.put(VOLT.get(), Volt.createAttributes().build());
        event.put(WHIZZ.get(), Whizz.createAttributes().build());
    }
}
