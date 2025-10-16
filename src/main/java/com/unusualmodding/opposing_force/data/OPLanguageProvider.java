package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForceTab;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
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

        this.addTabName(OpposingForceTab.TAB.get(), "Opposing Force");

        // blocks
        OPBlocks.AUTO_TRANSLATE.forEach(this::forBlocks);
        OPItems.AUTO_TRANSLATE.forEach(this::forItems);

        this.addItem(OPItems.INFERNO_PIE_SLICE, "Slice of Inferno Pie");

        this.forEntity(OPEntities.DICER);
        this.forEntity(OPEntities.EMERALDFISH);
        this.forEntity(OPEntities.FIRE_SLIME);
        this.forEntity(OPEntities.FROWZY);
        this.forEntity(OPEntities.GUZZLER);
        this.forEntity(OPEntities.HANGING_SPIDER);
        this.forEntity(OPEntities.RAMBLER);
        this.forEntity(OPEntities.SLUG);
        this.forEntity(OPEntities.TERROR);
        this.forEntity(OPEntities.TREMBLER);
        this.forEntity(OPEntities.UMBER_SPIDER);
        this.forEntity(OPEntities.VOLT);
        this.forEntity(OPEntities.WHIZZ);

        this.forEntity(OPEntities.ELECTRIC_CHARGE);
        this.forEntity(OPEntities.TOMAHAWK);
        this.forEntity(OPEntities.UMBER_DAGGER);

        this.potion(OPPotions.GLOOM_TOXIN_POTION, "Gloom Toxin", "gloom_toxin");
        this.potion(OPPotions.LONG_GLOOM_TOXIN_POTION, "Gloom Toxin", "long_gloom_toxin");
        this.potion(OPPotions.STRONG_GLOOM_TOXIN_POTION, "Gloom Toxin", "strong_gloom_toxin");

        this.potion(OPPotions.SLUG_INFESTATION_POTION, "Slug Infestation", "slug_infestation");

        this.sound(OPSoundEvents.ARMOR_EQUIP_DEEPWOVEN, "Deepwoven armor rustles");
        this.sound(OPSoundEvents.ARMOR_EQUIP_WOODEN, "Wooden armor knocks");
        this.sound(OPSoundEvents.ARMOR_EQUIP_STONE, "Stone armor clanks");
        this.sound(OPSoundEvents.ARMOR_EQUIP_EMERALD, "Emerald armor clanks");
        this.sound(OPSoundEvents.ARMOR_EQUIP_MOON_SHOES, "Moon shoes flutter");

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
        this.sound(OPSoundEvents.GUZZLER_SLAM, "Guzzler slams");

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

        this.sound(OPSoundEvents.TERROR_DEATH, "Terror dies");
        this.sound(OPSoundEvents.TERROR_HURT, "Terror hurts");
        this.sound(OPSoundEvents.TERROR_IDLE, "Terror grumbles");
        this.sound(OPSoundEvents.TERROR_FLOP, "Terror flops");

        this.sound(OPSoundEvents.TESLA_BOW_CHARGED, "Tesla Bow loads");
        this.sound(OPSoundEvents.TESLA_BOW_SHOOT, "Tesla Bow fires");

        this.sound(OPSoundEvents.TREMBLER_DEATH, "Trembler dies");
        this.sound(OPSoundEvents.TREMBLER_HURT, "Trembler hurts");
        this.sound(OPSoundEvents.TREMBLER_IDLE, "Trembler hisses");
        this.sound(OPSoundEvents.TREMBLER_IDLE_FUNNY, "TREMBLER BREATHES");
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

        this.sound(OPSoundEvents.LASER_BOLT_IMPACT, "Laser impacts");

        this.sound(OPSoundEvents.BLASTER_SHOOT, "Blaster shoots");

        this.addBlock(OPBlocks.INFESTED_AMETHYST_BLOCK, "Infested Block of Amethyst");

        this.addBlock(OPBlocks.DICER_HEAD, "Dicer Head");
        this.addBlock(OPBlocks.DICER_WALL_HEAD, "Dicer Head");

        this.addBlock(OPBlocks.FROWZY_HEAD, "Frowzy Head");
        this.addBlock(OPBlocks.FROWZY_WALL_HEAD, "Frowzy Head");

        this.addBlock(OPBlocks.RAMBLE_SKULL, "Ramble Skull");
        this.addBlock(OPBlocks.RAMBLE_WALL_SKULL, "Ramble Skull");

        // other
        this.addEnchantmentWithDesc(OPEnchantments.CAPACITANCE.get(), "Increases the size of the fired electric charge");
        this.addEnchantmentWithDesc(OPEnchantments.REBOUND.get(), "The fired electric charge bounces off blocks and passes through mobs");
        this.addEnchantmentWithDesc(OPEnchantments.KICKBACK.get(), "Launches the user backwards after firing");
        this.addEnchantmentWithDesc(OPEnchantments.QUASAR.get(), "The fired electric charge changes color rapidly and pulls mobs towards it");
        this.addEnchantmentWithDesc(OPEnchantments.STATIC_ATTRACTION.get(), "The fired electric charge seeks out nearby entities");
        this.addEnchantmentWithDesc(OPEnchantments.BATTERY.get(), "Tesla bow has a chance to not consume electric charges");

        this.addEnchantmentWithDesc(OPEnchantments.PLAGUE.get(), "Increases the level of slug infestation inflicted");

        this.addEnchantmentWithDesc(OPEnchantments.RAPID_FIRE.get(), "Increases the fire rate of the blaster at the cost of damage per shot");
        this.addEnchantmentWithDesc(OPEnchantments.SPLITTING.get(), "Laser bolts split into multiple laser bolts on hit");
        this.addEnchantmentWithDesc(OPEnchantments.FREEZE_RAY.get(), "Laser bolts inflict freezing on hit");
        this.addEnchantmentWithDesc(OPEnchantments.POWER_SUPPLY.get(), "Blaster has a chance to not consume redstone dust");

        this.addEnchantmentWithDesc(OPEnchantments.PHOTOSYNTHESIS.get(), "Heals the user over time while exposed to sunlight");

        this.addEffect(OPEffects.ELECTRIFIED, "Electrified");
        this.addEffect(OPEffects.GLOOM_TOXIN, "Gloom Toxin");
        this.addEffect(OPEffects.SLUG_INFESTATION, "Slug Infestation");

        this.addAdvancement("root", "Opposing Force");
        this.addAdvancementDesc("root", "Discover unique and challenging mobs throughout the world");

        this.addAdvancement("trembler_shell", "Spin to Win");
        this.addAdvancementDesc("trembler_shell", "Defeat a Trembler and claim its shell");

        this.addAdvancement("deepwoven_armor", "A Cozy Caver");
        this.addAdvancementDesc("deepwoven_armor", "Conceal yourself with a piece Deepwoven armor");

        this.addAdvancement("ignite_fire_slime", "Protocol III");
        this.addAdvancementDesc("ignite_fire_slime", "Reignite a Fire Slime with Blaze Powder to prevent it from evaporating");

        this.addAdvancement("tame_slug", "Go my Slug");
        this.addAdvancementDesc("tame_slug", "Tame a Slug using Slime Balls");

        this.addAdvancement("grow_slug", "Rise, my glorious creation");
        this.addAdvancementDesc("grow_slug", "Feed a tamed Slug a Slime Block to increase its size");

        this.addAdvancement("capture_whizz", "With the Whizz? No way!");
        this.addAdvancementDesc("capture_whizz", "Use a Silk Touch pickaxe to capture a Whizz");

        this.translateAttribute(OPAttributes.STEALTH);
        this.translateAttribute(OPAttributes.ELECTRIC_RESISTANCE);
        this.translateAttribute(OPAttributes.JUMP_POWER);
        this.translateAttribute(OPAttributes.AIR_SPEED);
        this.translateAttribute(OPAttributes.FORTUNE);
        this.translateAttribute(OPAttributes.LOOTING);
        this.translateAttribute(OPAttributes.EXPERIENCE_GAIN);

        // jeed compat
        this.add("effect.opposing_force.electrified.description", "Inflicts lethal damage over time while in water or rain; higher levels do more damage per second.");
        this.add("effect.opposing_force.gloom_toxin.description", "Inflicts lethal damage over time while in light levels of 7 or less; higher levels do more damage per second.");
        this.add("effect.opposing_force.slug_infestation.description", "Decreases walking speed and gives the entity a 25% chance to spawn between 1 and 2 slugs when hurt; higher levels increase the amount of slugs spawned.");

        this.translateDamageType(OPDamageTypes.ELECTRIFIED, player -> player + " succumbed to electric shocks", (player, entity) -> player + " was electrified by " + entity);
        this.translateDamageType(OPDamageTypes.ELECTRIC, player -> player + " met a shocking end", (player, entity) -> player + " was zapped by " + entity);
        this.translateDamageType(OPDamageTypes.GLOOM_TOXIN, player -> player + " was consumed by darkness", (player, entity) -> player + " didn't reach the light");
        this.translateDamageType(OPDamageTypes.LASER, player -> player + " was vaporized", (player, entity) -> player + " was vaporized by" + entity);
        this.translateDamageType(OPDamageTypes.TOMAHAWK, player -> player + " was domed", (player, entity) -> player + " was domed by" + entity);
        this.translateDamageType(OPDamageTypes.UMBER_DAGGER, player -> player + " was stabbed", (player, entity) -> player + " was stabbed by" + entity);
        this.translateDamageType(OPDamageTypes.LASER_BOLT, player -> player + " was blasted", (player, entity) -> player + " was blasted by" + entity);
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

    private void translateAttribute(RegistryObject<? extends Attribute> attribute) {
        this.add(attribute.get().getDescriptionId(), toUpper(ForgeRegistries.ATTRIBUTES, attribute));
    }

    private void translateDamageType(ResourceKey<DamageType> source, Function<String, String> death, BiFunction<String, String, String> killed) {
        String msgId = source.location().getPath();
        this.add("death.attack." + msgId, death.apply("%1$s"));
        this.add("death.attack." + msgId + ".player", killed.apply("%1$s", "%2$s"));
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

    private void addDescription(RegistryObject<? extends ItemLike> item, String desc) {
        this.add(item.get().asItem().getDescriptionId() + ".desc", desc);
    }

    private static <T> String toUpper(IForgeRegistry<T> registry, RegistryObject<? extends T> object) {
        return toUpper(registry.getKey(object.get()).getPath());
    }

    private static String toUpper(String string) {
        return StringUtils.capitaliseAllWords(string.replace('_', ' '));
    }
}
