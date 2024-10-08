package com.peeko32213.hole.core.events;


import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.common.entity.EntityUmberSpider;
import com.peeko32213.hole.common.entity.projectile.EntitySmallElectricBall;
import com.peeko32213.hole.common.entity.util.FearTheLightGoal;
import com.peeko32213.hole.common.entity.util.SmartNearestTargetGoal;
import com.peeko32213.hole.common.entity.util.WanderStrollUpsideDown;
import com.peeko32213.hole.common.entity.*;
import com.peeko32213.hole.common.entity.util.FearTheLightGoal;
import com.peeko32213.hole.common.entity.util.SmartNearestTargetGoal;
import com.peeko32213.hole.common.entity.util.WanderStrollUpsideDown;
import com.peeko32213.hole.core.registry.HoleEntities;
import com.peeko32213.hole.core.registry.HoleEntityPlacement;
import com.peeko32213.hole.core.registry.HoleItems;
import net.minecraft.Util;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.peeko32213.hole.Hole.prefix;

@Mod.EventBusSubscriber(modid = Hole.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent e) {
        DispenserBlock.registerBehavior(HoleItems.ELECTRIC_CHARGE.get(), new DefaultDispenseItemBehavior() {
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
                EntitySmallElectricBall smallfireball = new EntitySmallElectricBall(level, d0, d1, d2, d3, d4, d5);
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
        event.put(HoleEntities.PALE_SPIDER.get(), EntityPaleSpider.createAttributes().build());
        event.put(HoleEntities.UMBER_SPIDER.get(), EntityUmberSpider.createAttributes().build());
        event.put(HoleEntities.RAMBLE.get(), EntityRamble.createAttributes().build());
        event.put(HoleEntities.TREMBLER.get(), EntityTrembler.createAttributes().build());
        event.put(HoleEntities.DICER.get(), EntityDicer.createAttributes().build());
        event.put(HoleEntities.TERROR.get(), EntityTerror.createAttributes().build());
        event.put(HoleEntities.VOLT.get(), EntityVolt.createAttributes().build());

    }

}
