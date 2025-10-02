package com.unusualmodding.opposing_force.registry.tags;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class OPDamageTypeTags {

    public static final TagKey<DamageType> DAMAGES_ROLLING_TREMBLER = damageTypeTag("damages_rolling_trembler");

    public static TagKey<DamageType> damageTypeTag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(OpposingForce.MOD_ID, name));
    }
}
