package com.unusualmodding.opposingforce.core.datagen.client;

import com.mojang.logging.LogUtils;
import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.core.registry.*;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(PackOutput output) {
        super(output, OpposingForce.MODID, "en_us");
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    protected void addTranslations(){

        addTabName(OPCreativeTabs.TAB.get(), "Opposing Force");

        addEntityType(OPEntities.PALE_SPIDER, "Pale Spider");
        addItem(OPItems.PALE_SPIDER_SPAWN_EGG, "Pale Spider Spawn Egg");

        addEntityType(OPEntities.UMBER_SPIDER, "Umber Spider");
        addItem(OPItems.UMBER_SPIDER_SPAWN_EGG, "Umber Spider Spawn Egg");
        addItem(OPItems.DEEP_SILK, "Deep Silk");

        addSound(OPSounds.UMBER_SPIDER_DEATH, "Umber Spider dies");
        addSound(OPSounds.UMBER_SPIDER_HURT, "Umber Spider hurts");
        addSound(OPSounds.UMBER_SPIDER_IDLE, "Umber Spider groans");

        addEntityType(OPEntities.RAMBLE, "Ramble");
        addItem(OPItems.RAMBLE_SPAWN_EGG, "Ramble Spawn Egg");

        addSound(OPSounds.RAMBLE_DEATH, "Ramble dies");
        addSound(OPSounds.RAMBLE_HURT, "Ramble hurts");
        addSound(OPSounds.RAMBLE_IDLE, "Ramble clatters");

        addSound(OPSounds.DICER_DEATH, "Dicer dies");
        addSound(OPSounds.DICER_HURT, "Dicer hurts");
        addSound(OPSounds.DICER_IDLE, "Dicer screams");
        addSound(OPSounds.DICER_ATTACK, "Dicer slashes");

        addSound(OPSounds.VOLT_DEATH, "Volt dies");
        addSound(OPSounds.VOLT_HURT, "Volt hurts");
        addSound(OPSounds.VOLT_IDLE, "Volt hums");
        addSound(OPSounds.VOLT_SHOOT, "Volt shoots");

        addSound(OPSounds.ELECTRIC_CHARGE, "Electricity whirls");
        addSound(OPSounds.ELECTRIC_CHARGE_DISSIPATE, "Electricity dissipates");

        addSound(OPSounds.TESLA_BOW_CHARGED, "Tesla Bow loads");
        addSound(OPSounds.TESLA_BOW_SHOOT, "Tesla Bow fires");

        addEntityType(OPEntities.DICER, "Dicer");
        addItem(OPItems.DICER_SPAWN_EGG, "Dicer Spawn Egg");

        addEntityType(OPEntities.TREMBLER, "Trembler");
        addItem(OPItems.TREMBLER_SPAWN_EGG, "Trembler Spawn Egg");

        addEntityType(OPEntities.TERROR, "Terror");
        addItem(OPItems.TERROR_SPAWN_EGG, "Terror Spawn Egg");

        addEntityType(OPEntities.VOLT, "Volt");
        addItem(OPItems.VOLT_SPAWN_EGG, "Volt Spawn Egg");
        addEntityType(OPEntities.ELECTRICITY_BALL, "Ball of Electricity");
        addItem(OPItems.ELECTRIC_CHARGE, "Electric Charge");
        addEffect(OPEffects.ELECTRIFIED, "Electrified");
        add("death.attack.hole.electrified", "%s met a shocking end");
        add("death.attack.hole.electrified.player", "%s met a shocking end by %s");
        addItem(OPItems.TESLA_BOW, "Tesla Bow");

        addEntityType(OPEntities.WIZZ, "Whizz");
        addItem(OPItems.WIZZ_SPAWN_EGG, "Whizz Spawn Egg");

        addEntityType(OPEntities.HOPPER, "Bouncer");
        addItem(OPItems.HOPPER_SPAWN_EGG, "Bouncer Spawn Egg");

        addEntityType(OPEntities.FROWZY, "Frowzy");
        addItem(OPItems.FROWZY_SPAWN_EGG, "Frowzy Spawn Egg");

        addEntityType(OPEntities.FIRE_SLIME, "Aflame Slime");
        addItem(OPItems.FIRESLIME_SPAWN_EGG, "Aflame Slime Spawn Egg");

        addEntityType(OPEntities.GUZZLER, "Guzzler");
        addItem(OPItems.GUZZLER_SPAWN_EGG, "Guzzler Spawn Egg");

        addBlock(OPBlocks.CAVE_PATTY, "Cave Patty");
        addBlock(OPBlocks.COPPER_ENOKI, "Copper Enoki");
        addBlock(OPBlocks.RAINCAP, "Rain Cap");
        addBlock(OPBlocks.CREAM_CAP, "Cream Cap");
        addBlock(OPBlocks.CHICKEN_OF_THE_CAVES, "Chicken of The Caves");
        addBlock(OPBlocks.PRINCESS_JELLY, "Princess Jelly");
        addBlock(OPBlocks.BLUE_TRUMPET, "Blue Trumpet");
        addBlock(OPBlocks.POWDER_GNOME, "Powder Gnome");
        addBlock(OPBlocks.BLACKCAP, "Black Cap");
        addBlock(OPBlocks.CAP_OF_EYE, "Cap of Eye");
        addBlock(OPBlocks.GREEN_FUNK, "Green Funk");
        addBlock(OPBlocks.LIME_NUB, "Lime Nub");
        addBlock(OPBlocks.POP_CAP, "Pop Cap");
        addBlock(OPBlocks.PURPLE_KNOB, "Purple Knob");
        addBlock(OPBlocks.QUEEN_IN_PURPLE, "Queen in Magenta");
        addBlock(OPBlocks.SLATESHROOM, "Slate Shroom");
        addBlock(OPBlocks.SLIPPERY_TOP, "Slippery Top");
        addBlock(OPBlocks.WHITECAP, "White Cap");

        addItem(OPItems.TOMAHAWK, "Tomahawk");
        add("death.attack.hole.tomahawk", "%s was domed by %s");

        addEntityType(OPEntities.SLUG, "Slug");
        addItem(OPItems.SLUG_SPAWN_EGG, "Slug Spawn Egg");
        addItem(OPItems.SLUG_EGGS, "Slug Eggs");

        addItem(OPItems.VILE_BOULDER, "Vile Boulder");

        addEntityType(OPEntities.FETID, "Fetid");
        addItem(OPItems.FETID_SPAWN_EGG, "Fetid Spawn Egg");

        addEntityType(OPEntities.SPINDLE, "Spindle");
        addItem(OPItems.SPINDLE_SPAWN_EGG, "Spindle Spawn Egg");
    }

    @Override
    public String getName() {
        return  OpposingForce.MODID  + " Languages: en_us";
    }

    public void addBETranslatable(String beName,String name){
        add(OpposingForce.MODID + ".blockentity." + beName, name);
    }

    public void addSound(Supplier<? extends SoundEvent> key, String name){
        add(OpposingForce.MODID + ".sound.subtitle." + key.get().getLocation().getPath(), name);
    }

    public void addTabName(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void add(CreativeModeTab key, String name) {
        add(key.getDisplayName().getString(), name);
    }

    public void addPotion(Supplier<? extends Potion> key, String name, String regName) {
        add(key.get(), name, regName);
    }

    public void addEnchantDescription(String description, Enchantment enchantment){
        add(enchantment.getDescriptionId() + ".desc", description);
    }

    public void add(Potion key, String name, String regName) {
        add("item.minecraft.potion.effect." + regName, name);
        add("item.minecraft.splash_potion.effect." + regName, "Splash " + name);
        add("item.minecraft.lingering_potion.effect." + regName, "Lingering " + name);
    }
}
