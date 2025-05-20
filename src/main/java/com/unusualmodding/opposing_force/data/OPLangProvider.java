package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OPCreativeTabs;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Objects;
import java.util.function.Supplier;

public class OPLangProvider extends LanguageProvider {

    public OPLangProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), OpposingForce.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations(){

        addTabName(OPCreativeTabs.TAB.get(), "Opposing Force");

        // blocks
        OPBlocks.AUTO_TRANSLATE.forEach(this::forBlock);

        addEntityType(OPEntities.PALE_SPIDER, "Pale Spider");
        addItem(OPItems.PALE_SPIDER_SPAWN_EGG, "Pale Spider Spawn Egg");

        addEntityType(OPEntities.UMBER_SPIDER, "Umber Spider");
        addItem(OPItems.UMBER_SPIDER_SPAWN_EGG, "Umber Spider Spawn Egg");
        addItem(OPItems.DEEP_SILK, "Deep Silk");

        addEntityType(OPEntities.RAMBLE, "Ramble");
        addItem(OPItems.RAMBLE_SPAWN_EGG, "Ramble Spawn Egg");

        sound(OPSounds.DICER_DEATH, "Dicer dies");
        sound(OPSounds.DICER_HURT, "Dicer hurts");
        sound(OPSounds.DICER_IDLE, "Dicer screams");
        sound(OPSounds.DICER_ATTACK, "Dicer slashes");

        sound(OPSounds.ELECTRIC_CHARGE, "Electricity whirls");
        sound(OPSounds.ELECTRIC_CHARGE_DISSIPATE, "Electricity dissipates");

        sound(OPSounds.RAMBLE_HURT, "Ramble hurt");
        sound(OPSounds.RAMBLE_DEATH, "Ramble dies");
        sound(OPSounds.RAMBLE_ATTACK, "Ramble slices");
        sound(OPSounds.RAMBLE_IDLE, "Ramble clatters");

        sound(OPSounds.TESLA_BOW_CHARGED, "Tesla Bow loads");
        sound(OPSounds.TESLA_BOW_SHOOT, "Tesla Bow fires");

        sound(OPSounds.UMBER_SPIDER_DEATH, "Umber Spider dies");
        sound(OPSounds.UMBER_SPIDER_HURT, "Umber Spider hurts");
        sound(OPSounds.UMBER_SPIDER_IDLE, "Umber Spider groans");

        sound(OPSounds.VOLT_DEATH, "Volt dies");
        sound(OPSounds.VOLT_HURT, "Volt hurts");
        sound(OPSounds.VOLT_IDLE, "Volt hums");
        sound(OPSounds.VOLT_SHOOT, "Volt shoots");

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

        addEntityType(OPEntities.WHIZZ, "Whizz");
        addItem(OPItems.WHIZZ_SPAWN_EGG, "Whizz Spawn Egg");

        addEntityType(OPEntities.BOUNCER, "Bouncer");
        addItem(OPItems.BOUNCER_SPAWN_EGG, "Bouncer Spawn Egg");

        addEntityType(OPEntities.FROWZY, "Frowzy");
        addItem(OPItems.FROWZY_SPAWN_EGG, "Frowzy Spawn Egg");

        addEntityType(OPEntities.FIRE_SLIME, "Aflame Slime");
        addItem(OPItems.FIRESLIME_SPAWN_EGG, "Aflame Slime Spawn Egg");

        addEntityType(OPEntities.GUZZLER, "Guzzler");
        addItem(OPItems.GUZZLER_SPAWN_EGG, "Guzzler Spawn Egg");

        addItem(OPItems.TOMAHAWK, "Tomahawk");
        add("death.attack.hole.tomahawk", "%s was domed by %s");

        addEntityType(OPEntities.SLUG, "Slug");
        addItem(OPItems.SLUG_SPAWN_EGG, "Slug Spawn Egg");
        addItem(OPItems.SLUG_EGGS, "Slug Eggs");

        addItem(OPItems.VILE_BOULDER, "Vile Boulder");

        addEnchantmentWithDesc(OPEnchantments.BIG_ELECTRIC_BALL.get(), "Increases the size of the fired electric charge");
        addEnchantmentWithDesc(OPEnchantments.BOUNCY_ELECTRIC_BALL.get(), "The fired electric charge bounces off blocks and passes through mobs");
        addEnchantmentWithDesc(OPEnchantments.KICKBACK.get(), "Launches the user backwards after firing");
    }

    @Override
    public String getName() {
        return  OpposingForce.MOD_ID + " Languages: en_us";
    }

    private void forBlock(Supplier<? extends Block> block) {
        addBlock(block, OPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath()));
    }

    private void forItem(Supplier<? extends Item> item) {
        addItem(item, OPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath()));
    }

    private void forEntity(Supplier<? extends EntityType<?>> entity) {
        addEntityType(entity, OPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.get())).getPath()));
    }

    public void addBETranslatable(String beName,String name){
        add(OpposingForce.MOD_ID + ".blockentity." + beName, name);
    }

    public void sound(Supplier<? extends SoundEvent> key, String subtitle){
        add("subtitles." + key.get().getLocation().getPath(), subtitle);
    }

    private void addEnchantmentWithDesc(Enchantment enchantment, String description) {
        String name = ForgeRegistries.ENCHANTMENTS.getKey(enchantment).getPath();
        this.add(enchantment, formatEnchantment(name));
        this.add(enchantment.getDescriptionId() + ".desc", description);
    }

    private String formatEnchantment(String path) {
        return WordUtils.capitalizeFully(path.replace("_", " ")).replace("Of ", "of ");
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

    public void add(Potion key, String name, String regName) {
        add("item.minecraft.potion.effect." + regName, name);
        add("item.minecraft.splash_potion.effect." + regName, "Splash " + name);
        add("item.minecraft.lingering_potion.effect." + regName, "Lingering " + name);
    }
}
