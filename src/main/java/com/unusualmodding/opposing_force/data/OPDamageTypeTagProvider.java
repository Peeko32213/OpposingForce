package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.opposing_force.registry.OPDamageTypes.*;

public class OPDamageTypeTagProvider extends TagsProvider<DamageType> {

    public OPDamageTypeTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, Registries.DAMAGE_TYPE, provider, OpposingForce.MOD_ID, helper);
    }

    protected void addTags(Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ELECTRIFIED, GLOOM_TOXIN);
        this.tag(DamageTypeTags.IS_PROJECTILE).add(TOMAHAWK);
        this.tag(DamageTypeTags.WITCH_RESISTANT_TO).add(GLOOM_TOXIN);
    }
}
