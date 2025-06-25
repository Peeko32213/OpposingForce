package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OPCreativeTab;
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

public class OPLanguageProvider extends LanguageProvider {

    public OPLanguageProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), OpposingForce.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations(){

        this.addTabName(OPCreativeTab.TAB.get(), "Opposing Force");

        // blocks
        OPBlocks.AUTO_TRANSLATE.forEach(this::forBlocks);
        OPItems.AUTO_TRANSLATE.forEach(this::forItems);

        this.forEntity(OPEntities.DICER);
        this.forEntity(OPEntities.EMERALDFISH);
        this.forEntity(OPEntities.FIRE_SLIME);
        this.forEntity(OPEntities.FROWZY);
        this.forEntity(OPEntities.GUZZLER);
        this.forEntity(OPEntities.PALE_SPIDER);
        this.forEntity(OPEntities.RAMBLE);
        this.forEntity(OPEntities.SLUG);
        this.forEntity(OPEntities.TERROR);
        this.forEntity(OPEntities.TREMBLER);
        this.forEntity(OPEntities.UMBER_SPIDER);
        this.forEntity(OPEntities.VOLT);
        this.forEntity(OPEntities.WHIZZ);

        this.forEntity(OPEntities.ELECTRIC_CHARGE);
        this.forEntity(OPEntities.SLUG_EGGS);
        this.forEntity(OPEntities.TOMAHAWK);

        this.potion(OPPotions.GLOOM_TOXIN_POTION, "Gloom Toxin", "gloom_toxin");
        this.potion(OPPotions.LONG_GLOOM_TOXIN_POTION, "Gloom Toxin", "long_gloom_toxin");
        this.potion(OPPotions.STRONG_GLOOM_TOXIN_POTION, "Gloom Toxin", "strong_gloom_toxin");

        this.potion(OPPotions.SLUG_INFESTATION_POTION, "Slug Infestation", "slug_infestation");

        this.sound(OPSoundEvents.ARMOR_EQUIP_DEEPWOVEN, "Deepwoven armor rustles");

        this.sound(OPSoundEvents.DICER_DEATH, "Dicer dies");
        this.sound(OPSoundEvents.DICER_HURT, "Dicer hurts");
        this.sound(OPSoundEvents.DICER_IDLE, "Dicer screams");
        this.sound(OPSoundEvents.DICER_ATTACK, "Dicer slashes");
        this.sound(OPSoundEvents.DICER_LASER, "Dicer lasers");

        this.sound(OPSoundEvents.ELECTRIC_CHARGE, "Electric Charge whirls");
        this.sound(OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE, "Electric Charge dissipates");
        this.sound(OPSoundEvents.ELECTRIC_CHARGE_ZAP, "Electric Charge zaps");

        this.sound(OPSoundEvents.FIRE_SLIME_DEATH, "Fire Slime dies");
        this.sound(OPSoundEvents.FIRE_SLIME_HURT, "Fire Slime hurts");
        this.sound(OPSoundEvents.FIRE_SLIME_IDLE, "Fire Slime sizzles");
        this.sound(OPSoundEvents.FIRE_SLIME_SQUISH, "Fire Slime squishes");
        this.sound(OPSoundEvents.FIRE_SLIME_JUMP, "Fire Slime jumps");
        this.sound(OPSoundEvents.FIRE_SLIME_ATTACK, "Fire Slime attacks");
        this.sound(OPSoundEvents.FIRE_SLIME_POP, "Fire Slime evaporates");

        this.sound(OPSoundEvents.FROWZY_DEATH, "Frowzy dies");
        this.sound(OPSoundEvents.FROWZY_HURT, "Frowzy hurts");
        this.sound(OPSoundEvents.FROWZY_IDLE, "Frowzy groans");
        this.sound(OPSoundEvents.FROWZY_ATTACK, "Frowzy attacks");
        this.sound(OPSoundEvents.FROWZY_RUN, "Frowzy pants");

        this.sound(OPSoundEvents.GUZZLER_HURT, "Guzzler hurt");
        this.sound(OPSoundEvents.GUZZLER_DEATH, "Guzzler dies");
        this.sound(OPSoundEvents.GUZZLER_IDLE, "Guzzler groans");
        this.sound(OPSoundEvents.GUZZLER_SPEW, "Guzzler spews");

        this.sound(OPSoundEvents.PALE_SPIDER_DEATH, "Pale Spider dies");
        this.sound(OPSoundEvents.PALE_SPIDER_HURT, "Pale Spider hurts");
        this.sound(OPSoundEvents.PALE_SPIDER_IDLE, "Pale Spider hisses");

        this.sound(OPSoundEvents.RAMBLE_HURT, "Ramble hurt");
        this.sound(OPSoundEvents.RAMBLE_DEATH, "Ramble dies");
        this.sound(OPSoundEvents.RAMBLE_ATTACK, "Ramble slices");
        this.sound(OPSoundEvents.RAMBLE_IDLE, "Ramble clatters");

        this.sound(OPSoundEvents.SLUG_DEATH, "Slug dies");
        this.sound(OPSoundEvents.SLUG_HURT, "Slug hurts");
        this.sound(OPSoundEvents.SLUG_SLIDE, "Footsteps");
        this.sound(OPSoundEvents.SLUG_EAT, "Slug eats");
        this.sound(OPSoundEvents.SLUG_ATTACK, "Slug attacks");

        this.sound(OPSoundEvents.TESLA_BOW_CHARGED, "Tesla Bow loads");
        this.sound(OPSoundEvents.TESLA_BOW_SHOOT, "Tesla Bow fires");

        this.sound(OPSoundEvents.TREMBLER_BLOCK, "Trembler blocks");

        this.sound(OPSoundEvents.UMBER_SPIDER_DEATH, "Umber Spider dies");
        this.sound(OPSoundEvents.UMBER_SPIDER_HURT, "Umber Spider hurts");
        this.sound(OPSoundEvents.UMBER_SPIDER_IDLE, "Umber Spider groans");

        this.sound(OPSoundEvents.VOLT_DEATH, "Volt dies");
        this.sound(OPSoundEvents.VOLT_HURT, "Volt hurts");
        this.sound(OPSoundEvents.VOLT_IDLE, "Volt hums");
        this.sound(OPSoundEvents.VOLT_SHOOT, "Volt shoots");
        this.sound(OPSoundEvents.VOLT_SQUISH, "Volt squishes");

        this.sound(OPSoundEvents.WHIZZ_DEATH, "Whizz dies");
        this.sound(OPSoundEvents.WHIZZ_HURT, "Whizz hurts");
        this.sound(OPSoundEvents.WHIZZ_FLY, "Whizz buzzes");
        this.sound(OPSoundEvents.WHIZZ_ATTACK, "Whizz bites");

        this.addBlock(OPBlocks.INFESTED_AMETHYST_BLOCK, "Infested Block of Amethyst");

        // other
        this.addEnchantmentWithDesc(OPEnchantments.BIG_ELECTRIC_BALL.get(), "Increases the size of the fired electric charge");
        this.addEnchantmentWithDesc(OPEnchantments.BOUNCY_ELECTRIC_BALL.get(), "The fired electric charge bounces off blocks and passes through mobs");
        this.addEnchantmentWithDesc(OPEnchantments.KICKBACK.get(), "Launches the user backwards after firing");

        this.addEffect(OPEffects.ELECTRIFIED, "Electrified");
        this.addEffect(OPEffects.GLOOM_TOXIN, "Gloom Toxin");
        this.addEffect(OPEffects.SLUG_INFESTATION, "Slug Infestation");

        this.addAdvancement("root", "Opposing Force");
        this.addAdvancementDesc("root", "Discover unique and challenging mobs throughout the world");

        this.addAdvancement("trembler_shell", "Spin to Win");
        this.addAdvancementDesc("trembler_shell", "Defeat a Trembler and claim its shell");

        this.addAdvancement("deepwoven_armor", "Deepwoven");
        this.addAdvancementDesc("deepwoven_armor", "Conceal yourself with a piece Deepwoven armor");

        this.addAdvancement("ignite_fire_slime", "Fire Slime");
        this.addAdvancementDesc("ignite_fire_slime", "Reignite a Fire Slime with Magma Cream to prevent it from evaporating");

        this.addAdvancement("tame_slug", "Tame Slug");
        this.addAdvancementDesc("tame_slug", "Use Slug Eggs to spawn a tamed Slug");

        this.addAdvancement("grow_slug", "Grow Slug");
        this.addAdvancementDesc("grow_slug", "Feed a tamed Slug a Slime Block to increase its size");

        this.addAdvancement("capture_whizz", "Capture Whizz");
        this.addAdvancementDesc("capture_whizz", "Use a Silk Touch pickaxe to capture a Whizz");

        // jeed compat
        this.add("effect.opposing_force.electrified.description", "Inflicts lethal damage over time while in water or rain; higher levels do more damage per second.");
        this.add("effect.opposing_force.gloom_toxin.description", "Inflicts lethal damage over time while in light levels of 7 or less; higher levels do more damage per second.");
        this.add("effect.opposing_force.slug_infestation.description", "Decreases walking speed and gives the entity a 25% chance to spawn between 1 and 2 slugs when hurt; higher levels increase the amount of slugs spawned.");

        this.add("death.attack.opposing_force.electrified", "%1$s met a shocking end");
        this.add("death.attack.opposing_force.electrified.player", "%1$s was zapped by %2$s");
        this.add("death.attack.opposing_force.gloom_toxin", "%1$s was consumed by darkness");
        this.add("death.attack.opposing_force.gloom_toxin.player", "%1$s didn't reach the light");
        this.add("death.attack.opposing_force.laser", "%1$s was vaporized by %2$s");
        this.add("death.attack.opposing_force.tomahawk", "%1$s was domed by %2$s");
        this.add("death.attack.opposing_force.tomahawk.item", "%1$s was domed by %2$s using %3$s");

        this.add("attribute.opposing_force.name.generic.stealth", "Stealth");
    }

    @Override
    public String getName() {
        return  OpposingForce.MOD_ID + " Languages: en_us";
    }

    private void forBlocks(Supplier<? extends Block> block) {
        addBlock(block, OPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath()));
    }

    private void forItems(Supplier<? extends Item> item) {
        addItem(item, OPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath()));
    }

    private void forEntity(Supplier<? extends EntityType<?>> entity) {
        addEntityType(entity, OPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.get())).getPath()));
    }

    public void addBETranslatable(String beName,String name){
        add(OpposingForce.MOD_ID + ".blockentity." + beName, name);
    }

    public void sound(Supplier<? extends SoundEvent> key, String subtitle){
        add("subtitles.opposing_force." + key.get().getLocation().getPath(), subtitle);
    }

    private void addEnchantmentWithDesc(Enchantment enchantment, String description) {
        String name = ForgeRegistries.ENCHANTMENTS.getKey(enchantment).getPath();
        this.add(enchantment, formatEnchantment(name));
        this.add(enchantment.getDescriptionId() + ".desc", description);
    }

    private String formatEnchantment(String path) {
        return WordUtils.capitalizeFully(path.replace("_", " ")).replace("Of ", "of ");
    }

    public void potion(Supplier<? extends Potion> key, String name, String regName) {
        potions(key.get(), name, regName);
    }

    public void potions(Potion key, String name, String regName) {
        add("item.minecraft.potion.effect." + regName, "Potion of " + name);
        add("item.minecraft.splash_potion.effect." + regName, "Splash Potion of " + name);
        add("item.minecraft.lingering_potion.effect." + regName, "Lingering Potion of " + name);
        add("item.minecraft.tipped_arrow.effect." + regName, "Arrow of " + name);
    }

    public void addTabName(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void addAdvancement(String key, String name) {
        this.add("advancement." + OpposingForce.MOD_ID + "." + key, name);
    }

    public void addAdvancementDesc(String key, String name) {
        this.add("advancement." + OpposingForce.MOD_ID + "." + key + ".desc", name);
    }
}
