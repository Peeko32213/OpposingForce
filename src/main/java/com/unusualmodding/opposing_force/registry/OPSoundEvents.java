package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, OpposingForce.MOD_ID);

    public static final RegistryObject<SoundEvent> ARMOR_EQUIP_DEEPWOVEN = registerSoundEvent("armor_equip_deepwoven");

    public static final RegistryObject<SoundEvent> DICER_HURT = registerSoundEvent("dicer_hurt");
    public static final RegistryObject<SoundEvent> DICER_DEATH = registerSoundEvent("dicer_death");
    public static final RegistryObject<SoundEvent> DICER_IDLE = registerSoundEvent("dicer_idle");
    public static final RegistryObject<SoundEvent> DICER_ATTACK = registerSoundEvent("dicer_attack");
    public static final RegistryObject<SoundEvent> DICER_LASER = registerSoundEvent("dicer_laser");

    public static final RegistryObject<SoundEvent> ELECTRIC_CHARGE = registerSoundEvent("electric_charge");
    public static final RegistryObject<SoundEvent> ELECTRIC_CHARGE_DISSIPATE = registerSoundEvent("electric_charge_dissipate");
    public static final RegistryObject<SoundEvent> ELECTRIC_CHARGE_ZAP = registerSoundEvent("electric_charge_zap");

    public static final RegistryObject<SoundEvent> GUZZLER_HURT = registerSoundEvent("guzzler_hurt");
    public static final RegistryObject<SoundEvent> GUZZLER_DEATH = registerSoundEvent("guzzler_death");
    public static final RegistryObject<SoundEvent> GUZZLER_IDLE = registerSoundEvent("guzzler_idle");
    public static final RegistryObject<SoundEvent> GUZZLER_SPEW = registerSoundEvent("guzzler_spew");

    public static final RegistryObject<SoundEvent> FIRE_SLIME_HURT = registerSoundEvent("fire_slime_hurt");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_DEATH = registerSoundEvent("fire_slime_death");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_IDLE = registerSoundEvent("fire_slime_idle");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_SQUISH = registerSoundEvent("fire_slime_squish");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_JUMP = registerSoundEvent("fire_slime_jump");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_ATTACK = registerSoundEvent("fire_slime_attack");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_POP = registerSoundEvent("fire_slime_pop");

    public static final RegistryObject<SoundEvent> FROWZY_HURT = registerSoundEvent("frowzy_hurt");
    public static final RegistryObject<SoundEvent> FROWZY_DEATH = registerSoundEvent("frowzy_death");
    public static final RegistryObject<SoundEvent> FROWZY_IDLE = registerSoundEvent("frowzy_idle");
    public static final RegistryObject<SoundEvent> FROWZY_ATTACK = registerSoundEvent("frowzy_attack");
    public static final RegistryObject<SoundEvent> FROWZY_RUN = registerSoundEvent("frowzy_run");

    public static final RegistryObject<SoundEvent> PALE_SPIDER_HURT = registerSoundEvent("pale_spider_hurt");
    public static final RegistryObject<SoundEvent> PALE_SPIDER_DEATH = registerSoundEvent("pale_spider_death");
    public static final RegistryObject<SoundEvent> PALE_SPIDER_IDLE = registerSoundEvent("pale_spider_idle");

    public static final RegistryObject<SoundEvent> RAMBLE_HURT = registerSoundEvent("ramble_hurt");
    public static final RegistryObject<SoundEvent> RAMBLE_DEATH = registerSoundEvent("ramble_death");
    public static final RegistryObject<SoundEvent> RAMBLE_IDLE = registerSoundEvent("ramble_idle");
    public static final RegistryObject<SoundEvent> RAMBLE_ATTACK = registerSoundEvent("ramble_attack");

    public static final RegistryObject<SoundEvent> SLUG_HURT = registerSoundEvent("slug_hurt");
    public static final RegistryObject<SoundEvent> SLUG_DEATH = registerSoundEvent("slug_death");
    public static final RegistryObject<SoundEvent> SLUG_SLIDE = registerSoundEvent("slug_slide");
    public static final RegistryObject<SoundEvent> SLUG_EAT = registerSoundEvent("slug_eat");
    public static final RegistryObject<SoundEvent> SLUG_ATTACK = registerSoundEvent("slug_attack");

    public static final RegistryObject<SoundEvent> TERROR_HURT = registerSoundEvent("terror_hurt");
    public static final RegistryObject<SoundEvent> TERROR_DEATH = registerSoundEvent("terror_death");
    public static final RegistryObject<SoundEvent> TERROR_IDLE = registerSoundEvent("terror_idle");
    public static final RegistryObject<SoundEvent> TERROR_FLOP = registerSoundEvent("terror_flop");

    public static final RegistryObject<SoundEvent> TESLA_BOW_CHARGED = registerSoundEvent("tesla_bow_charged");
    public static final RegistryObject<SoundEvent> TESLA_BOW_SHOOT = registerSoundEvent("tesla_bow_shoot");

    public static final RegistryObject<SoundEvent> TREMBLER_DEATH = registerSoundEvent("trembler_death");
    public static final RegistryObject<SoundEvent> TREMBLER_HURT = registerSoundEvent("trembler_hurt");
    public static final RegistryObject<SoundEvent> TREMBLER_IDLE = registerSoundEvent("trembler_idle");
    public static final RegistryObject<SoundEvent> TREMBLER_IDLE_FUNNY = registerSoundEvent("trembler_idle_funny");
    public static final RegistryObject<SoundEvent> TREMBLER_BLOCK = registerSoundEvent("trembler_block");

    public static final RegistryObject<SoundEvent> UMBER_SPIDER_HURT = registerSoundEvent("umber_spider_hurt");
    public static final RegistryObject<SoundEvent> UMBER_SPIDER_DEATH = registerSoundEvent("umber_spider_death");
    public static final RegistryObject<SoundEvent> UMBER_SPIDER_IDLE = registerSoundEvent("umber_spider_idle");

    public static final RegistryObject<SoundEvent> VOLT_DEATH = registerSoundEvent("volt_death");
    public static final RegistryObject<SoundEvent> VOLT_HURT = registerSoundEvent("volt_hurt");
    public static final RegistryObject<SoundEvent> VOLT_IDLE = registerSoundEvent("volt_idle");
    public static final RegistryObject<SoundEvent> VOLT_SHOOT = registerSoundEvent("volt_shoot");
    public static final RegistryObject<SoundEvent> VOLT_SQUISH = registerSoundEvent("volt_squish");

    public static final RegistryObject<SoundEvent> WHIZZ_DEATH = registerSoundEvent("whizz_death");
    public static final RegistryObject<SoundEvent> WHIZZ_HURT = registerSoundEvent("whizz_hurt");
    public static final RegistryObject<SoundEvent> WHIZZ_FLY = registerSoundEvent("whizz_fly");
    public static final RegistryObject<SoundEvent> WHIZZ_ATTACK = registerSoundEvent("whizz_attack");

    private static RegistryObject<SoundEvent> registerSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(OpposingForce.MOD_ID, soundName)));
    }
}
