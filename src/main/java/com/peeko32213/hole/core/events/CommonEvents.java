package com.peeko32213.hole.core.events;


import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.common.entity.EntityUmberSpider;
import com.peeko32213.hole.common.entity.util.DroppingMeleeGoal;
import com.peeko32213.hole.common.entity.util.FearTheLightGoal;
import com.peeko32213.hole.common.entity.util.SmartNearestTargetGoal;
import com.peeko32213.hole.common.entity.util.WanderStrollUpsideDown;
import com.peeko32213.hole.core.registry.HoleEntities;
import com.peeko32213.hole.core.registry.HoleEntityPlacement;
import com.scouter.goalsmith.data.GoalMappings;
import com.scouter.goalsmith.data.TargetGoalMappings;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.peeko32213.hole.Hole.prefix;

@Mod.EventBusSubscriber(modid = Hole.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HoleEntities.PALE_SPIDER.get(), EntityPaleSpider.createAttributes().build());
        event.put(HoleEntities.UMBER_SPIDER.get(), EntityUmberSpider.createAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            TargetGoalMappings.NAMED_TARGET_GOALS.put(prefix("smart_nearest_target_goal"), SmartNearestTargetGoal.class);
            GoalMappings.NAMED_GOALS.put(prefix("dropping_melee_goal"), SmartNearestTargetGoal.class);
            GoalMappings.NAMED_GOALS.put(prefix("fear_the_light_goal"), FearTheLightGoal.class);
            GoalMappings.NAMED_GOALS.put(prefix("wander_stroll_upside_down_goal"), WanderStrollUpsideDown.class);

        });
        event.enqueueWork(HoleEntityPlacement::entityPlacement);

    }
}
