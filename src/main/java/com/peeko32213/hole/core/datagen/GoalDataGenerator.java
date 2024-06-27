package com.peeko32213.hole.core.datagen;

import com.peeko32213.hole.common.entity.goal.codec.DroppingMeleeGoalCodec;
import com.peeko32213.hole.common.entity.goal.codec.FearTheLightGoalCodec;
import com.peeko32213.hole.common.entity.goal.codec.WanderStrollUpsideDownCodec;
import com.peeko32213.hole.common.entity.goal.codec.target.SmartNearestTargetGoalCodec;
import com.peeko32213.hole.core.utils.HoleTags;
import com.scouter.goalsmith.data.GoalData;
import com.scouter.goalsmith.data.GoalOperation;
import com.scouter.goalsmith.data.TargetGoalOperation;
import com.scouter.goalsmith.data.goalcodec.*;
import com.scouter.goalsmith.data.goalcodec.targetgoalcodec.HurtByTargetGoalCodec;
import com.scouter.goalsmith.data.operation.goal.AddOperation;
import com.scouter.goalsmith.data.operation.target.AddTargetOperation;
import com.scouter.goalsmith.datagen.EntityDataProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static com.peeko32213.hole.Hole.prefix;

public class GoalDataGenerator extends EntityDataProvider {


    public GoalDataGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildPuppets(Consumer<EntityDataConsumer> consumer) {

        List<GoalOperation> paleSpiderOperations = new ArrayList<>();
        paleSpiderOperations.add(new AddOperation(
                List.of(
                        new FloatGoalCodec(0),
                        new DroppingMeleeGoalCodec(1),
                        new WanderStrollUpsideDownCodec(2, 1.2D, 100),
                        new RandomLookAroundGoalCodec(6),
                        new LookAtEntityGoalCodec(7, TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("goalsmith", "player")), 30F, 0.02F, false)
                )
        ));

        List<TargetGoalOperation> paleSpiderTargetGoalOperations = new ArrayList<>();
        paleSpiderTargetGoalOperations.add(
                new AddTargetOperation(
                        List.of(
                                new HurtByTargetGoalCodec(1, HoleTags.PALE_SPIDER),
                                new SmartNearestTargetGoalCodec(1, TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("goalsmith", "player")), true, true)
                        )
                )
        );


        List<GoalOperation> umberSpiderOperations = new ArrayList<>();
        umberSpiderOperations.add(new AddOperation(
                List.of(
                        new FloatGoalCodec(1),
                        new LeapAtTargetGoalCodec(3, 0.6F),
                        new WaterAvoidingRandomStrollGoalCodec(5, 0.8D, 0.001F),
                        new LookAtEntityGoalCodec(6, TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("goalsmith", "player")), 8.0F, 0.02F, false),
                        new RandomLookAroundGoalCodec(6),
                        new FearTheLightGoalCodec(0, 1.5D, 100, 10),
                        new MeleeAttackGoalCodec(4, 0.8D, false)
                        )
        ));

        List<TargetGoalOperation> umberSpiderTargetGoalOperations = new ArrayList<>();
        umberSpiderTargetGoalOperations.add(
                new AddTargetOperation(
                        List.of(
                                new HurtByTargetGoalCodec(1, HoleTags.UMBER_SPIDER),
                                new SmartNearestTargetGoalCodec(1, TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("goalsmith", "player")), true, true)
                        )
                )
        );

        GoalData paleSpiderData = new GoalData(prefix("pale_spider"), paleSpiderOperations, paleSpiderTargetGoalOperations, Collections.emptyList());
        GoalData umberSpiderData = new GoalData(prefix("umber_spider"), umberSpiderOperations, umberSpiderTargetGoalOperations, Collections.emptyList());

        consumer.accept(new EntityDataConsumer(prefix("pale_spider"), paleSpiderData));
        consumer.accept(new EntityDataConsumer(prefix("umber_spider"), umberSpiderData));

    }
}
