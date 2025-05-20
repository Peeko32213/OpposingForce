package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPSounds;
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
        this.sound(OPSounds.DICER_HURT,
                sound(OpposingForce.modPrefix("entity/dicer/hurt_0")),
                sound(OpposingForce.modPrefix("entity/dicer/hurt_1"))
        );
        this.sound(OPSounds.DICER_DEATH,
                sound(OpposingForce.modPrefix("entity/dicer/death_0"))
        );
        this.sound(OPSounds.DICER_IDLE,
                sound(OpposingForce.modPrefix("entity/dicer/idle_0")),
                sound(OpposingForce.modPrefix("entity/dicer/idle_1"))
        );
        this.sound(OPSounds.DICER_ATTACK,
                sound(OpposingForce.modPrefix("entity/dicer/attack_0")),
                sound(OpposingForce.modPrefix("entity/dicer/attack_1"))
        );

        this.sound(OPSounds.ELECTRIC_CHARGE,
                sound(OpposingForce.modPrefix("entity/misc/electrical_charge_0"))
        );
        this.sound(OPSounds.ELECTRIC_CHARGE_DISSIPATE,
                sound(OpposingForce.modPrefix("entity/misc/electrical_charge_dissipate_0"))
        );

        this.sound(OPSounds.RAMBLE_HURT,
                sound("mob/skeleton/hurt1").pitch(0.65f),
                sound("mob/skeleton/hurt2").pitch(0.65f),
                sound("mob/skeleton/hurt3").pitch(0.65f),
                sound("mob/skeleton/hurt4").pitch(0.65f)
        );
        this.sound(OPSounds.RAMBLE_DEATH,
                sound(OpposingForce.modPrefix("entity/ramble/death_0"))
        );
        this.sound(OPSounds.RAMBLE_IDLE,
                sound(OpposingForce.modPrefix("entity/ramble/idle_0"))
        );
        this.sound(OPSounds.RAMBLE_ATTACK,
                sound("mob/skeleton/step1"),
                sound("mob/skeleton/step2"),
                sound("mob/skeleton/step3"),
                sound("mob/skeleton/step4")
        );

        this.sound(OPSounds.TESLA_BOW_CHARGED,
                sound(OpposingForce.modPrefix("item/tesla_bow/charged_0"))
        );
        this.sound(OPSounds.TESLA_BOW_SHOOT,
                sound(OpposingForce.modPrefix("item/tesla_bow/shoot_0"))
        );

        this.sound(OPSounds.UMBER_SPIDER_HURT,
                sound(OpposingForce.modPrefix("entity/umber_spider/hurt_0"))
        );
        this.sound(OPSounds.UMBER_SPIDER_DEATH,
                sound(OpposingForce.modPrefix("entity/umber_spider/death_0"))
        );
        this.sound(OPSounds.UMBER_SPIDER_IDLE,
                sound(OpposingForce.modPrefix("entity/umber_spider/idle_0"))
        );

        this.sound(OPSounds.VOLT_HURT,
                sound(OpposingForce.modPrefix("entity/volt/hurt_0"))
        );
        this.sound(OPSounds.VOLT_DEATH,
                sound(OpposingForce.modPrefix("entity/volt/death_0"))
        );
        this.sound(OPSounds.VOLT_IDLE,
                sound(OpposingForce.modPrefix("entity/volt/idle_0"))
        );
        this.sound(OPSounds.VOLT_SHOOT,
                sound(OpposingForce.modPrefix("entity/volt/shoot_0"))
        );
    }

    private void soundDefinition(Supplier<SoundEvent> soundEvent, String subtitle, SoundDefinition.Sound... sounds) {
        this.add(soundEvent.get(), SoundDefinition.definition().subtitle("subtitles." + subtitle).with(sounds));
    }

    private void sound(Supplier<SoundEvent> soundEvent, SoundDefinition.Sound... sounds){
        this.soundDefinition(soundEvent, soundEvent.get().getLocation().getPath(), sounds);
    }
}
