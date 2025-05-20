package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.opposing_force.registry.OPDamageTypes.ELECTRIFIED;
import static com.unusualmodding.opposing_force.registry.OPDamageTypes.TOMAHAWK;

public class OPDamageTypeTagProvider extends TagsProvider<DamageType> {


    public OPDamageTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, Registries.DAMAGE_TYPE, future, OpposingForce.MOD_ID, helper);
    }

    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ELECTRIFIED);
        this.tag(DamageTypeTags.DAMAGES_HELMET);
        this.tag(DamageTypeTags.IS_PROJECTILE).add(ELECTRIFIED).add(TOMAHAWK);
        this.tag(DamageTypeTags.IS_FIRE);
        this.tag(DamageTypeTags.BYPASSES_RESISTANCE).add(ELECTRIFIED);
        this.tag(DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL);
        this.tag(DamageTypeTags.IS_FALL);
        this.tag(DamageTypeTags.NO_ANGER);
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY);
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS);
        this.tag(DamageTypeTags.WITCH_RESISTANT_TO).add(ELECTRIFIED);
    }

    private static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, OpposingForce.modPrefix(name));
    }
}
