package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.entity.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class OPEntityPlacement {

    public  static void entityPlacement() {
        SpawnPlacements.register(OPEntities.PALE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PaleSpiderEntity::canFirstTierSpawn);
        SpawnPlacements.register(OPEntities.UMBER_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UmberSpiderEntity::canSecondTierSpawn);
        SpawnPlacements.register(OPEntities.TREMBLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TremblerEntity::canFirstTierSpawn);
        SpawnPlacements.register(OPEntities.DICER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DicerEntity::canSecondTierSpawn);
        SpawnPlacements.register(OPEntities.RAMBLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RambleEntity::canSecondTierSpawn);
        SpawnPlacements.register(OPEntities.VOLT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VoltEntity::canSecondTierSpawn);
        SpawnPlacements.register(OPEntities.TERROR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TerrorEntity::canWaterSpawn);
        SpawnPlacements.register(OPEntities.WHIZZ.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WhizzEntity::canWhizzSpawn);
        SpawnPlacements.register(OPEntities.FROWZY.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrowzyEntity::canFirstTierSpawn);
        SpawnPlacements.register(OPEntities.GUZZLER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GuzzlerEntity::canSecondTierSpawn);
        SpawnPlacements.register(OPEntities.SLUG.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SlugEntity::canFirstTierSpawn);

    }

    public static boolean rollSpawn(int rolls, RandomSource random, MobSpawnType reason){
        if(reason == MobSpawnType.SPAWNER){
            return true;
        }else{
            return rolls <= 0 || random.nextInt(rolls) == 0;
        }
    }
}
