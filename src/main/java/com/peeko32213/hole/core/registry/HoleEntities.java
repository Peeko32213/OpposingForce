package com.peeko32213.hole.core.registry;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.common.entity.EntityUmberSpider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Hole.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HoleEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            Hole.MODID);

    public static final RegistryObject<EntityType<EntityPaleSpider>> PALE_SPIDER = ENTITIES.register("pale_spider",
            () -> EntityType.Builder.of(EntityPaleSpider::new, MobCategory.MONSTER).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(Hole.MODID, "pale_spider").toString()));

    public static final RegistryObject<EntityType<EntityUmberSpider>> UMBER_SPIDER = ENTITIES.register("umber_spider",
            () -> EntityType.Builder.of(EntityUmberSpider::new, MobCategory.MONSTER).sized(1.5F, 1.0F)
                    .build(new ResourceLocation(Hole.MODID, "umber_spider").toString()));


}
