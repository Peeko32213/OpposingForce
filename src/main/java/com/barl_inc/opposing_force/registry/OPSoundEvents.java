package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("deprecation")
public class OPSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, OpposingForce.MOD_ID);

    public static final DeferredHolder<SoundEvent, ?> ARMOR_EQUIP_DEEPWOVEN = registerSoundEvent("armor_equip_deepwoven");
    public static final DeferredHolder<SoundEvent, ?> ARMOR_EQUIP_WOODEN = registerSoundEvent("armor_equip_wooden");
    public static final DeferredHolder<SoundEvent, ?> ARMOR_EQUIP_STONE = registerSoundEvent("armor_equip_stone");
    public static final DeferredHolder<SoundEvent, ?> ARMOR_EQUIP_EMERALD = registerSoundEvent("armor_equip_emerald");
    public static final DeferredHolder<SoundEvent, ?> ARMOR_EQUIP_MOON_SHOES = registerSoundEvent("armor_equip_moon_shoes");

    public static final DeferredHolder<SoundEvent, ?> DICER_HURT = registerSoundEvent("dicer_hurt");
    public static final DeferredHolder<SoundEvent, ?> DICER_DEATH = registerSoundEvent("dicer_death");
    public static final DeferredHolder<SoundEvent, ?> DICER_IDLE = registerSoundEvent("dicer_idle");
    public static final DeferredHolder<SoundEvent, ?> DICER_ATTACK = registerSoundEvent("dicer_attack");
    public static final DeferredHolder<SoundEvent, ?> DICER_LASER = registerSoundEvent("dicer_laser");

    public static final DeferredHolder<SoundEvent, ?> ELECTRIC_CHARGE = registerSoundEvent("electric_charge");
    public static final DeferredHolder<SoundEvent, ?> ELECTRIC_CHARGE_DISSIPATE = registerSoundEvent("electric_charge_dissipate");
    public static final DeferredHolder<SoundEvent, ?> ELECTRIC_CHARGE_ZAP = registerSoundEvent("electric_charge_zap");

    public static final DeferredHolder<SoundEvent, ?> GUZZLER_HURT = registerSoundEvent("guzzler_hurt");
    public static final DeferredHolder<SoundEvent, ?> GUZZLER_DEATH = registerSoundEvent("guzzler_death");
    public static final DeferredHolder<SoundEvent, ?> GUZZLER_IDLE = registerSoundEvent("guzzler_idle");
    public static final DeferredHolder<SoundEvent, ?> GUZZLER_SPEW = registerSoundEvent("guzzler_spew");
    public static final DeferredHolder<SoundEvent, ?> GUZZLER_SLAM = registerSoundEvent("guzzler_slam");

    public static final DeferredHolder<SoundEvent, ?> FIRE_SLIME_HURT = registerSoundEvent("fire_slime_hurt");
    public static final DeferredHolder<SoundEvent, ?> FIRE_SLIME_DEATH = registerSoundEvent("fire_slime_death");
    public static final DeferredHolder<SoundEvent, ?> FIRE_SLIME_IDLE = registerSoundEvent("fire_slime_idle");
    public static final DeferredHolder<SoundEvent, ?> FIRE_SLIME_SQUISH = registerSoundEvent("fire_slime_squish");
    public static final DeferredHolder<SoundEvent, ?> FIRE_SLIME_JUMP = registerSoundEvent("fire_slime_jump");
    public static final DeferredHolder<SoundEvent, ?> FIRE_SLIME_ATTACK = registerSoundEvent("fire_slime_attack");
    public static final DeferredHolder<SoundEvent, ?> FIRE_SLIME_POP = registerSoundEvent("fire_slime_pop");

    public static final DeferredHolder<SoundEvent, ?> FROWZY_HURT = registerSoundEvent("frowzy_hurt");
    public static final DeferredHolder<SoundEvent, ?> FROWZY_DEATH = registerSoundEvent("frowzy_death");
    public static final DeferredHolder<SoundEvent, ?> FROWZY_IDLE = registerSoundEvent("frowzy_idle");
    public static final DeferredHolder<SoundEvent, ?> FROWZY_ATTACK = registerSoundEvent("frowzy_attack");
    public static final DeferredHolder<SoundEvent, ?> FROWZY_RUN = registerSoundEvent("frowzy_run");

    public static final DeferredHolder<SoundEvent, ?> HANGING_SPIDER_HURT = registerSoundEvent("hanging_spider_hurt");
    public static final DeferredHolder<SoundEvent, ?> HANGING_SPIDER_DEATH = registerSoundEvent("hanging_spider_death");
    public static final DeferredHolder<SoundEvent, ?> HANGING_SPIDER_IDLE = registerSoundEvent("hanging_spider_idle");

    public static final DeferredHolder<SoundEvent, ?> RAMBLER_HURT = registerSoundEvent("rambler_hurt");
    public static final DeferredHolder<SoundEvent, ?> RAMBLER_DEATH = registerSoundEvent("rambler_death");
    public static final DeferredHolder<SoundEvent, ?> RAMBLER_IDLE = registerSoundEvent("rambler_idle");
    public static final DeferredHolder<SoundEvent, ?> RAMBLER_ATTACK = registerSoundEvent("rambler_attack");

    public static final DeferredHolder<SoundEvent, ?> SLUG_HURT = registerSoundEvent("slug_hurt");
    public static final DeferredHolder<SoundEvent, ?> SLUG_DEATH = registerSoundEvent("slug_death");
    public static final DeferredHolder<SoundEvent, ?> SLUG_SLIDE = registerSoundEvent("slug_slide");
    public static final DeferredHolder<SoundEvent, ?> SLUG_EAT = registerSoundEvent("slug_eat");
    public static final DeferredHolder<SoundEvent, ?> SLUG_ATTACK = registerSoundEvent("slug_attack");

    public static final DeferredHolder<SoundEvent, ?> TART_HURT = registerSoundEvent("tart_hurt");
    public static final DeferredHolder<SoundEvent, ?> TART_DEATH = registerSoundEvent("tart_death");
    public static final DeferredHolder<SoundEvent, ?> TART_IDLE = registerSoundEvent("tart_idle");
    public static final DeferredHolder<SoundEvent, ?> TART_ATTACK = registerSoundEvent("tart_attack");
    public static final DeferredHolder<SoundEvent, ?> TART_PLUCK = registerSoundEvent("tart_pluck");

    public static final DeferredHolder<SoundEvent, ?> TERROR_HURT = registerSoundEvent("terror_hurt");
    public static final DeferredHolder<SoundEvent, ?> TERROR_DEATH = registerSoundEvent("terror_death");
    public static final DeferredHolder<SoundEvent, ?> TERROR_IDLE = registerSoundEvent("terror_idle");
    public static final DeferredHolder<SoundEvent, ?> TERROR_FLOP = registerSoundEvent("terror_flop");
    public static final DeferredHolder<SoundEvent, ?> TERROR_SAW_START = registerSoundEvent("terror_saw_start");
    public static final DeferredHolder<SoundEvent, ?> TERROR_SAW = registerSoundEvent("terror_saw");
    public static final DeferredHolder<SoundEvent, ?> TERROR_SAW_END = registerSoundEvent("terror_saw_end");

    public static final DeferredHolder<SoundEvent, ?> TESLA_BOW_CHARGED = registerSoundEvent("tesla_bow_charged");
    public static final DeferredHolder<SoundEvent, ?> TESLA_BOW_SHOOT = registerSoundEvent("tesla_bow_shoot");

    public static final DeferredHolder<SoundEvent, ?> TREMBLER_DEATH = registerSoundEvent("trembler_death");
    public static final DeferredHolder<SoundEvent, ?> TREMBLER_HURT = registerSoundEvent("trembler_hurt");
    public static final DeferredHolder<SoundEvent, ?> TREMBLER_IDLE = registerSoundEvent("trembler_idle");
    public static final DeferredHolder<SoundEvent, ?> TREMBLER_IDLE_FUNNY = registerSoundEvent("trembler_idle_funny");
    public static final DeferredHolder<SoundEvent, ?> TREMBLER_BLOCK = registerSoundEvent("trembler_block");

    public static final DeferredHolder<SoundEvent, ?> UMBER_SPIDER_HURT = registerSoundEvent("umber_spider_hurt");
    public static final DeferredHolder<SoundEvent, ?> UMBER_SPIDER_DEATH = registerSoundEvent("umber_spider_death");
    public static final DeferredHolder<SoundEvent, ?> UMBER_SPIDER_IDLE = registerSoundEvent("umber_spider_idle");

    public static final DeferredHolder<SoundEvent, ?> VOLT_DEATH = registerSoundEvent("volt_death");
    public static final DeferredHolder<SoundEvent, ?> VOLT_HURT = registerSoundEvent("volt_hurt");
    public static final DeferredHolder<SoundEvent, ?> VOLT_IDLE = registerSoundEvent("volt_idle");
    public static final DeferredHolder<SoundEvent, ?> VOLT_SHOOT = registerSoundEvent("volt_shoot");
    public static final DeferredHolder<SoundEvent, ?> VOLT_SQUISH = registerSoundEvent("volt_squish");

    public static final DeferredHolder<SoundEvent, ?> WHIZZ_DEATH = registerSoundEvent("whizz_death");
    public static final DeferredHolder<SoundEvent, ?> WHIZZ_HURT = registerSoundEvent("whizz_hurt");
    public static final DeferredHolder<SoundEvent, ?> WHIZZ_FLY = registerSoundEvent("whizz_fly");
    public static final DeferredHolder<SoundEvent, ?> WHIZZ_ATTACK = registerSoundEvent("whizz_attack");

    public static final DeferredHolder<SoundEvent, ?> SKYVERN_DEATH = registerSoundEvent("skyvern_death");
    public static final DeferredHolder<SoundEvent, ?> SKYVERN_HURT = registerSoundEvent("skyvern_hurt");
    public static final DeferredHolder<SoundEvent, ?> SKYVERN_IDLE = registerSoundEvent("skyvern_idle");
    public static final DeferredHolder<SoundEvent, ?> SKYVERN_IDLE_HOSTILE = registerSoundEvent("skyvern_idle_hostile");
    public static final DeferredHolder<SoundEvent, ?> SKYVERN_ROAR = registerSoundEvent("skyvern_roar");
    public static final DeferredHolder<SoundEvent, ?> SKYVERN_CHARGE_WARN = registerSoundEvent("skyvern_charge_warn");
    public static final DeferredHolder<SoundEvent, ?> SKYVERN_LOOP = registerSoundEvent("skyvern_loop");
    public static final DeferredHolder<SoundEvent, ?> SKYVERN_WHOOSH = registerSoundEvent("skyvern_whoosh");

    public static final DeferredHolder<SoundEvent, ?> LASER_BOLT_IMPACT = registerSoundEvent("laser_bolt_impact");

    public static final DeferredHolder<SoundEvent, ?> BLASTER_SHOOT = registerSoundEvent("blaster_shoot");

    public static final DeferredHolder<SoundEvent, ?> LASER_BLADE_BLOCK = registerSoundEvent("laser_blade_block");
    public static final DeferredHolder<SoundEvent, ?> LASER_BLADE_HIT = registerSoundEvent("laser_blade_hit");
    public static final DeferredHolder<SoundEvent, ?> LASER_BLADE_SWING = registerSoundEvent("laser_blade_swing");
    public static final DeferredHolder<SoundEvent, ?> LASER_BLADE_SPIN = registerSoundEvent("laser_blade_spin");
    public static final DeferredHolder<SoundEvent, ?> LASER_BLADE_CATCH = registerSoundEvent("laser_blade_catch");

    public static final DeferredHolder<SoundEvent, ?> SAWBLADE_SAW_START = registerSoundEvent("sawblade_saw_start");
    public static final DeferredHolder<SoundEvent, ?> SAWBLADE_SAW = registerSoundEvent("sawblade_saw");
    public static final DeferredHolder<SoundEvent, ?> SAWBLADE_SAW_END = registerSoundEvent("sawblade_saw_end");
    public static final DeferredHolder<SoundEvent, ?> SAWBLADE_SWING = registerSoundEvent("sawblade_swing");

    public static final DeferredHolder<SoundEvent, ?> WALTZ_OF_THE_SLUG_DISC = registerSoundEvent("waltz_of_the_slug_disc");
    public static final DeferredHolder<SoundEvent, ?> SLAYSER_DISC = registerSoundEvent("slayser_disc");

    public static final DeferredHolder<SoundEvent, ?> NOTE_BLOCK_IMITATE_DICER = registerSoundEvent("note_block_imitate_dicer");
    public static final DeferredHolder<SoundEvent,?> NOTE_BLOCK_IMITATE_FROWZY = registerSoundEvent("note_block_imitate_frowzy");
    public static final DeferredHolder<SoundEvent,?> NOTE_BLOCK_IMITATE_RAMBLER = registerSoundEvent("note_block_imitate_rambler");
    public static final DeferredHolder<SoundEvent,?> NOTE_BLOCK_IMITATE_TART = registerSoundEvent("note_block_imitate_tart");
    public static final DeferredHolder<SoundEvent,?> NOTE_BLOCK_IMITATE_WHIZZ = registerSoundEvent("note_block_imitate_whizz");

    private static DeferredHolder<SoundEvent, ?> registerSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> SoundEvent.createVariableRangeEvent( ResourceLocation.fromNamespaceAndPath(OpposingForce.MOD_ID, soundName)));
    }

    private static Holder.Reference<SoundEvent> registerSoundEventHolder(String name) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, OpposingForce.modPrefix(name), SoundEvent.createVariableRangeEvent(OpposingForce.modPrefix(name)));
    }
}
