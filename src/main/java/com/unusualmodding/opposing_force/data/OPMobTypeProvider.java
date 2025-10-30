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
    }
}
