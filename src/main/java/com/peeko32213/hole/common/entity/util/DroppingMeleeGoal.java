package com.peeko32213.hole.common.entity.util;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.core.registry.HoleGoals;
import com.scouter.goalsmith.codec.NullableFieldCodec;
import com.scouter.goalsmith.data.GoalCodec;
import com.scouter.goalsmith.data.goalcodec.RandomStrollGoalCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;

import java.util.EnumSet;

public class DroppingMeleeGoal extends Goal {


    private boolean prevOnGround = false;
    private EntityPaleSpider spider;
    private int attackCooldown = 0;

    public DroppingMeleeGoal(EntityPaleSpider spider) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.spider = spider;
    }

    @Override
    public boolean canUse() {
        return spider.getTarget() != null;
    }


    @Override
    public void tick() {
        LivingEntity target = spider.getTarget();
        if (attackCooldown > 0) {
            attackCooldown--;
        }
        if (target != null) {
            double dist = spider.distanceTo(target);
            if (spider.isUpsideDown()) {
                double d0 = spider.getX() - target.getX();
                double d2 = spider.getZ() - target.getZ();
                double xzDistSqr = d0 * d0 + d2 * d2;
                BlockPos ceilingPos = new BlockPos((int) target.getX(), (int) (spider.getY() - 3 - spider.level().random.nextInt(3)), (int) target.getZ());
                BlockPos lowestPos = EntityPaleSpider.getLowestPos(spider.level(), ceilingPos);
                spider.getMoveControl().setWantedPosition(lowestPos.getX() + 0.5F, ceilingPos.getY(), lowestPos.getZ() + 0.5F, 1.1D);
                if (xzDistSqr < 12.5F) {
                    spider.setUpsideDown(false);
                }
            } else {
                if (spider.onGround()) {
                    spider.getNavigation().moveTo(target, 1.15D);
                }
            }
            if (dist < 1.8D) {
                if (attackCooldown == 0) {
                    spider.doHurtTarget(target);
                    attackCooldown = 20;
                    spider.swinging = true;
                }
            }
        }
    }
}