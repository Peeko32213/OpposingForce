package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.*;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(OPEntities.DICER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dicer::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.FROWZY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frowzy::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.GUZZLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guzzler::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.PALE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PaleSpider::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.RAMBLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ramble::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.SLUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Slug::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.TERROR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Terror::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.TREMBLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Trembler::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.UMBER_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UmberSpider::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.VOLT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Volt::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(OPEntities.WHIZZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Whizz::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(OPEntities.DICER.get(), Dicer.createAttributes().build());
        event.put(OPEntities.EMERALDFISH.get(), Emeraldfish.createAttributes().build());
        event.put(OPEntities.FIRE_SLIME.get(), FireSlime.createAttributes().build());
        event.put(OPEntities.FROWZY.get(), Frowzy.createAttributes().build());
        event.put(OPEntities.GUZZLER.get(), Guzzler.createAttributes().build());
        event.put(OPEntities.HAUNTED_TOOL.get(), HauntedTool.createAttributes().build());
        event.put(OPEntities.PALE_SPIDER.get(), PaleSpider.createAttributes().build());
        event.put(OPEntities.RAMBLE.get(), Ramble.createAttributes().build());
        event.put(OPEntities.SLUG.get(), Slug.createAttributes().build());
        event.put(OPEntities.TERROR.get(), Terror.createAttributes().build());
        event.put(OPEntities.TREMBLER.get(), Trembler.createAttributes().build());
        event.put(OPEntities.UMBER_SPIDER.get(), UmberSpider.createAttributes().build());
        event.put(OPEntities.VOLT.get(), Volt.createAttributes().build());
        event.put(OPEntities.WHIZZ.get(), Whizz.createAttributes().build());
    }
}
