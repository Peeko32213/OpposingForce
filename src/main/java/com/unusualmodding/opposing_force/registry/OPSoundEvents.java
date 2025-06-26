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

    public static final RegistryObject<SoundEvent> ARMOR_EQUIP_DEEPWOVEN = createSoundEvent("armor_equip_deepwoven");

    // mobs
    public static final RegistryObject<SoundEvent> DICER_HURT = createSoundEvent("dicer_hurt");
    public static final RegistryObject<SoundEvent> DICER_DEATH = createSoundEvent("dicer_death");
    public static final RegistryObject<SoundEvent> DICER_IDLE = createSoundEvent("dicer_idle");
    public static final RegistryObject<SoundEvent> DICER_ATTACK = createSoundEvent("dicer_attack");
    public static final RegistryObject<SoundEvent> DICER_LASER = createSoundEvent("dicer_laser");

    public static final RegistryObject<SoundEvent> ELECTRIC_CHARGE = createSoundEvent("electric_charge");
    public static final RegistryObject<SoundEvent> ELECTRIC_CHARGE_DISSIPATE = createSoundEvent("electric_charge_dissipate");
    public static final RegistryObject<SoundEvent> ELECTRIC_CHARGE_ZAP = createSoundEvent("electric_charge_zap");

    public static final RegistryObject<SoundEvent> GUZZLER_HURT = createSoundEvent("guzzler_hurt");
    public static final RegistryObject<SoundEvent> GUZZLER_DEATH = createSoundEvent("guzzler_death");
    public static final RegistryObject<SoundEvent> GUZZLER_IDLE = createSoundEvent("guzzler_idle");
    public static final RegistryObject<SoundEvent> GUZZLER_SPEW = createSoundEvent("guzzler_spew");

    public static final RegistryObject<SoundEvent> FIRE_SLIME_HURT = createSoundEvent("fire_slime_hurt");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_DEATH = createSoundEvent("fire_slime_death");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_IDLE = createSoundEvent("fire_slime_idle");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_SQUISH = createSoundEvent("fire_slime_squish");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_JUMP = createSoundEvent("fire_slime_jump");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_ATTACK = createSoundEvent("fire_slime_attack");
    public static final RegistryObject<SoundEvent> FIRE_SLIME_POP = createSoundEvent("fire_slime_pop");

    public static final RegistryObject<SoundEvent> FROWZY_HURT = createSoundEvent("frowzy_hurt");
    public static final RegistryObject<SoundEvent> FROWZY_DEATH = createSoundEvent("frowzy_death");
    public static final RegistryObject<SoundEvent> FROWZY_IDLE = createSoundEvent("frowzy_idle");
    public static final RegistryObject<SoundEvent> FROWZY_ATTACK = createSoundEvent("frowzy_attack");
    public static final RegistryObject<SoundEvent> FROWZY_RUN = createSoundEvent("frowzy_run");

    public static final RegistryObject<SoundEvent> PALE_SPIDER_HURT = createSoundEvent("pale_spider_hurt");
    public static final RegistryObject<SoundEvent> PALE_SPIDER_DEATH = createSoundEvent("pale_spider_death");
    public static final RegistryObject<SoundEvent> PALE_SPIDER_IDLE = createSoundEvent("pale_spider_idle");

    public static final RegistryObject<SoundEvent> RAMBLE_HURT = createSoundEvent("ramble_hurt");
    public static final RegistryObject<SoundEvent> RAMBLE_DEATH = createSoundEvent("ramble_death");
    public static final RegistryObject<SoundEvent> RAMBLE_IDLE = createSoundEvent("ramble_idle");
    public static final RegistryObject<SoundEvent> RAMBLE_ATTACK = createSoundEvent("ramble_attack");

    public static final RegistryObject<SoundEvent> SLUG_HURT = createSoundEvent("slug_hurt");
    public static final RegistryObject<SoundEvent> SLUG_DEATH = createSoundEvent("slug_death");
    public static final RegistryObject<SoundEvent> SLUG_SLIDE = createSoundEvent("slug_slide");
    public static final RegistryObject<SoundEvent> SLUG_EAT = createSoundEvent("slug_eat");
    public static final RegistryObject<SoundEvent> SLUG_ATTACK = createSoundEvent("slug_attack");

    public static final RegistryObject<SoundEvent> TERROR_HURT = createSoundEvent("terror_hurt");
    public static final RegistryObject<SoundEvent> TERROR_DEATH = createSoundEvent("terror_death");
    public static final RegistryObject<SoundEvent> TERROR_IDLE = createSoundEvent("terror_idle");
    public static final RegistryObject<SoundEvent> TERROR_FLOP = createSoundEvent("terror_flop");

    public static final RegistryObject<SoundEvent> TESLA_BOW_CHARGED = createSoundEvent("tesla_bow_charged");
    public static final RegistryObject<SoundEvent> TESLA_BOW_SHOOT = createSoundEvent("tesla_bow_shoot");

    public static final RegistryObject<SoundEvent> TREMBLER_BLOCK = createSoundEvent("trembler_block");

    public static final RegistryObject<SoundEvent> UMBER_SPIDER_HURT = createSoundEvent("umber_spider_hurt");
    public static final RegistryObject<SoundEvent> UMBER_SPIDER_DEATH = createSoundEvent("umber_spider_death");
    public static final RegistryObject<SoundEvent> UMBER_SPIDER_IDLE = createSoundEvent("umber_spider_idle");

    public static final RegistryObject<SoundEvent> VOLT_DEATH = createSoundEvent("volt_death");
    public static final RegistryObject<SoundEvent> VOLT_HURT = createSoundEvent("volt_hurt");
    public static final RegistryObject<SoundEvent> VOLT_IDLE = createSoundEvent("volt_idle");
    public static final RegistryObject<SoundEvent> VOLT_SHOOT = createSoundEvent("volt_shoot");
    public static final RegistryObject<SoundEvent> VOLT_SQUISH = createSoundEvent("volt_squish");

    public static final RegistryObject<SoundEvent> WHIZZ_DEATH = createSoundEvent("whizz_death");
    public static final RegistryObject<SoundEvent> WHIZZ_HURT = createSoundEvent("whizz_hurt");
    public static final RegistryObject<SoundEvent> WHIZZ_FLY = createSoundEvent("whizz_fly");
    public static final RegistryObject<SoundEvent> WHIZZ_ATTACK = createSoundEvent("whizz_attack");

    private static RegistryObject<SoundEvent> createSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(OpposingForce.MOD_ID, soundName)));
    }
}
