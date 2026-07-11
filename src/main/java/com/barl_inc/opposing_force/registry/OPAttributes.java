package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class OPAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, OpposingForce.MOD_ID);

    public static final RegistryObject<Attribute> STEALTH = registerAttribute("stealth", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> AIR_SPEED = registerAttribute("air_speed", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> JUMP_POWER = registerAttribute("jump_power", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> EXPERIENCE_GAIN = registerAttribute("experience_gain", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SUMMON_DAMAGE = registerAttribute("summon_damage", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SUMMON_DURATION = registerAttribute("summon_duration", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final RegistryObject<Attribute> RANGED_DAMAGE = registerAttribute("ranged_damage", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));

    public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute) {
        return ATTRIBUTES.register(name, () -> attribute.apply("attribute.name." + OpposingForce.MOD_ID + "." + name));
    }
}
