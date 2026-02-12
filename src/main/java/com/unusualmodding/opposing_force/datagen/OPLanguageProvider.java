package com.unusualmodding.opposing_force.datagen;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.OpposingForceTab;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;
import org.codehaus.plexus.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class OPLanguageProvider extends LanguageProvider {

    public OPLanguageProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), OpposingForce.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations(){

        this.translateTab(OpposingForceTab.TAB.get(), "Opposing Force");

        // blocks
        OPBlocks.BLOCK_TRANSLATIONS.forEach(this::forBlocks);
        OPItems.ITEM_TRANSLATIONS.forEach(this::forItems);

        this.addItem(OPItems.INFERNO_PIE_SLICE, "Slice of Inferno Pie");
        this.addItem(OPItems.RAW_TART, "Raw Tart");

        this.forEntity(OPEntities.DICER);
        this.forEntity(OPEntities.EMERALDFISH);
        this.forEntity(OPEntities.FIRE_SLIME);
        this.forEntity(OPEntities.FROWZY);
        this.forEntity(OPEntities.GUZZLER);
        this.forEntity(OPEntities.HANGING_SPIDER);
        this.forEntity(OPEntities.LADYBUG);
//        this.forEntity(OPEntities.NYMPH);
        this.forEntity(OPEntities.RAMBLER);
        this.forEntity(OPEntities.SKYVERN);
        this.forEntity(OPEntities.SKYVERN_SEGMENT);
        this.forEntity(OPEntities.STRATO_ARROW);
        this.forEntity(OPEntities.SLUG);
        this.forEntity(OPEntities.TART);
        this.forEntity(OPEntities.TERROR);
        this.forEntity(OPEntities.TREMBLER);
        this.forEntity(OPEntities.UMBER_SPIDER);
        this.forEntity(OPEntities.VOLT);
        this.forEntity(OPEntities.WHIZZ);

        this.forEntity(OPEntities.DONUT);
        this.forEntity(OPEntities.ELECTRIC_CHARGE);
        this.forEntity(OPEntities.TERROR_SAW);
        this.forEntity(OPEntities.TOMAHAWK);
        this.forEntity(OPEntities.UMBER_DAGGER);
        this.forEntity(OPEntities.FIRE_BOMB);
        this.forEntity(OPEntities.KINETIC_BOMB);
        this.forEntity(OPEntities.LIGHTNING_BOMB);
        this.forEntity(OPEntities.WHIZZ_BOMB);
        this.forEntity(OPEntities.LASER_BLADE);
        this.forEntity(OPEntities.LASER_BOLT);
        this.forEntity(OPEntities.DICER_LASER);

        this.translatePotion(OPPotions.GLOOM_TOXIN_POTION, "Gloom Toxin", "gloom_toxin");
        this.translatePotion(OPPotions.LONG_GLOOM_TOXIN_POTION, "Gloom Toxin", "long_gloom_toxin");
        this.translatePotion(OPPotions.STRONG_GLOOM_TOXIN_POTION, "Gloom Toxin", "strong_gloom_toxin");

        this.translatePotion(OPPotions.SLUG_INFESTATION_POTION, "Slug Infestation", "slug_infestation");

        this.translateSound(OPSoundEvents.ARMOR_EQUIP_DEEPWOVEN, "Deepwoven armor rustles");
        this.translateSound(OPSoundEvents.ARMOR_EQUIP_WOODEN, "Wooden armor knocks");
        this.translateSound(OPSoundEvents.ARMOR_EQUIP_STONE, "Stone armor clanks");
        this.translateSound(OPSoundEvents.ARMOR_EQUIP_EMERALD, "Emerald armor clanks");
        this.translateSound(OPSoundEvents.ARMOR_EQUIP_MOON_SHOES, "Moon shoes flutter");

        this.translateSound(OPSoundEvents.DICER_DEATH, "Dicer dies");
        this.translateSound(OPSoundEvents.DICER_HURT, "Dicer hurts");
        this.translateSound(OPSoundEvents.DICER_IDLE, "Dicer screams");
        this.translateSound(OPSoundEvents.DICER_ATTACK, "Dicer slashes");
        this.translateSound(OPSoundEvents.DICER_LASER, "Dicer lasers");
        this.translateSound(OPSoundEvents.NOTE_BLOCK_IMITATE_DICER, "Dicer screams");

        this.translateSound(OPSoundEvents.ELECTRIC_CHARGE, "Electric Charge whirls");
        this.translateSound(OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE, "Electric Charge dissipates");
        this.translateSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP, "Electric Charge zaps");

        this.translateSound(OPSoundEvents.FIRE_SLIME_DEATH, "Fire Slime dies");
        this.translateSound(OPSoundEvents.FIRE_SLIME_HURT, "Fire Slime hurts");
        this.translateSound(OPSoundEvents.FIRE_SLIME_IDLE, "Fire Slime sizzles");
        this.translateSound(OPSoundEvents.FIRE_SLIME_SQUISH, "Fire Slime squishes");
        this.translateSound(OPSoundEvents.FIRE_SLIME_JUMP, "Fire Slime jumps");
        this.translateSound(OPSoundEvents.FIRE_SLIME_ATTACK, "Fire Slime attacks");
        this.translateSound(OPSoundEvents.FIRE_SLIME_POP, "Fire Slime evaporates");

        this.translateSound(OPSoundEvents.FROWZY_DEATH, "Frowzy dies");
        this.translateSound(OPSoundEvents.FROWZY_HURT, "Frowzy hurts");
        this.translateSound(OPSoundEvents.FROWZY_IDLE, "Frowzy groans");
        this.translateSound(OPSoundEvents.FROWZY_ATTACK, "Frowzy attacks");
        this.translateSound(OPSoundEvents.FROWZY_RUN, "Frowzy pants");
        this.translateSound(OPSoundEvents.NOTE_BLOCK_IMITATE_FROWZY, "Frowzy groans");

        this.translateSound(OPSoundEvents.GUZZLER_HURT, "Guzzler hurt");
        this.translateSound(OPSoundEvents.GUZZLER_DEATH, "Guzzler dies");
        this.translateSound(OPSoundEvents.GUZZLER_IDLE, "Guzzler groans");
        this.translateSound(OPSoundEvents.GUZZLER_SPEW, "Guzzler spews");
        this.translateSound(OPSoundEvents.GUZZLER_SLAM, "Guzzler slams");

        this.translateSound(OPSoundEvents.HANGING_SPIDER_DEATH, "Hanging Spider dies");
        this.translateSound(OPSoundEvents.HANGING_SPIDER_HURT, "Hanging Spider hurts");
        this.translateSound(OPSoundEvents.HANGING_SPIDER_IDLE, "Hanging Spider hisses");

        this.translateSound(OPSoundEvents.RAMBLER_HURT, "Rambler hurt");
        this.translateSound(OPSoundEvents.RAMBLER_DEATH, "Rambler dies");
        this.translateSound(OPSoundEvents.RAMBLER_ATTACK, "Rambler slices");
        this.translateSound(OPSoundEvents.RAMBLER_IDLE, "Rambler clatters");
        this.translateSound(OPSoundEvents.NOTE_BLOCK_IMITATE_RAMBLER, "Rambler clatters");

        this.translateSound(OPSoundEvents.SLUG_DEATH, "Slug dies");
        this.translateSound(OPSoundEvents.SLUG_HURT, "Slug hurts");
        this.translateSound(OPSoundEvents.SLUG_SLIDE, "Slug slides");
        this.translateSound(OPSoundEvents.SLUG_EAT, "Slug eats");
        this.translateSound(OPSoundEvents.SLUG_ATTACK, "Slug attacks");

        this.translateSound(OPSoundEvents.TART_DEATH, "Slug dies");
        this.translateSound(OPSoundEvents.TART_HURT, "Slug hurts");
        this.translateSound(OPSoundEvents.TART_IDLE, "Tart gurgles");
        this.translateSound(OPSoundEvents.TART_ATTACK, "Tart attacks");
        this.translateSound(OPSoundEvents.TART_PLUCK, "Tart escapes leaf");
        this.translateSound(OPSoundEvents.NOTE_BLOCK_IMITATE_TART, "Tart tarts");

        this.translateSound(OPSoundEvents.TERROR_DEATH, "Terror dies");
        this.translateSound(OPSoundEvents.TERROR_HURT, "Terror hurts");
        this.translateSound(OPSoundEvents.TERROR_IDLE, "Terror grumbles");
        this.translateSound(OPSoundEvents.TERROR_FLOP, "Terror flops");
        this.translateSound(OPSoundEvents.TERROR_SAW_START, "Terror starts sawing");
        this.translateSound(OPSoundEvents.TERROR_SAW, "Terror saws");
        this.translateSound(OPSoundEvents.TERROR_SAW_END, "Terror stops sawing");

        this.translateSound(OPSoundEvents.SAWBLADE_SAW_START, "Sawblade starts sawing");
        this.translateSound(OPSoundEvents.SAWBLADE_SAW, "Sawblade saws");
        this.translateSound(OPSoundEvents.SAWBLADE_SAW_END, "Sawblade stops sawing");
        this.translateSound(OPSoundEvents.SAWBLADE_SWING, "Sawblade swings");

        this.translateSound(OPSoundEvents.TESLA_BOW_CHARGED, "Tesla Bow loads");
        this.translateSound(OPSoundEvents.TESLA_BOW_SHOOT, "Tesla Bow fires");

        this.translateSound(OPSoundEvents.TREMBLER_DEATH, "Trembler dies");
        this.translateSound(OPSoundEvents.TREMBLER_HURT, "Trembler hurts");
        this.translateSound(OPSoundEvents.TREMBLER_IDLE, "Trembler hisses");
        this.translateSound(OPSoundEvents.TREMBLER_IDLE_FUNNY, "TREMBLER BREATHES");
        this.translateSound(OPSoundEvents.TREMBLER_BLOCK, "Trembler blocks");

        this.translateSound(OPSoundEvents.UMBER_SPIDER_DEATH, "Umber Spider dies");
        this.translateSound(OPSoundEvents.UMBER_SPIDER_HURT, "Umber Spider hurts");
        this.translateSound(OPSoundEvents.UMBER_SPIDER_IDLE, "Umber Spider groans");

        this.translateSound(OPSoundEvents.VOLT_DEATH, "Volt dies");
        this.translateSound(OPSoundEvents.VOLT_HURT, "Volt hurts");
        this.translateSound(OPSoundEvents.VOLT_IDLE, "Volt hums");
        this.translateSound(OPSoundEvents.VOLT_SHOOT, "Volt shoots");
        this.translateSound(OPSoundEvents.VOLT_SQUISH, "Volt squishes");

        this.translateSound(OPSoundEvents.WHIZZ_DEATH, "Whizz dies");
        this.translateSound(OPSoundEvents.WHIZZ_HURT, "Whizz hurts");
        this.translateSound(OPSoundEvents.WHIZZ_FLY, "Whizz buzzes");
        this.translateSound(OPSoundEvents.WHIZZ_ATTACK, "Whizz bites");
        this.translateSound(OPSoundEvents.NOTE_BLOCK_IMITATE_WHIZZ, "Whizz bites");

        this.translateSound(OPSoundEvents.SKYVERN_DEATH, "Skyvern dies");
        this.translateSound(OPSoundEvents.SKYVERN_HURT, "Skyvern hurts");
        this.translateSound(OPSoundEvents.SKYVERN_IDLE, "Skyvern chatters");
        this.translateSound(OPSoundEvents.SKYVERN_IDLE_HOSTILE, "Skyvern chatters angrily");
        this.translateSound(OPSoundEvents.SKYVERN_ROAR, "Skyvern roars");
        this.translateSound(OPSoundEvents.SKYVERN_CHARGE_WARN, "Skyvern warns");
        this.translateSound(OPSoundEvents.SKYVERN_LOOP, "Skyvern charges");
        this.translateSound(OPSoundEvents.SKYVERN_WHOOSH, "Skyvern whooshes");

        this.translateSound(OPSoundEvents.LASER_BOLT_IMPACT, "Laser impacts");

        this.translateSound(OPSoundEvents.BLASTER_SHOOT, "Blaster shoots");

        this.translateSound(OPSoundEvents.LASER_BLADE_BLOCK, "Laser Blade blocks");
        this.translateSound(OPSoundEvents.LASER_BLADE_HIT, "Laser Blade impacts");
        this.translateSound(OPSoundEvents.LASER_BLADE_SWING, "Laser Blade swings");
        this.translateSound(OPSoundEvents.LASER_BLADE_SPIN, "Laser Blade spins");
        this.translateSound(OPSoundEvents.LASER_BLADE_CATCH, "Laser Blade caught");

        this.translateSound(OPSoundEvents.WALTZ_OF_THE_SLUG_DISC, "Music Disc");

        this.addBlock(OPBlocks.INFESTED_AMETHYST_BLOCK, "Infested Block of Amethyst");

        this.addBlock(OPBlocks.ANGRY_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.angry_rambler_skull.desc", "Angry");

        this.addBlock(OPBlocks.CLASSIC_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.classic_rambler_skull.desc", "Classic");

        this.addBlock(OPBlocks.CRUNDLY_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.crundly_rambler_skull.desc", "Crundly");

        this.addBlock(OPBlocks.DWARVEN_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.dwarven_rambler_skull.desc", "Dwarven");

        this.addBlock(OPBlocks.EVIL_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.evil_rambler_skull.desc", "Evil");

        this.addBlock(OPBlocks.GRINNING_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.grinning_rambler_skull.desc", "Grinning");

        this.addBlock(OPBlocks.IMPRISONED_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.imprisoned_rambler_skull.desc", "Imprisoned");

        this.addBlock(OPBlocks.INDOMITABLE_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.indomitable_rambler_skull.desc", "Indomitable");

        this.addBlock(OPBlocks.LEERING_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.leering_rambler_skull.desc", "Leering");

        this.addBlock(OPBlocks.MAGMATIC_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.magmatic_rambler_skull.desc", "Magmatic");

        this.addBlock(OPBlocks.MUSICAL_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.musical_rambler_skull.desc", "Musical");

        this.addBlock(OPBlocks.NOSY_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.nosy_rambler_skull.desc", "Nosy");

        this.addBlock(OPBlocks.SKELETAL_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.skeletal_rambler_skull.desc", "Skeletal");

        this.addBlock(OPBlocks.SMILING_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.smiling_rambler_skull.desc", "Smiling");

        this.addBlock(OPBlocks.STRANGE_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.strange_rambler_skull.desc", "Strange");

        this.addBlock(OPBlocks.VALIANT_RAMBLER_SKULL.getFirst(), "Rambler Skull");
        this.add("block.opposing_force.valiant_rambler_skull.desc", "Valiant");

        this.translateMusicDisc(OPItems.WALTZ_OF_THE_SLUG_DISC, "ChipsTheCat - Waltz of the Slug");

        this.addItem(OPItems.TERROR_LEG, "Raw Terror Leg");

        this.add("item.opposing_force.slug_baron_set_bonus.desc", "Set Bonus: Summon slugs when hit");

        // other
        this.translateEffect(OPMobEffects.ELECTRIFIED, "Inflicts lethal damage over time while in water or rain; higher levels do more damage per second.");
        this.translateEffect(OPMobEffects.GLOOM_TOXIN, "Inflicts lethal damage over time while in light levels of 7 or less; higher levels do more damage per second.");
        this.translateEffect(OPMobEffects.SLUG_INFESTATION, "Decreases walking speed and gives the entity a 10% chance to spawn between 1 and 2 slugs when hurt; higher levels increase the amount of slugs spawned.");
        this.translateEffect(OPMobEffects.ENERGIZED, "Gain increased speed and attack damage.");

        this.translateEnchantmentWithDesc(OPEnchantments.CAPACITANCE.get(), "Increases the size of the fired electric charge");
        this.translateEnchantmentWithDesc(OPEnchantments.REBOUND.get(), "The fired electric charge bounces off blocks and passes through mobs");
        this.translateEnchantmentWithDesc(OPEnchantments.KICKBACK.get(), "Launches the user backwards after firing");
        this.translateEnchantmentWithDesc(OPEnchantments.QUASAR.get(), "The fired electric charge changes color rapidly and pulls mobs towards it");

        this.translateEnchantmentWithDesc(OPEnchantments.PLAGUE.get(), "Increases the level of slug infestation inflicted");

        this.translateEnchantmentWithDesc(OPEnchantments.POWER_SUPPLY.get(), "Blaster has a chance to not consume redstone dust");

        this.translateEnchantmentWithDesc(OPEnchantments.THROWING.get(), "Laser blade parry is replaced by throwing");

        this.translateAdvancement("root", "Opposing Force");
        this.translateAdvancementDesc("root", "Discover unique and challenging mobs throughout the world");

        this.translateAdvancement("trembler_shell", "Spin to Win");
        this.translateAdvancementDesc("trembler_shell", "Defeat a Trembler and claim its shell");

        this.translateAdvancement("deepwoven_armor", "A Cozy Caver");
        this.translateAdvancementDesc("deepwoven_armor", "Conceal yourself with a piece Deepwoven armor");

        this.translateAdvancement("tame_slug", "Go my Slug");
        this.translateAdvancementDesc("tame_slug", "Tame a Slug using Slime Balls");

        this.translateAdvancement("grow_slug", "Rise, my glorious creation");
        this.translateAdvancementDesc("grow_slug", "Feed a tamed Slug a Slime Block to increase its size");

        this.translateAdvancement("capture_whizz", "With the Whizz? No way!");
        this.translateAdvancementDesc("capture_whizz", "Use a Silk Touch pickaxe to capture a Whizz");

        this.translateAttribute(OPAttributes.STEALTH);
        this.translateAttribute(OPAttributes.JUMP_POWER);
        this.translateAttribute(OPAttributes.AIR_SPEED);
        this.translateAttribute(OPAttributes.EXPERIENCE_GAIN);
        this.translateAttribute(OPAttributes.SUMMON_DAMAGE);
        this.translateAttribute(OPAttributes.SUMMON_DURATION);
        this.translateAttribute(OPAttributes.RANGED_DAMAGE);

        // tame commands
        this.add("entity.opposing_force.all.command_0", "%s is wandering");
        this.add("entity.opposing_force.all.command_1", "%s is staying");
        this.add("entity.opposing_force.all.command_2", "%s is following");

        this.add("item.opposing_force.laser_blade.color", "Color: ");
        this.add("item.opposing_force.laser_blade.dyeable", "Dyeable");

        this.translateDamageType(OPDamageTypes.ELECTRIC, player -> player + " met a shocking end", (player, entity) -> player + " was zapped by " + entity);
        this.translateDamageType(OPDamageTypes.ELECTRIFIED, player -> player + " was electrified", (player, entity) -> player + " was electrified by " + entity);
        this.translateDamageType(OPDamageTypes.GLOOM_TOXIN, player -> player + " was consumed by darkness", (player, entity) -> player + " didn't reach the light");
        this.translateDamageType(OPDamageTypes.LASER, player -> player + " was vaporized", (player, entity) -> player + " was vaporized by" + entity);
        this.translateDamageType(OPDamageTypes.TOMAHAWK, player -> player + " was domed", (player, entity) -> player + " was domed by" + entity);
        this.translateDamageType(OPDamageTypes.UMBER_DAGGER, player -> player + " was stabbed", (player, entity) -> player + " was stabbed by" + entity);
        this.translateDamageType(OPDamageTypes.LASER_BOLT, player -> player + " was blasted", (player, entity) -> player + " was blasted by" + entity);
        this.translateDamageType(OPDamageTypes.THROWN_LASER_BLADE, player -> player + " was sliced in half", (player, entity) -> player + " was sliced in half by" + entity);
        this.translateDamageType(OPDamageTypes.SAWBLADE, player -> player + " was sawn in half", (player, entity) -> player + " was sawn in half by" + entity);

        // misc
        this.add("opposing_force.nether_progression.enabled", "Forgotten monsters have returned to the world...");
        this.add("opposing_force.end_progression.enabled", "The seal has been broken...");

        this.add("config.jade.plugin_opposing_force.skyvern_segment", "Skyvern Segments display as parent Skyvern");
    }

    @Override
    public @NotNull String getName() {
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

    public void translateSound(Supplier<? extends SoundEvent> key, String subtitle){
        add("subtitles.opposing_force." + key.get().getLocation().getPath(), subtitle);
    }

    private void translateEnchantmentWithDesc(Enchantment enchantment, String description) {
        String name = ForgeRegistries.ENCHANTMENTS.getKey(enchantment).getPath();
        this.add(enchantment, formatEnchantment(name));
        this.add(enchantment.getDescriptionId() + ".desc", description);
    }

    private String formatEnchantment(String path) {
        return WordUtils.capitalizeFully(path.replace("_", " ")).replace("Of ", "of ");
    }

    public void translatePotion(Supplier<? extends Potion> key, String name, String regName) {
        potions(key.get(), name, regName);
    }

    public void potions(Potion key, String name, String regName) {
        add("item.minecraft.potion.effect." + regName, "Potion of " + name);
        add("item.minecraft.splash_potion.effect." + regName, "Splash Potion of " + name);
        add("item.minecraft.lingering_potion.effect." + regName, "Lingering Potion of " + name);
        add("item.minecraft.tipped_arrow.effect." + regName, "Arrow of " + name);
    }

    protected void translateMusicDisc(Supplier<? extends Item> item, String description) {
        String disc = item.get().getDescriptionId();
        add(disc, "Music Disc");
        add(disc + ".desc", description);
    }

    private void translateAttribute(RegistryObject<? extends Attribute> attribute) {
        this.add(attribute.get().getDescriptionId(), toUpper(ForgeRegistries.ATTRIBUTES, attribute));
    }

    private void translateEffect(RegistryObject<? extends MobEffect> effect, String desc) {
        this.add(effect.get(), toUpper(ForgeRegistries.MOB_EFFECTS, effect));
        this.add(effect.get().getDescriptionId() + ".description", desc);
    }

    private void translateDamageType(ResourceKey<DamageType> source, Function<String, String> death, BiFunction<String, String, String> killed) {
        String msgId = source.location().getPath();
        this.add("death.attack." + msgId, death.apply("%1$s"));
        this.add("death.attack." + msgId + ".player", killed.apply("%1$s", "%2$s"));
    }

    public void translateTab(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void translateAdvancement(String key, String name) {
        this.add("advancement." + OpposingForce.MOD_ID + "." + key, name);
    }

    public void translateAdvancementDesc(String key, String name) {
        this.add("advancement." + OpposingForce.MOD_ID + "." + key + ".desc", name);
    }

    private void translateDescription(RegistryObject<? extends ItemLike> item, String desc) {
        this.add(item.get().asItem().getDescriptionId() + ".desc", desc);
    }

    private static <T> String toUpper(IForgeRegistry<T> registry, RegistryObject<? extends T> object) {
        return toUpper(registry.getKey(object.get()).getPath());
    }

    private static String toUpper(String string) {
        return StringUtils.capitaliseAllWords(string.replace('_', ' '));
    }
}
