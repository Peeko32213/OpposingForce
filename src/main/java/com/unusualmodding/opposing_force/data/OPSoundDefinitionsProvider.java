package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class OPSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public OPSoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, OpposingForce.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.sound(OPSoundEvents.ARMOR_EQUIP_DEEPWOVEN,
                sound("item/armor/equip_leather1").pitch(0.88F),
                sound("item/armor/equip_leather2").pitch(0.88F),
                sound("item/armor/equip_leather3").pitch(0.88F),
                sound("item/armor/equip_leather4").pitch(0.88F),
                sound("item/armor/equip_leather5").pitch(0.88F),
                sound("item/armor/equip_leather6").pitch(0.88F)
        );

        this.sound(OPSoundEvents.ARMOR_EQUIP_WOODEN,
                sound("dig/wood1").pitch(0.8F),
                sound("dig/wood2").pitch(0.8F),
                sound("dig/wood3").pitch(0.8F),
                sound("dig/wood4").pitch(0.8F)
        );

        this.sound(OPSoundEvents.ARMOR_EQUIP_STONE,
                sound("dig/stone1").pitch(0.8F),
                sound("dig/stone2").pitch(0.8F),
                sound("dig/stone3").pitch(0.8F),
                sound("dig/stone4").pitch(0.8F)
        );

        this.sound(OPSoundEvents.ARMOR_EQUIP_EMERALD,
                sound("item/armor/equip_diamond1").pitch(0.9F),
                sound("item/armor/equip_diamond2").pitch(0.9F),
                sound("item/armor/equip_diamond3").pitch(0.9F),
                sound("item/armor/equip_diamond4").pitch(0.9F),
                sound("item/armor/equip_diamond5").pitch(0.9F),
                sound("item/armor/equip_diamond6").pitch(0.9F)
        );

        this.sound(OPSoundEvents.ARMOR_EQUIP_MOON_SHOES,
                sound("item/armor/equip_leather1").pitch(0.88F),
                sound("item/armor/equip_leather2").pitch(0.88F),
                sound("item/armor/equip_leather3").pitch(0.88F),
                sound("item/armor/equip_leather4").pitch(0.88F),
                sound("item/armor/equip_leather5").pitch(0.88F),
                sound("item/armor/equip_leather6").pitch(0.88F)
        );

        this.sound(OPSoundEvents.DICER_HURT,
                sound(OpposingForce.modPrefix("entity/dicer/hurt1")).volume(0.9F),
                sound(OpposingForce.modPrefix("entity/dicer/hurt2")).volume(0.9F)
        );
        this.sound(OPSoundEvents.DICER_DEATH,
                sound(OpposingForce.modPrefix("entity/dicer/death1")).volume(0.9F)
        );
        this.sound(OPSoundEvents.DICER_IDLE,
                sound(OpposingForce.modPrefix("entity/dicer/idle1")).volume(0.8F),
                sound(OpposingForce.modPrefix("entity/dicer/idle2")).volume(0.8F)
        );
        this.sound(OPSoundEvents.DICER_ATTACK,
                sound(OpposingForce.modPrefix("entity/dicer/attack1")),
                sound(OpposingForce.modPrefix("entity/dicer/attack2"))
        );
        this.sound(OPSoundEvents.DICER_LASER,
                sound(OpposingForce.modPrefix("entity/dicer/laser1")).attenuationDistance(20)
        );

        this.sound(OPSoundEvents.ELECTRIC_CHARGE,
                sound(OpposingForce.modPrefix("entity/electric_charge/loop1")).attenuationDistance(20)
        );
        this.sound(OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE,
                sound(OpposingForce.modPrefix("entity/electric_charge/dissipate1"))
        );
        this.sound(OPSoundEvents.ELECTRIC_CHARGE_ZAP,
                sound(OpposingForce.modPrefix("entity/electric_charge/zap1"))
        );

        this.sound(OPSoundEvents.FIRE_SLIME_HURT,
                sound("mob/slime/small1").pitch(0.9F),
                sound("mob/slime/small2").pitch(0.9F),
                sound("mob/slime/small3").pitch(0.9F),
                sound("mob/slime/small4").pitch(0.9F),
                sound("mob/slime/small5").pitch(0.9F)
        );
        this.sound(OPSoundEvents.FIRE_SLIME_DEATH,
                sound("mob/slime/small1").pitch(0.9F),
                sound("mob/slime/small2").pitch(0.9F),
                sound("mob/slime/small3").pitch(0.9F),
                sound("mob/slime/small4").pitch(0.9F),
                sound("mob/slime/small5").pitch(0.9F)
        );
        this.sound(OPSoundEvents.FIRE_SLIME_IDLE,
                sound(OpposingForce.modPrefix("entity/fire_slime/idle1")).volume(0.3F)
        );
        this.sound(OPSoundEvents.FIRE_SLIME_SQUISH,
                sound(OpposingForce.modPrefix("entity/fire_slime/jump1")).volume(0.35F)
        );
        this.sound(OPSoundEvents.FIRE_SLIME_JUMP,
                sound(OpposingForce.modPrefix("entity/fire_slime/jump1")).volume(0.35F)
        );
        this.sound(OPSoundEvents.FIRE_SLIME_ATTACK,
                sound("mob/slime/attack1"),
                sound("mob/slime/attack2")
        );
        this.sound(OPSoundEvents.FIRE_SLIME_POP,
                sound("liquid/lavapop").pitch(0.9F)
        );

        this.sound(OPSoundEvents.FROWZY_HURT,
                sound(OpposingForce.modPrefix("entity/frowzy/hurt1")),
                sound(OpposingForce.modPrefix("entity/frowzy/hurt2")),
                sound(OpposingForce.modPrefix("entity/frowzy/hurt3"))
        );
        this.sound(OPSoundEvents.FROWZY_DEATH,
                sound(OpposingForce.modPrefix("entity/frowzy/death1"))
        );
        this.sound(OPSoundEvents.FROWZY_IDLE,
                sound(OpposingForce.modPrefix("entity/frowzy/idle1")),
                sound(OpposingForce.modPrefix("entity/frowzy/idle2")),
                sound(OpposingForce.modPrefix("entity/frowzy/idle3"))
        );
        this.sound(OPSoundEvents.FROWZY_ATTACK,
                sound(OpposingForce.modPrefix("entity/frowzy/attack1")),
                sound(OpposingForce.modPrefix("entity/frowzy/attack2")),
                sound(OpposingForce.modPrefix("entity/frowzy/attack3"))
        );
        this.sound(OPSoundEvents.FROWZY_RUN,
                sound(OpposingForce.modPrefix("entity/frowzy/run1")).volume(0.6F)
        );

        this.sound(OPSoundEvents.GUZZLER_HURT,
                sound(OpposingForce.modPrefix("entity/guzzler/hurt1")).volume(0.75F)
        );
        this.sound(OPSoundEvents.GUZZLER_DEATH,
                sound(OpposingForce.modPrefix("entity/guzzler/death1"))
        );
        this.sound(OPSoundEvents.GUZZLER_IDLE,
                sound(OpposingForce.modPrefix("entity/guzzler/idle1"))
        );
        this.sound(OPSoundEvents.GUZZLER_SPEW,
                sound(OpposingForce.modPrefix("entity/guzzler/spew1"))
        );

        this.sound(OPSoundEvents.PALE_SPIDER_HURT,
                sound("mob/spider/say1").pitch(1.2F),
                sound("mob/spider/say2").pitch(1.2F),
                sound("mob/spider/say3").pitch(1.2F),
                sound("mob/spider/say4").pitch(1.2F)
        );
        this.sound(OPSoundEvents.PALE_SPIDER_DEATH,
                sound("mob/spider/death").pitch(1.2F)
        );
        this.sound(OPSoundEvents.PALE_SPIDER_IDLE,
                sound("mob/spider/say1").pitch(1.2F),
                sound("mob/spider/say2").pitch(1.2F),
                sound("mob/spider/say3").pitch(1.2F),
                sound("mob/spider/say4").pitch(1.2F)
        );

        this.sound(OPSoundEvents.RAMBLE_HURT,
                sound("mob/skeleton/hurt1").pitch(0.7F),
                sound("mob/skeleton/hurt2").pitch(0.7F),
                sound("mob/skeleton/hurt3").pitch(0.7F),
                sound("mob/skeleton/hurt4").pitch(0.7F)
        );
        this.sound(OPSoundEvents.RAMBLE_DEATH,
                sound(OpposingForce.modPrefix("entity/ramble/death1"))
        );
        this.sound(OPSoundEvents.RAMBLE_IDLE,
                sound(OpposingForce.modPrefix("entity/ramble/idle1"))
        );
        this.sound(OPSoundEvents.RAMBLE_ATTACK,
                sound("mob/skeleton/hurt1").volume(0.8F),
                sound("mob/skeleton/hurt2").volume(0.8F),
                sound("mob/skeleton/hurt3").volume(0.8F),
                sound("mob/skeleton/hurt4").volume(0.8F)
        );

        this.sound(OPSoundEvents.SLUG_HURT,
                sound("mob/slime/big1"),
                sound("mob/slime/big2"),
                sound("mob/slime/big3"),
                sound("mob/slime/big4")
        );
        this.sound(OPSoundEvents.SLUG_DEATH,
                sound("mob/slime/big1").pitch(0.8F),
                sound("mob/slime/big2").pitch(0.8F),
                sound("mob/slime/big3").pitch(0.8F),
                sound("mob/slime/big4").pitch(0.8F)
        );
        this.sound(OPSoundEvents.SLUG_SLIDE,
                sound(OpposingForce.modPrefix("entity/slug/walk1")),
                sound(OpposingForce.modPrefix("entity/slug/walk2")),
                sound(OpposingForce.modPrefix("entity/slug/walk3"))
        );
        this.sound(OPSoundEvents.SLUG_EAT,
                sound("mob/strider/eat1").pitch(1.1F),
                sound("mob/strider/eat2").pitch(1.1F),
                sound("mob/strider/eat3").pitch(1.1F)
        );
        this.sound(OPSoundEvents.SLUG_ATTACK,
                sound("mob/slime/attack1").pitch(0.84F),
                sound("mob/slime/attack2").pitch(0.84F)
        );

        this.sound(OPSoundEvents.TERROR_HURT,
                sound(OpposingForce.modPrefix("entity/terror/hurt1")).pitch(1.2F)
        );
        this.sound(OPSoundEvents.TERROR_DEATH,
                sound(OpposingForce.modPrefix("entity/terror/death1"))
        );
        this.sound(OPSoundEvents.TERROR_IDLE,
                sound(OpposingForce.modPrefix("entity/terror/idle1")),
                sound(OpposingForce.modPrefix("entity/terror/idle2"))
        );
        this.sound(OPSoundEvents.TERROR_FLOP,
                sound("entity/fish/flop1").volume(0.3F).pitch(0.8F),
                sound("entity/fish/flop2").volume(0.3F).pitch(0.8F),
                sound("entity/fish/flop3").volume(0.3F).pitch(0.8F),
                sound("entity/fish/flop4").volume(0.3F).pitch(0.8F)
        );

        this.sound(OPSoundEvents.TESLA_BOW_CHARGED,
                sound(OpposingForce.modPrefix("item/tesla_bow/charged1"))
        );
        this.sound(OPSoundEvents.TESLA_BOW_SHOOT,
                sound(OpposingForce.modPrefix("item/tesla_bow/shoot1"))
        );

        this.sound(OPSoundEvents.TREMBLER_DEATH,
                sound(OpposingForce.modPrefix("entity/trembler/death1"))
        );
        this.sound(OPSoundEvents.TREMBLER_HURT,
                sound(OpposingForce.modPrefix("entity/trembler/hurt1")).pitch(1.2F)
        );
        this.sound(OPSoundEvents.TREMBLER_IDLE,
                sound(OpposingForce.modPrefix("entity/trembler/idle1"))
        );
        this.sound(OPSoundEvents.TREMBLER_IDLE_FUNNY,
                sound(OpposingForce.modPrefix("entity/trembler/idle_funny1"))
        );
        this.sound(OPSoundEvents.TREMBLER_BLOCK,
                sound("item/shield/block1").pitch(1.2F),
                sound("item/shield/block2").pitch(1.2F),
                sound("item/shield/block3").pitch(1.2F),
                sound("item/shield/block4").pitch(1.2F),
                sound("item/shield/block5").pitch(1.2F)
        );

        this.sound(OPSoundEvents.UMBER_SPIDER_HURT,
                sound(OpposingForce.modPrefix("entity/umber_spider/hurt1"))
        );
        this.sound(OPSoundEvents.UMBER_SPIDER_DEATH,
                sound(OpposingForce.modPrefix("entity/umber_spider/death1"))
        );
        this.sound(OPSoundEvents.UMBER_SPIDER_IDLE,
                sound(OpposingForce.modPrefix("entity/umber_spider/idle1"))
        );

        this.sound(OPSoundEvents.VOLT_HURT,
                sound(OpposingForce.modPrefix("entity/volt/hurt1"))
        );
        this.sound(OPSoundEvents.VOLT_DEATH,
                sound(OpposingForce.modPrefix("entity/volt/death1"))
        );
        this.sound(OPSoundEvents.VOLT_IDLE,
                sound(OpposingForce.modPrefix("entity/volt/idle1"))
        );
        this.sound(OPSoundEvents.VOLT_SHOOT,
                sound(OpposingForce.modPrefix("entity/volt/shoot1"))
        );
        this.sound(OPSoundEvents.VOLT_SQUISH,
                sound("mob/slime/small1").pitch(0.8F),
                sound("mob/slime/small2").pitch(0.8F),
                sound("mob/slime/small3").pitch(0.8F),
                sound("mob/slime/small4").pitch(0.8F),
                sound("mob/slime/small5").pitch(0.8F)
        );

        this.sound(OPSoundEvents.WHIZZ_DEATH,
                sound("block/amethyst_cluster/break1").pitch(1.2F),
                sound("block/amethyst_cluster/break2").pitch(1.2F),
                sound("block/amethyst_cluster/break3").pitch(1.2F),
                sound("block/amethyst_cluster/break4").pitch(1.2F)
        );
        this.sound(OPSoundEvents.WHIZZ_HURT,
                sound("block/amethyst/step1").pitch(1.3F),
                sound("block/amethyst/step2").pitch(1.3F),
                sound("block/amethyst/step3").pitch(1.3F),
                sound("block/amethyst/step4").pitch(1.3F),
                sound("block/amethyst/step5").pitch(1.3F),
                sound("block/amethyst/step6").pitch(1.3F),
                sound("block/amethyst/step7").pitch(1.3F),
                sound("block/amethyst/step8").pitch(1.3F),
                sound("block/amethyst/step9").pitch(1.3F),
                sound("block/amethyst/step10").pitch(1.3F),
                sound("block/amethyst/step11").pitch(1.3F),
                sound("block/amethyst/step12").pitch(1.3F)
        );
        this.sound(OPSoundEvents.WHIZZ_FLY,
                sound(OpposingForce.modPrefix("entity/whizz/loop1")).pitch(0.9F)
        );
        this.sound(OPSoundEvents.WHIZZ_ATTACK,
                sound("block/amethyst/resonate1").pitch(1.2F),
                sound("block/amethyst/resonate2").pitch(1.2F),
                sound("block/amethyst/resonate3").pitch(1.2F),
                sound("block/amethyst/resonate4").pitch(1.2F)
        );

        this.sound(OPSoundEvents.LASER_BOLT_IMPACT,
                sound(OpposingForce.modPrefix("entity/laser_bolt/impact1")).pitch(1.1F)
        );

        this.sound(OPSoundEvents.BLASTER_SHOOT,
                sound(OpposingForce.modPrefix("item/blaster/shoot1")).pitch(1.2F)
        );
    }

    private void soundDefinition(Supplier<SoundEvent> soundEvent, String subtitle, SoundDefinition.Sound... sounds) {
        this.add(soundEvent.get(), SoundDefinition.definition().subtitle("subtitles.opposing_force." + subtitle).with(sounds));
    }

    private void sound(Supplier<SoundEvent> soundEvent, SoundDefinition.Sound... sounds){
        this.soundDefinition(soundEvent, soundEvent.get().getLocation().getPath(), sounds);
    }
}
