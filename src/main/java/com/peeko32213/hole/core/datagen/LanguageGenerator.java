package com.peeko32213.hole.core.datagen;

import com.mojang.logging.LogUtils;
import com.peeko32213.hole.Hole;
import com.peeko32213.hole.core.registry.HoleCreativeTabs;
import com.peeko32213.hole.core.registry.HoleEntities;
import com.peeko32213.hole.core.registry.HoleItems;
import com.peeko32213.hole.core.registry.HoleSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(PackOutput output) {
        super(output, Hole.MODID, "en_us");
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    protected void addTranslations(){

        addTabName(HoleCreativeTabs.TAB.get(), "Hole");


        addEntityType(HoleEntities.PALE_SPIDER, "Pale Spider");
        addItem(HoleItems.PALE_SPIDER_SPAWN_EGG, "Pale Spider Spawn Egg");

        addEntityType(HoleEntities.UMBER_SPIDER, "Umber Spider");
        addItem(HoleItems.UMBER_SPIDER_SPAWN_EGG, "Umber Spider Spawn Egg");

        addSound(HoleSounds.UMBER_SPIDER_DEATH, "Umber Spider Dying");
        addSound(HoleSounds.UMBER_SPIDER_HURT, "Umber Spider Hurt");
        addSound(HoleSounds.UMBER_SPIDER_IDLE, "Umber Spider Idling");

    }

    @Override
    public String getName() {
        return  Hole.MODID  + " Languages: en_us";
    }

    public void addBETranslatable(String beName,String name){
        add(Hole.MODID + ".blockentity." + beName, name);
    }






    public void addSound(Supplier<? extends SoundEvent> key, String name){
        add(Hole.MODID + ".sound.subtitle." + key.get().getLocation().getPath(), name);
    }


    public void addTabName(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void add(CreativeModeTab key, String name) {
        add(key.getDisplayName().getString(), name);
    }

    public void addPotion(Supplier<? extends Potion> key, String name, String regName) {
        add(key.get(), name, regName);
    }

    public void addEnchantDescription(String description, Enchantment enchantment){
        add(enchantment.getDescriptionId() + ".desc", description);
    }

    public void add(Potion key, String name, String regName) {
        add("item.minecraft.potion.effect." + regName, name);
        add("item.minecraft.splash_potion.effect." + regName, "Splash " + name);
        add("item.minecraft.lingering_potion.effect." + regName, "Lingering " + name);
    }
}
