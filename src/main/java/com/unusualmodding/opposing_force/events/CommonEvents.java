package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.*;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

//    @SubscribeEvent
//    public static void commonSetup(FMLCommonSetupEvent e) {
//        DispenserBlock.registerBehavior(OPItems.ELECTRIC_CHARGE.get(), new DefaultDispenseItemBehavior() {
//            public ItemStack execute(BlockSource source, ItemStack stack) {
//                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
//                Position position = DispenserBlock.getDispensePosition(source);
//                double d0 = position.x() + (double)((float)direction.getStepX() * 0.3F);
//                double d1 = position.y() + (double)((float)direction.getStepY() * 0.3F);
//                double d2 = position.z() + (double)((float)direction.getStepZ() * 0.3F);
//                Level level = source.getLevel();
//                RandomSource randomsource = level.random;
//                double d3 = randomsource.triangle(direction.getStepX(), 0.11485000000000001D);
//                double d4 = randomsource.triangle(direction.getStepY(), 0.11485000000000001D);
//                double d5 = randomsource.triangle(direction.getStepZ(), 0.11485000000000001D);
//                ElectricBall smallfireball = new ElectricBall(Level level);
//                level.addFreshEntity(Util.make(smallfireball, (ball) -> {
//                    ball.setItem(stack);
//                }));
//                stack.shrink(1);
//                return stack;
//            }
//            });
//        }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(OPEntities.PALE_SPIDER.get(), PaleSpiderEntity.createAttributes().build());
        event.put(OPEntities.UMBER_SPIDER.get(), UmberSpiderEntity.createAttributes().build());
        event.put(OPEntities.RAMBLE.get(), RambleEntity.createAttributes().build());
        event.put(OPEntities.TREMBLER.get(), TremblerEntity.createAttributes().build());
        event.put(OPEntities.DICER.get(), DicerEntity.createAttributes().build());
        event.put(OPEntities.TERROR.get(), TerrorEntity.createAttributes().build());
        event.put(OPEntities.VOLT.get(), VoltEntity.createAttributes().build());
        event.put(OPEntities.WHIZZ.get(), WhizzEntity.createAttributes().build());
        event.put(OPEntities.BOUNCER.get(), HopperEntity.createAttributes().build());
        event.put(OPEntities.FROWZY.get(), FrowzyEntity.createAttributes().build());
        event.put(OPEntities.GUZZLER.get(), GuzzlerEntity.createAttributes().build());
        event.put(OPEntities.FIRE_SLIME.get(), FireSlimeEntity.createAttributes().build());
        event.put(OPEntities.SLUG.get(), SlugEntity.createAttributes().build());
//        event.put(OPEntities.FETID.get(), FetidEntity.createAttributes().build());
//        event.put(OPEntities.SPINDLE.get(), SpindleEntity.createAttributes().build());
    }

}
