package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.enchantments.KickbackEnchantment;
import com.barl_inc.opposing_force.enchantments.OPEnchantment;
import com.barl_inc.opposing_force.enchantments.ThrowingEnchantment;
import com.barl_inc.opposing_force.items.BlasterItem;
import com.barl_inc.opposing_force.items.LaserBladeItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, OpposingForce.MOD_ID);
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final EnchantmentCategory TESLA_CANNON = EnchantmentCategory.create("tesla_cannon", (item -> item == OPItems.TESLA_CANNON.get()));
    public static final EnchantmentCategory VILE_BOULDER = EnchantmentCategory.create("vile_boulder", (item -> item == OPItems.VILE_BOULDER.get()));
    public static final EnchantmentCategory BLASTER = EnchantmentCategory.create("blaster", (item -> item instanceof BlasterItem));
    public static final EnchantmentCategory LASER_BLADE = EnchantmentCategory.create("laser_blade", (item -> item instanceof LaserBladeItem));

    // blaster
    public static final RegistryObject<Enchantment> POWER_SUPPLY = ENCHANTMENTS.register("power_supply", () -> new OPEnchantment("power_supply", Enchantment.Rarity.UNCOMMON, BLASTER, 3, 15, EquipmentSlot.MAINHAND));

    // tesla cannon
    public static final RegistryObject<Enchantment> KICKBACK = ENCHANTMENTS.register("kickback", KickbackEnchantment::new);

    // vile boulder
    public static final RegistryObject<Enchantment> PLAGUE = ENCHANTMENTS.register("plague", () -> new OPEnchantment("plague", Enchantment.Rarity.RARE, VILE_BOULDER, 2, 20, EquipmentSlot.MAINHAND));

    // laser blade
    public static final RegistryObject<Enchantment> THROWING = ENCHANTMENTS.register("throwing", ThrowingEnchantment::new);

    public static boolean areCompatible(OPEnchantment enchantment1, Enchantment enchantment2) {
        return true;
    }

    public static void addAllEnchantsToCreativeTab(CreativeModeTab.Output output, EnchantmentCategory enchantmentCategory){
        for (RegistryObject<Enchantment> enchantObject : ENCHANTMENTS.getEntries()) {
            if (enchantObject.isPresent()) {
                Enchantment enchant = enchantObject.get();
                if (enchant.category == enchantmentCategory) {
                    EnchantmentInstance instance = new EnchantmentInstance(enchant, enchant.getMaxLevel());
                    output.accept(EnchantedBookItem.createForEnchantment(instance));
                }
            }
        }
    }
}