package com.peeko32213.hole.core.registry;

import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.common.entity.EntityUmberSpider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class HoleEntityPlacement {

    public  static void entityPlacement() {
        //SpawnPlacements.register(UPEntities.STETHACANTHUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityStethacanthus::checkSurfaceWaterDinoSpawnRules);
        //SpawnPlacements.register(UPEntities.BEELZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBaseDinosaurAnimal::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(HoleEntities.PALE_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityPaleSpider::canFirstTierSpawn);
        SpawnPlacements.register(HoleEntities.UMBER_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityUmberSpider::canSecondTierSpawn);
    }

    public static boolean rollSpawn(int rolls, RandomSource random, MobSpawnType reason){
        if(reason == MobSpawnType.SPAWNER){
            return true;
        }else{
            return rolls <= 0 || random.nextInt(rolls) == 0;
        }
    }
}
