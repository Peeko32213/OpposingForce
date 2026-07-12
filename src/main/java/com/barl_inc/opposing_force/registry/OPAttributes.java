package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class OPAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, OpposingForce.MOD_ID);

    public static final DeferredHolder<Attribute, ?> STEALTH = registerAttribute("stealth", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, ?> AIR_SPEED = registerAttribute("air_speed", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, ?> JUMP_POWER = registerAttribute("jump_power", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, ?> EXPERIENCE_GAIN = registerAttribute("experience_gain", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, ?> SUMMON_DAMAGE = registerAttribute("summon_damage", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, ?> SUMMON_DURATION = registerAttribute("summon_duration", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, ?> RANGED_DAMAGE = registerAttribute("ranged_damage", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));

    public static DeferredHolder<Attribute, ?> registerAttribute(String name, Function<String, Attribute> attribute) {
        return ATTRIBUTES.register(name, () -> attribute.apply("attribute.name." + OpposingForce.MOD_ID + "." + name));
    }
}
