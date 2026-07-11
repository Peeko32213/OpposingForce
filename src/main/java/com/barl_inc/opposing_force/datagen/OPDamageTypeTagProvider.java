package com.barl_inc.opposing_force.datagen;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.registry.tags.OPDamageTypeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barl_inc.opposing_force.registry.OPDamageTypes.*;

public class OPDamageTypeTagProvider extends TagsProvider<DamageType> {

    public OPDamageTypeTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, Registries.DAMAGE_TYPE, provider, OpposingForce.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider provider) {

        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(
                ELECTRIFIED,
                GLOOM_TOXIN
        );

        this.tag(DamageTypeTags.IS_PROJECTILE).add(
                TOMAHAWK,
                UMBER_DAGGER,
                LASER,
                LASER_BOLT,
                THROWN_LASER_BLADE
        );

        this.tag(DamageTypeTags.NO_IMPACT).add(
                LASER,
                SAWBLADE
        );

        this.tag(DamageTypeTags.BYPASSES_COOLDOWN).add(
                LASER_BOLT
        );

        this.tag(DamageTypeTags.WITCH_RESISTANT_TO).add(
                GLOOM_TOXIN
        );

        this.tag(OPDamageTypeTags.DAMAGES_ROLLING_TREMBLER)
                .addTag(DamageTypeTags.BYPASSES_ARMOR)
                .addTag(DamageTypeTags.BYPASSES_RESISTANCE)
                .addTag(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .addTag(DamageTypeTags.IS_DROWNING)
                .addTag(DamageTypeTags.IS_FIRE)
                .addTag(DamageTypeTags.IS_LIGHTNING);

    }
}
