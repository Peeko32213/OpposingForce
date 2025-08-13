package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.enchantments.*;
import com.unusualmodding.opposing_force.items.WoodenArmorItem;
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
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final EnchantmentCategory TESLA_BOW = EnchantmentCategory.create("tesla_bow", (item -> item == OPItems.TESLA_BOW.get()));
    public static final EnchantmentCategory VILE_BOULDER = EnchantmentCategory.create("vile_boulder", (item -> item == OPItems.VILE_BOULDER.get()));
    public static final EnchantmentCategory BLASTER = EnchantmentCategory.create("blaster", (item -> item == OPItems.BLASTER.get()));
    public static final EnchantmentCategory WOODEN_ARMOR = EnchantmentCategory.create("wooden_armor", (item -> item instanceof WoodenArmorItem));

    public static final RegistryObject<Enchantment> CAPACITANCE = ENCHANTMENTS.register("capacitance", () -> new OPEnchantment("capacitance", Enchantment.Rarity.RARE, TESLA_BOW, 3, 20, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> QUASAR = ENCHANTMENTS.register("quasar", () -> new TrueTreasureEnchantment("quasar", Enchantment.Rarity.VERY_RARE, TESLA_BOW, 1, 30, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> REBOUND = ENCHANTMENTS.register("rebound", () -> new OPEnchantment("rebound", Enchantment.Rarity.UNCOMMON, TESLA_BOW, 4, 15, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> STATIC_ATTRACTION = ENCHANTMENTS.register("static_attraction", () -> new TreasureEnchantment("static_attraction", Enchantment.Rarity.VERY_RARE, TESLA_BOW, 1, 25, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> PLAGUE = ENCHANTMENTS.register("plague", () -> new OPEnchantment("plague", Enchantment.Rarity.RARE, VILE_BOULDER, 2, 20, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> SPLITTING = ENCHANTMENTS.register("splitting", () -> new OPEnchantment("splitting", Enchantment.Rarity.RARE, BLASTER, 5, 20, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> RAPID_FIRE = ENCHANTMENTS.register("rapid_fire", () -> new OPEnchantment("rapid_fire", Enchantment.Rarity.RARE, BLASTER, 3, 20, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> PHOTOSYNTHESIS = ENCHANTMENTS.register("photosynthesis", () -> new TrueTreasureEnchantment("photosynthesis", Enchantment.Rarity.RARE, WOODEN_ARMOR, 1, 20, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> KICKBACK = ENCHANTMENTS.register("kickback", () -> new OPEnchantment("kickback", Enchantment.Rarity.RARE, EnchantmentCategory.CROSSBOW, 4, 20, EquipmentSlot.MAINHAND));

    public static boolean areCompatible(OPEnchantment enchantment1, Enchantment enchantment2) {
        if (enchantment1 == KICKBACK.get() && (enchantment2 == Enchantments.QUICK_CHARGE)) {
            return false;
        }
        if (enchantment1 == CAPACITANCE.get() && enchantment2 == Enchantments.MULTISHOT) {
            return false;
        }
        if (enchantment1 == QUASAR.get() && (enchantment2 == Enchantments.MULTISHOT || enchantment2 == CAPACITANCE.get() || enchantment2 == Enchantments.QUICK_CHARGE || enchantment2 == REBOUND.get())) {
            return false;
        }
        if (enchantment1 == STATIC_ATTRACTION.get() && (enchantment2 == CAPACITANCE.get() || enchantment2 == QUASAR.get())) {
            return false;
        }
        if (enchantment1 == RAPID_FIRE.get() && (enchantment2 == SPLITTING.get())) {
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