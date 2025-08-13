package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, OpposingForce.MOD_ID);

    public static final RegistryObject<Attribute> STEALTH = registerAttribute("stealth", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ELECTRIC_RESISTANCE = registerAttribute("electric_resistance", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> BULK = registerAttribute("bulk", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> AIR_SPEED = registerAttribute("air_speed", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> JUMP_POWER = registerAttribute("jump_power", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> EXPERIENCE_GAIN = registerAttribute("experience_gain", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> FORTUNE = registerAttribute("fortune", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> LOOTING = registerAttribute("looting", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> VILLAGER_REPUTATION = registerAttribute("villager_reputation", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> VEGAN_NOURISHMENT = registerAttribute("vegan_nourishment", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));

    public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute) {
        return ATTRIBUTES.register(name, () -> attribute.apply("attribute.name." + OpposingForce.MOD_ID + "." + name));
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entity -> {
            event.add(entity, STEALTH.get());
            event.add(entity, ELECTRIC_RESISTANCE.get());
            event.add(entity, BULK.get());
            event.add(entity, AIR_SPEED.get());
            event.add(entity, JUMP_POWER.get());
            event.add(entity, EXPERIENCE_GAIN.get());
            event.add(entity, FORTUNE.get());
            event.add(entity, LOOTING.get());
            event.add(entity, VILLAGER_REPUTATION.get());
            event.add(entity, VEGAN_NOURISHMENT.get());
        });
    }
}
