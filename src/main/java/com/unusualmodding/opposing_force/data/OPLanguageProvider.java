package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OPCreativeTab;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
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

        addTabName(OPCreativeTab.TAB.get(), "Opposing Force");

        // blocks
        OPBlocks.AUTO_TRANSLATE.forEach(this::forBlock);
        OPItems.AUTO_TRANSLATE.forEach(this::forItem);

        forEntity(OPEntities.DICER);
        forEntity(OPEntities.EMERALDFISH);
        forEntity(OPEntities.FIRE_SLIME);
        forEntity(OPEntities.FROWZY);
        forEntity(OPEntities.GUZZLER);
        forEntity(OPEntities.PALE_SPIDER);
        forEntity(OPEntities.RAMBLE);
        forEntity(OPEntities.SLUG);
        forEntity(OPEntities.TERROR);
        forEntity(OPEntities.TREMBLER);
        forEntity(OPEntities.UMBER_SPIDER);
        forEntity(OPEntities.VOLT);
        forEntity(OPEntities.WHIZZ);

        forEntity(OPEntities.ELECTRIC_CHARGE);
        forEntity(OPEntities.SLUG_EGGS);
        forEntity(OPEntities.TOMAHAWK);

        addItem(OPItems.DEEPWOVEN_HELMET, "Deepwoven Hat");
        addItem(OPItems.DEEPWOVEN_CHESTPLATE, "Deepwoven Tunic");
        addItem(OPItems.DEEPWOVEN_LEGGINGS, "Deepwoven Pants");
        addItem(OPItems.DEEPWOVEN_BOOTS, "Deepwoven Boots");

        sound(OPSoundEvents.ARMOR_EQUIP_DEEPWOVEN, "Deepwoven armor rustles");

        sound(OPSoundEvents.DICER_DEATH, "Dicer dies");
        sound(OPSoundEvents.DICER_HURT, "Dicer hurts");
        sound(OPSoundEvents.DICER_IDLE, "Dicer screams");
        sound(OPSoundEvents.DICER_ATTACK, "Dicer slashes");
        sound(OPSoundEvents.DICER_LASER, "Dicer lasers");

        sound(OPSoundEvents.ELECTRIC_CHARGE, "Electric Charge whirls");
        sound(OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE, "Electric Charge dissipates");
        sound(OPSoundEvents.ELECTRIC_CHARGE_ZAP, "Electric Charge zaps");

        sound(OPSoundEvents.FIRE_SLIME_DEATH, "Fire Slime dies");
        sound(OPSoundEvents.FIRE_SLIME_HURT, "Fire Slime hurts");
        sound(OPSoundEvents.FIRE_SLIME_SQUISH, "Fire Slime squishes");
        sound(OPSoundEvents.FIRE_SLIME_JUMP, "Fire Slime jumps");
        sound(OPSoundEvents.FIRE_SLIME_ATTACK, "Fire Slime attacks");
        sound(OPSoundEvents.FIRE_SLIME_POP, "Fire Slime evaporates");

        sound(OPSoundEvents.FROWZY_DEATH, "Frowzy dies");
        sound(OPSoundEvents.FROWZY_HURT, "Frowzy hurts");
        sound(OPSoundEvents.FROWZY_IDLE, "Frowzy groans");

        sound(OPSoundEvents.GUZZLER_SHOOT, "Guzzler shoots");

        sound(OPSoundEvents.PALE_SPIDER_DEATH, "Pale Spider dies");
        sound(OPSoundEvents.PALE_SPIDER_HURT, "Pale Spider hurts");
        sound(OPSoundEvents.PALE_SPIDER_IDLE, "Pale Spider hisses");

        sound(OPSoundEvents.RAMBLE_HURT, "Ramble hurt");
        sound(OPSoundEvents.RAMBLE_DEATH, "Ramble dies");
        sound(OPSoundEvents.RAMBLE_ATTACK, "Ramble slices");
        sound(OPSoundEvents.RAMBLE_IDLE, "Ramble clatters");

        sound(OPSoundEvents.SLUG_DEATH, "Slug dies");
        sound(OPSoundEvents.SLUG_HURT, "Slug hurts");
        sound(OPSoundEvents.SLUG_SLIDE, "Footsteps");
        sound(OPSoundEvents.SLUG_EAT, "Slug eats");

        sound(OPSoundEvents.TESLA_BOW_CHARGED, "Tesla Bow loads");
        sound(OPSoundEvents.TESLA_BOW_SHOOT, "Tesla Bow fires");

        sound(OPSoundEvents.TREMBLER_BLOCK, "Trembler blocks");

        sound(OPSoundEvents.UMBER_SPIDER_DEATH, "Umber Spider dies");
        sound(OPSoundEvents.UMBER_SPIDER_HURT, "Umber Spider hurts");
        sound(OPSoundEvents.UMBER_SPIDER_IDLE, "Umber Spider groans");

        sound(OPSoundEvents.VOLT_DEATH, "Volt dies");
        sound(OPSoundEvents.VOLT_HURT, "Volt hurts");
        sound(OPSoundEvents.VOLT_IDLE, "Volt hums");
        sound(OPSoundEvents.VOLT_SHOOT, "Volt shoots");
        sound(OPSoundEvents.VOLT_SQUISH, "Volt squishes");

        sound(OPSoundEvents.WHIZZ_DEATH, "Whizz dies");
        sound(OPSoundEvents.WHIZZ_HURT, "Whizz hurts");
        sound(OPSoundEvents.WHIZZ_FLY, "Whizz buzzes");
        sound(OPSoundEvents.WHIZZ_ATTACK, "Whizz bites");

        addBlock(OPBlocks.INFESTED_AMETHYST_BLOCK, "Infested Block of Amethyst");

        // other
        addEnchantmentWithDesc(OPEnchantments.BIG_ELECTRIC_BALL.get(), "Increases the size of the fired electric charge");
        addEnchantmentWithDesc(OPEnchantments.BOUNCY_ELECTRIC_BALL.get(), "The fired electric charge bounces off blocks and passes through mobs");
        addEnchantmentWithDesc(OPEnchantments.KICKBACK.get(), "Launches the user backwards after firing");

        addEffect(OPEffects.ELECTRIFIED, "Electrified");
        addEffect(OPEffects.GLOOM_TOXIN, "Gloom Toxin");

        add("death.attack.opposing_force.electrified", "%1$s met a shocking end");
        add("death.attack.opposing_force.electrified.player", "%1$s was zapped by %2$s");
        add("death.attack.opposing_force.gloom_toxin", "%1$s was consumed by darkness");
        add("death.attack.opposing_force.gloom_toxin.player", "%1$s didn't reach the light");
        add("death.attack.opposing_force.laser", "%1$s was vaporized by %2$s");
        add("death.attack.opposing_force.tomahawk", "%1$s was domed by %2$s");
        add("death.attack.opposing_force.tomahawk.item", "%1$s was domed by %2$s using %3$s");

        add("attribute.opposing_force.name.generic.stealth", "Stealth");
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

    public void addTabName(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }
}
