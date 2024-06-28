package com.peeko32213.hole.core.datagen;

import com.peeko32213.hole.Hole;
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
import com.scouter.goalsmith.datagen.GoalDataBuilder;
import com.scouter.goalsmith.datagen.GoalDataProvider;
import com.scouter.goalsmith.util.GSTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static com.peeko32213.hole.Hole.prefix;

public class GoalDataGenerator extends GoalDataProvider {


    public GoalDataGenerator(PackOutput pOutput) {
        super(pOutput, Hole.MODID);
    }

    @Override
    protected void createGoalData(Consumer<GoalDataConsumer> consumer) {

        GoalDataConsumer paleSpider = new GoalDataBuilder()
                .withTargetEntity(prefix("pale_spider"))
                .addGoals(
                        new FloatGoalCodec(0),
                        new DroppingMeleeGoalCodec(1),
                        new WanderStrollUpsideDownCodec(2, 1.2D, 100),
                        new RandomLookAroundGoalCodec(6),
                        new LookAtEntityGoalCodec(7, GSTags.PLAYER, 30F, 0.02F, false)
                )
                .addTargetGoals(
                        new HurtByTargetGoalCodec(1, HoleTags.PALE_SPIDER),
                        new SmartNearestTargetGoalCodec(1, GSTags.LIVING_ENTITY, true, true)
                )
                .buildGoalDataConsumer();


        GoalDataConsumer umberSpider = new GoalDataBuilder()
                .withTargetEntity(prefix("umber_spider"))
                .addGoals(
                        new FloatGoalCodec(1),
                        new LeapAtTargetGoalCodec(3, 0.6F),
                        new WaterAvoidingRandomStrollGoalCodec(5, 0.8D, 0.001F),
                        new LookAtEntityGoalCodec(6, TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("goalsmith", "player")), 8.0F, 0.02F, false),
                        new RandomLookAroundGoalCodec(6),
                        new FearTheLightGoalCodec(0, 1.5D, 100, 10),
                        new MeleeAttackGoalCodec(4, 0.8D, false)
                )
                .addTargetGoals(
                        new HurtByTargetGoalCodec(1, HoleTags.UMBER_SPIDER),
                        new SmartNearestTargetGoalCodec(1, TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("goalsmith", "player")), true, true)
                )
                .buildGoalDataConsumer();





        consumer.accept(paleSpider);
        consumer.accept(umberSpider);

    }
}
