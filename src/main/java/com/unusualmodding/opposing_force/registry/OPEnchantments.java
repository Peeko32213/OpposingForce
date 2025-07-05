package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.enchantments.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class OPEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, OpposingForce.MOD_ID);

    public static final EnchantmentCategory TESLA_BOW = EnchantmentCategory.create("tesla_bow", (item -> item == OPItems.TESLA_BOW.get()));

    // Opposing Force categories
    public static final RegistryObject<Enchantment> BIG_ELECTRIC_BALL = ENCHANTMENTS.register("capacitance", () -> new OPEnchantment("capacitance", Enchantment.Rarity.RARE, TESLA_BOW, 3, 20, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> BOUNCY_ELECTRIC_BALL = ENCHANTMENTS.register("rebound", () -> new OPEnchantment("rebound", Enchantment.Rarity.UNCOMMON, TESLA_BOW, 4, 15, EquipmentSlot.MAINHAND));

    // Vanilla categories
    public static final RegistryObject<Enchantment> KICKBACK = ENCHANTMENTS.register("kickback", () -> new OPEnchantment("kickback", Enchantment.Rarity.RARE, EnchantmentCategory.CROSSBOW, 3, 10, EquipmentSlot.MAINHAND));

    public static boolean areCompatible(OPEnchantment enchantment1, Enchantment enchantment2) {
        if (enchantment1 == KICKBACK.get() && enchantment2 == Enchantments.QUICK_CHARGE) {
            return false;
        }
        if (enchantment1 == BIG_ELECTRIC_BALL.get() && enchantment2 == Enchantments.MULTISHOT) {
            return false;
        }
        return true;
    }

    public static void addAllEnchantsToCreativeTab(CreativeModeTab.Output output, EnchantmentCategory enchantmentCategory){
        for (RegistryObject<Enchantment> enchantObject : ENCHANTMENTS.getEntries()) {
            if (enchantObject.isPresent()) {
                Enchantment enchant = enchantObject.get();
                if(enchant.category == enchantmentCategory){
                    EnchantmentInstance instance = new EnchantmentInstance(enchant, enchant.getMaxLevel());
                    output.accept(EnchantedBookItem.createForEnchantment(instance));
                }
            }
        }
    }
}