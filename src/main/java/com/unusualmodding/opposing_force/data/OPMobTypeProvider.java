package com.unusualmodding.opposing_force.data;

import com.unusualmodding.alkahest.data.AlkahestMobTypes;
import com.unusualmodding.alkahest.data.MobTypeStrategies;
import com.unusualmodding.alkahest.datagen.AlkahestMobTypeAssignmentProvider;
import com.unusualmodding.alkahest.datagen.MobTypeAssignmentBuilders;
import com.unusualmodding.alkahest.datagen.MobTypeAssignmentConsumer;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.data.PackOutput;

import java.util.function.Consumer;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

public class OPMobTypeProvider extends AlkahestMobTypeAssignmentProvider {

    public OPMobTypeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildAssignment(Consumer<MobTypeAssignmentConsumer> consumer) {
        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("dicer"))
                        .entity(OPEntities.DICER.get())
                        .mobTypes(AlkahestMobTypes.ASTRAL)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("rambler"))
                        .entity(OPEntities.RAMBLER.get())
                        .mobTypes(AlkahestMobTypes.ABOMINATION, AlkahestMobTypes.UNDEAD)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("slug"))
                        .entity(OPEntities.SLUG.get())
                        .mobTypes(AlkahestMobTypes.FILTH)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("frowzy"))
                        .entity(OPEntities.FROWZY.get())
                        .mobTypes(AlkahestMobTypes.UNDEAD)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("hanging_spider"))
                        .entity(OPEntities.HANGING_SPIDER.get())
                        .mobTypes(AlkahestMobTypes.ARTHROPOD)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("trembler"))
                        .entity(OPEntities.TREMBLER.get())
                        .mobTypes(AlkahestMobTypes.DRACONIC)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("guzzler"))
                        .entity(OPEntities.GUZZLER.get())
                        .mobTypes(AlkahestMobTypes.DRACONIC, AlkahestMobTypes.FIERY)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("fire_slime"))
                        .entity(OPEntities.FIRE_SLIME.get())
                        .mobTypes(AlkahestMobTypes.ELEMENTAL, AlkahestMobTypes.FIERY)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("umber_spider"))
                        .entity(OPEntities.UMBER_SPIDER.get())
                        .mobTypes(AlkahestMobTypes.ARTHROPOD)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("volt"))
                        .entity(OPEntities.VOLT.get())
                        .mobTypes(AlkahestMobTypes.CRYPTIC)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("terror"))
                        .entity(OPEntities.TERROR.get())
                        .mobTypes(AlkahestMobTypes.AQUATIC)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );

        consumer.accept(
                MobTypeAssignmentBuilders.builder(modPrefix("whizz"))
                        .entity(OPEntities.WHIZZ.get())
                        .mobTypes(AlkahestMobTypes.EARTHEN)
                        .strategy(MobTypeStrategies.firstNonNeutral())
                        .build()
        );
    }
}
