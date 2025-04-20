package com.unusualmodding.opposingforce.core.registry;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.enchantments.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = OpposingForce.MODID, bus = EventBusSubscriber.Bus.MOD)
public class OPEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, OpposingForce.MODID);

    public static final RegistryObject<Enchantment> BIG_ELECTRIC_BALL = ENCHANTMENTS.register("big_electric_ball", () -> new LargeElectricBallEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> BOUNCY_ELECTRIC_BALL = ENCHANTMENTS.register("bouncy_electric_ball", () -> new BouncyElectricBallEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> KICKBACK = ENCHANTMENTS.register("kickback", () -> new KickbackEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));

}