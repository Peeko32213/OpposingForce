package com.unusualmodding.opposingforce.core.events;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.entity.custom.monster.*;
import com.unusualmodding.opposingforce.common.entity.custom.projectile.SmallElectricBall;
import com.unusualmodding.opposingforce.core.registry.OPEntities;
import com.unusualmodding.opposingforce.core.registry.OPItems;
import net.minecraft.Util;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = OpposingForce.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent e) {
        DispenserBlock.registerBehavior(OPItems.ELECTRIC_CHARGE.get(), new DefaultDispenseItemBehavior() {
            public ItemStack execute(BlockSource p_123556_, ItemStack p_123557_) {
                Direction direction = p_123556_.getBlockState().getValue(DispenserBlock.FACING);
                Position position = DispenserBlock.getDispensePosition(p_123556_);
                double d0 = position.x() + (double)((float)direction.getStepX() * 0.3F);
                double d1 = position.y() + (double)((float)direction.getStepY() * 0.3F);
                double d2 = position.z() + (double)((float)direction.getStepZ() * 0.3F);
                Level level = p_123556_.getLevel();
                RandomSource randomsource = level.random;
                double d3 = randomsource.triangle((double)direction.getStepX(), 0.11485000000000001D);
                double d4 = randomsource.triangle((double)direction.getStepY(), 0.11485000000000001D);
                double d5 = randomsource.triangle((double)direction.getStepZ(), 0.11485000000000001D);
                SmallElectricBall smallfireball = new SmallElectricBall(level, d0, d1, d2, d3, d4, d5);
                level.addFreshEntity(Util.make(smallfireball, (p_123552_) -> {
                    p_123552_.setItem(p_123557_);
                }));
                p_123557_.shrink(1);
                return p_123557_;
            }
            });
        }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(OPEntities.PALE_SPIDER.get(), PaleSpiderEntity.createAttributes().build());
        event.put(OPEntities.UMBER_SPIDER.get(), UmberSpiderEntity.createAttributes().build());
        event.put(OPEntities.RAMBLE.get(), RambleEntity.createAttributes().build());
        event.put(OPEntities.TREMBLER.get(), TremblerEntity.createAttributes().build());
        event.put(OPEntities.DICER.get(), DicerEntity.createAttributes().build());
        event.put(OPEntities.TERROR.get(), TerrorEntity.createAttributes().build());
        event.put(OPEntities.VOLT.get(), VoltEntity.createAttributes().build());
        event.put(OPEntities.WIZZ.get(), WizzEntity.createAttributes().build());
        event.put(OPEntities.HOPPER.get(), HopperEntity.createAttributes().build());
        event.put(OPEntities.FROWZY.get(), FrowzyEntity.createAttributes().build());
        event.put(OPEntities.GUZZLER.get(), GuzzlerEntity.createAttributes().build());
        event.put(OPEntities.FIRE_SLIME.get(), FireSlimeEntity.createAttributes().build());
        event.put(OPEntities.SLUG.get(), SlugEntity.createAttributes().build());
        event.put(OPEntities.FETID.get(), FetidEntity.createAttributes().build());
        event.put(OPEntities.SPINDLE.get(), SpindleEntity.createAttributes().build());
    }

}
